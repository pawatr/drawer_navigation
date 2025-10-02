package com.pawatr.drawer_navigation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.pawatr.drawer_navigation.core.design.theme.DrawernavigationTheme
import com.pawatr.drawer_navigation.feature.home.presentation.HomeScreen
import com.pawatr.drawer_navigation.feature.order_receive.presentation.OrderReceiveScreen
import com.pawatr.drawer_navigation.feature.menu_order_receive.presentation.MenuReceiveOrderScreen
import com.pawatr.drawer_navigation.feature.menu_order_receive_tote.presentation.MenuOrderReceiveToteScreen
import com.pawatr.drawer_navigation.feature.order_receive_tote.presentation.OrderReceiveToteScreen
import com.pawatr.drawer_navigation.navigation.AppNavigator
import com.pawatr.drawer_navigation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawernavigationTheme {
                NavDrawerContainer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawerContainer() {
    val backStack = rememberNavBackStack<Screen>(Screen.Home)
    val navigator = remember(backStack) { AppNavigator(backStack) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    val drawerItems = remember {
        listOf(
            DrawerItem(Icons.Outlined.Home, "Home", Screen.Home),
            DrawerItem(Icons.Outlined.Receipt, "รับสินค้า", Screen.MenuOrderReceive),
            DrawerItem(Icons.Outlined.Receipt, "ตรวจนับ", Screen.MenuOrderReceiveTote)
        )
    }

    BackHandler(enabled = drawerState.isOpen) { scope.launch { drawerState.close() } }
    BackHandler(enabled = !drawerState.isOpen && backStack.size > 1) {
        backStack.removeLastOrNull()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = backStack.lastOrNull() == item.screen,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navigator.replaceAll(item.screen)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "SAGE",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF006D2E),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbar) }
        ) { inner ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() != null },
                entryProvider = entryProvider {
                    entry<Screen.Home> {
                        HomeScreen(
                            onShowMessage = { msg ->
                                scope.launch { snackbar.showSnackbar(msg, duration = SnackbarDuration.Short) }
                            },
                            onNavigateOrderReceive = {
                                navigator.toMenuOrderReceive()
                            },
                            onNavigateOrderReceiveTote = {
                                navigator.toOrderReceiveTote()
                            }
                        )
                    }
                    entry<Screen.MenuOrderReceive> {
                        MenuReceiveOrderScreen(
                            onNavigateOrderReceive = { navigator.toOrderReceive() }
                        )
                    }
                    entry<Screen.OrderReceive> {
                        OrderReceiveScreen(
                            onShowMessage = { msg ->
                                scope.launch { snackbar.showSnackbar(msg, duration = SnackbarDuration.Short) }
                            }
                        )
                    }
                    entry(Screen.MenuOrderReceiveTote) {
                        MenuOrderReceiveToteScreen(
                            onNavigateOrderReceiveTote = {
                                navigator.toOrderReceiveTote()
                            }
                        )
                    }
                    entry<Screen.OrderReceiveTote> {
                        OrderReceiveToteScreen()
                    }
                },
                entryDecorators = listOf(
                    rememberSavedStateNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                modifier = Modifier.padding(inner)
            )
        }
    }
}

data class DrawerItem(
    val icon: ImageVector,
    val title: String,
    val screen: Screen
)