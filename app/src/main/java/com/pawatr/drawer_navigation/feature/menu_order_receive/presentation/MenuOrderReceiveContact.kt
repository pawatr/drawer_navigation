package com.pawatr.drawer_navigation.feature.menu_order_receive.presentation

import com.pawatr.drawer_navigation.core.viewmodel.UiAction
import com.pawatr.drawer_navigation.core.viewmodel.UiEvent
import com.pawatr.drawer_navigation.core.viewmodel.UiState
import com.pawatr.drawer_navigation.core.model.MenuCardUi

data class MenuOrderReceiveState(
    val title: String = "เมนูรับสินค้า",
    val menuList: List<MenuCardUi> = emptyList()
) : UiState

sealed interface MenuOrderReceiveAction : UiAction {
    data object OpenOrderReceive : MenuOrderReceiveAction
}

sealed interface MenuOrderReceiveEvent : UiEvent {
    data object NavigateToOrderReceive : MenuOrderReceiveEvent
}