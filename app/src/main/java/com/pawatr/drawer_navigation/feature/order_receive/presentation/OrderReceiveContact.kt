package com.pawatr.drawer_navigation.feature.order_receive.presentation

import com.pawatr.drawer_navigation.core.viewmodel.UiAction
import com.pawatr.drawer_navigation.core.viewmodel.UiEvent
import com.pawatr.drawer_navigation.core.viewmodel.UiState


data class OrderReceiveState(
    val title: String = "รับสินค้าจากคลัง",
    val input: String = "",
    val barcodes: List<String> = emptyList(),
    val showResumeDialog: Boolean = false,
    val cachedBarcodes: List<String>? = null
) : UiState

sealed interface OrderReceiveAction : UiAction {
    data class Submit(val text: String) : OrderReceiveAction
    data class InputChanged(val text: String) : OrderReceiveAction
    data class Remove(val code: String) : OrderReceiveAction
    data object AcceptCached : OrderReceiveAction
    data object DiscardCached : OrderReceiveAction
    data object DismissDialog : OrderReceiveAction
}

sealed interface OrderReceiveEvent : UiEvent {
    data class Toast(val msg: String) : OrderReceiveEvent
}