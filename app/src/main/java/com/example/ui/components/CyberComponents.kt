package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppRole
import com.example.data.model.ControlTab
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberBorderBright
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanGlow
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberGreenDark
import com.example.ui.theme.CyberGreenGlow
import com.example.ui.theme.CyberRed
import com.example.ui.theme.CyberRedGlow
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun CyberHeader(
    currentRole: AppRole,
    onToggleRole: () -> Unit,
    onOpenInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left hamburger button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CyberSurface,
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.size(44.dp)
        ) {
            IconButton(
                onClick = onOpenInfo,
                modifier = Modifier.testTag("btn_menu")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = CyberCyan,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Center Cyber Logo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onToggleRole() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "XYZ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = CyberCyan,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "XRAT",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp,
                    color = CyberRed,
                    fontFamily = FontFamily.Monospace
                )
            }
            // Role switcher pill
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (currentRole == AppRole.ADMIN_CONSOLE) CyberRed.copy(alpha = 0.15f) else CyberCyan.copy(alpha = 0.15f),
                border = BorderStroke(
                    1.dp,
                    if (currentRole == AppRole.ADMIN_CONSOLE) CyberRed.copy(alpha = 0.4f) else CyberCyan.copy(alpha = 0.4f)
                ),
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Text(
                    text = if (currentRole == AppRole.ADMIN_CONSOLE) "● ADMIN CONSOLE" else "⚡ AGENT TARGET VIEW",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = if (currentRole == AppRole.ADMIN_CONSOLE) CyberRed else CyberCyan,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        // Right Profile / Mode button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CyberSurface,
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.size(44.dp)
        ) {
            IconButton(
                onClick = onToggleRole,
                modifier = Modifier.testTag("btn_switch_view")
            ) {
                Icon(
                    imageVector = if (currentRole == AppRole.ADMIN_CONSOLE) Icons.Default.PhoneAndroid else Icons.Default.Tune,
                    contentDescription = "Switch View",
                    tint = if (currentRole == AppRole.ADMIN_CONSOLE) CyberRed else CyberCyan,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
fun AntiUninstallShieldCard(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isEnabled) CyberGreen else CyberBorder
    val glowColor = if (isEnabled) CyberGreenGlow else Color.Transparent

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        border = BorderStroke(1.5.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .shadow(if (isEnabled) 12.dp else 0.dp, spotColor = glowColor, ambientColor = glowColor)
            .clickable { onToggle() }
            .testTag("card_anti_uninstall")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Shield Icon Container
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isEnabled) CyberGreen.copy(alpha = 0.15f) else CyberBorder.copy(alpha = 0.3f),
                    border = BorderStroke(1.dp, if (isEnabled) CyberGreen.copy(alpha = 0.5f) else CyberBorder),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Anti Uninstall",
                            tint = if (isEnabled) CyberGreen else TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "ANTI UNINSTALL",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = TextPrimary,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = if (isEnabled) "ENABLED • APP PROTECTED" else "DISABLED • UNPROTECTED",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isEnabled) CyberGreen else TextSecondary,
                        letterSpacing = 0.5.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            CyberSwitch(
                checked = isEnabled,
                onCheckedChange = { onToggle() },
                activeColor = CyberCyan,
                testTag = "switch_anti_uninstall"
            )
        }
    }
}

@Composable
fun CyberTabRow(
    selectedTab: ControlTab,
    onTabSelected: (ControlTab) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = Color.Transparent,
        contentColor = TextPrimary,
        edgePadding = 16.dp,
        divider = {},
        indicator = { tabPositions ->
            if (selectedTab.ordinal < tabPositions.size) {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab.ordinal])
                        .height(3.dp)
                        .padding(horizontal = 12.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(CyberCyan, CyberRed)),
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        ControlTab.entries.forEach { tab ->
            val isSelected = selectedTab == tab
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 2.dp)
                    .testTag("tab_${tab.name.lowercase()}"),
                text = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) CyberCardBg else Color.Transparent,
                        border = if (isSelected) BorderStroke(1.dp, CyberCyan.copy(alpha = 0.4f)) else null,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(CyberRed, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(
                                text = tab.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                letterSpacing = 1.sp,
                                color = if (isSelected) CyberCyan else TextSecondary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    title: String,
    statusText: String,
    statusActive: Boolean,
    icon: ImageVector,
    iconColor: Color = CyberCyan,
    isAlertCard: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    val borderColor = if (isAlertCard) {
        CyberRed.copy(alpha = 0.8f)
    } else if (statusActive) {
        CyberCyan.copy(alpha = 0.6f)
    } else {
        CyberBorder
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CyberCardBg),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Icon Box
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
                            color = if (isAlertCard) CyberRed else TextPrimary,
                            letterSpacing = 0.5.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = statusText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (statusActive) {
                                if (isAlertCard) CyberRed else CyberCyan
                            } else {
                                TextTertiary
                            },
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            content()
        }
    }
}

@Composable
fun CyberSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    activeColor: Color = CyberCyan,
    testTag: String = ""
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 24.dp else 2.dp,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "thumbOffset"
    )

    val bgColor by animateColorAsState(
        targetValue = if (checked) activeColor.copy(alpha = 0.25f) else CyberBorder.copy(alpha = 0.4f),
        label = "bgColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) activeColor else CyberBorderBright,
        label = "borderColor"
    )

    Box(
        modifier = Modifier
            .width(54.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(15.dp))
            .clickable { onCheckedChange(!checked) }
            .testTag(testTag)
            .padding(2.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(22.dp)
                .background(
                    if (checked) activeColor else TextSecondary,
                    CircleShape
                )
                .shadow(if (checked) 6.dp else 0.dp, shape = CircleShape, spotColor = activeColor)
        )
    }
}
