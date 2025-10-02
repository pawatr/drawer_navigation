package com.pawatr.drawer_navigation.feature.home.presentation

import com.pawatr.drawer_navigation.core.viewmodel.*
import com.pawatr.drawer_navigation.feature.menu_order_receive.presentation.MenuOrderReceiveEvent

data class HomeState(
    val greeting: String = "Hello Compose",
    val loading: Boolean = false,
    val branchName: String = "N/A",
    val versionName: String = "7.1.0.0"
) : UiState

sealed interface HomeAction : UiAction {
    data object Refresh : HomeAction
    data object OpenMenuOrderReceive : HomeAction
    data object OpenMenuOrderReceiveTote : HomeAction
}

sealed interface HomeEvent : UiEvent {
    data class Toast(val msg: String) : HomeEvent
    data object NavigateToOrderReceive : HomeEvent
    data object NavigateToOrderReceiveTote : HomeEvent
}
