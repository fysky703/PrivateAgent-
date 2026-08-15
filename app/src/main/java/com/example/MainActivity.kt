package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AppRole
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AgentClientScreen
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ControlViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ControlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val deviceState by viewModel.deviceState.collectAsStateWithLifecycle()
                val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
                val appRole by viewModel.appRole.collectAsStateWithLifecycle()
                val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(toastMessage) {
                    toastMessage?.let { msg ->
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                        viewModel.clearToast()
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CyberBackground)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    Crossfade(targetState = appRole, label = "roleCrossfade") { role ->
                        when (role) {
                            AppRole.ADMIN_CONSOLE -> {
                                AdminDashboardScreen(
                                    viewModel = viewModel,
                                    deviceState = deviceState,
                                    selectedTab = currentTab,
                                    onTabSelected = { viewModel.setTab(it) }
                                )
                            }
                            AppRole.AGENT_CLIENT_VIEW -> {
                                AgentClientScreen(
                                    viewModel = viewModel,
                                    deviceState = deviceState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
