package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

class SirenSoundSynth(private val context: Context) {
    private var isPlaying = false
    private var synthJob: Job? = null
    private var audioTrack: AudioTrack? = null

    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    fun startSiren(scope: CoroutineScope) {
        if (isPlaying) return
        isPlaying = true

        synthJob = scope.launch(Dispatchers.Default) {
            val sampleRate = 44100
            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            try {
                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                audioTrack?.play()

                val buffer = ShortArray(1024)
                var phase = 0.0
                var time = 0.0
                val lowFreq = 650.0
                val highFreq = 1450.0

                while (isActive && isPlaying) {
                    // Modulate frequency smoothly (wailing siren sound)
                    val mod = (sin(2.0 * Math.PI * 1.8 * time) + 1.0) / 2.0
                    val currentFreq = lowFreq + (highFreq - lowFreq) * mod

                    for (i in buffer.indices) {
                        phase += 2.0 * Math.PI * currentFreq / sampleRate
                        if (phase > 2.0 * Math.PI) {
                            phase -= 2.0 * Math.PI
                        }
                        // Add harmonics for sharp piercing siren tone
                        val primary = sin(phase)
                        val harmonic = 0.3 * sin(2 * phase)
                        val sample = ((primary + harmonic) * 0.8 * Short.MAX_VALUE).toInt()
                            .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                        buffer[i] = sample.toShort()
                    }

                    audioTrack?.write(buffer, 0, buffer.size)
                    time += buffer.size.toDouble() / sampleRate
                }
            } catch (_: Exception) {
                // Audio synthesis fallback handled gracefully
            }
        }

        // Start vibration pulses
        try {
            if (vibrator?.hasVibrator() == true) {
                val timings = longArrayOf(0, 400, 200, 400, 200)
                val amplitudes = intArrayOf(0, 255, 0, 255, 0)
                val effect = VibrationEffect.createWaveform(timings, amplitudes, 0)
                vibrator.vibrate(effect)
            }
        } catch (_: Exception) {}
    }

    fun stopSiren() {
        isPlaying = false
        synthJob?.cancel()
        synthJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null

        try {
            vibrator?.cancel()
        } catch (_: Exception) {}
    }
}
