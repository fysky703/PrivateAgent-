# XYZ XRAT - Advanced Remote Admin & Device Management Console

An advanced Android Remote Management and Device Security Administration application built with Kotlin, Jetpack Compose, and Material Design 3 Cyberpunk aesthetics.

---

## 📱 Features

### 1. Control Operations
- **Flashlight Control:** Remote toggle of camera LED with live beam telemetry.
- **Play Video Takeover:** Force-stream YouTube or direct MP4 video URLs directly on the target agent device.
- **Set Wallpaper:** Remote wallpaper deployment with cyber neon presets and custom image URL injection.
- **Lock Device (V1):** Standard blackout lockdown screen with countdown timer in minutes and touch input block.
- **Lock Device V2 (Critical Emergency Alarm):**
  - High-decibel piercing police siren synthesized audio alert.
  - Rapid red strobe flash visual alert.
  - Device vibration pattern.
  - Locked red padlock interface with live countdown timer in crimson red.
  - Real-time custom Admin Broadcast Message banner.
  - Power button and touch override prevention simulation.

### 2. Live Feeds
- **Live Screen Monitor:** Real-time 30 FPS stream simulation with bitrate and resolution monitoring.
- **Live Camera Stream:** Front & Rear camera feed switching with IR Night Vision mode.
- **Live Microphone & Intercom:** Surrounding audio level monitoring and Push-to-Talk Admin voice broadcasting directly to agent speaker.

### 3. Management & Stealth
- **Anti-Uninstall Shield:** Device Administrator protection toggle.
- **Hide App (Stealth Mode):** Toggle agent launcher visibility.
- **Remote Reboot & Factory Reset:** Signal dispatcher for system restart and data wipe.

### 4. Telemetry, GPS & Hardware
- **Hardware Toggles:** Remote control of Wi-Fi, Mobile Data, Bluetooth, and GPS Location.
- **Live GPS Tracking:** Accurate Latitude, Longitude, location address, and interactive radar visualizer.
- **Device Specs:** Battery level, Charging status, Public IP (`185.220.101.5`), Local IP (`192.168.1.108`), Internal storage & RAM gauges.

### 5. Remote Phone Dialer & Mic Intercom
- Enter target phone number -> Dispatches immediate call on Agent device.
- Admin Push-to-Talk Mic Intercom for live 2-way conversation.

---

## ⚡ Firebase Realtime Database Integration

Pre-configured with Firebase Realtime Database:
- **Project ID:** `my-project-a03ee`
- **Database URL:** `https://my-project-a03ee-default-rtdb.asia-southeast1.firebasedatabase.app`
- **API Key:** `AIzaSyBa7zGD66GlRrJq5NzTxAPjb7nedvkEIhM`
- **Storage Bucket:** `my-project-a03ee.firebasestorage.app`

---

## 🚀 GitHub Repository Setup & Build Instructions

### 1. Push to GitHub
```bash
git init
git add .
git commit -m "Initial commit: XYZ XRAT Admin Console"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/XYZ-XRAT-Admin.git
git push -u origin main
```

### 2. Build APK in Android Studio
1. Open this project in **Android Studio (Ladybug or newer)**.
2. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
3. The generated APK will be located in `app/build/outputs/apk/debug/app-debug.apk`.

### 3. Direct Download in Google AI Studio
- You can instantly download the complete source code ZIP from the top **Settings > Export Project as ZIP** menu in Google AI Studio.
