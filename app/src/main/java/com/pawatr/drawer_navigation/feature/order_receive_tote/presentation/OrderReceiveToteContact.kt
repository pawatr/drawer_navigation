package com.pawatr.drawer_navigation.feature.order_receive_tote.presentation

import com.pawatr.drawer_navigation.core.viewmodel.UiAction
import com.pawatr.drawer_navigation.core.viewmodel.UiEvent
import com.pawatr.drawer_navigation.core.viewmodel.UiState

data class OrderReceiveToteUiState(
    val title: String = "ตรวจสอบสินค้าภายใน Tote",
): UiState

sealed interface OrderReceiveToteAction: UiAction

sealed interface  OrderReceiveToteEvent: UiEvent