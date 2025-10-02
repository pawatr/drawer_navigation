package com.pawatr.drawer_navigation.feature.menu_order_receive_tote.presentation

import com.pawatr.drawer_navigation.core.viewmodel.UiAction
import com.pawatr.drawer_navigation.core.viewmodel.UiEvent
import com.pawatr.drawer_navigation.core.viewmodel.UiState

data class MenuOrderReceiveToteUiState(
    val title: String = "ตรวจสอบสินค้าภายใน Tote",
): UiState

sealed interface MenuOrderReceiveToteAction: UiAction {
    data object OpenOrderReceiveTote: MenuOrderReceiveToteAction
}

sealed interface  MenuOrderReceiveToteEvent: UiEvent {
    data object NavigateToOrderReceiveTote: MenuOrderReceiveToteEvent
}