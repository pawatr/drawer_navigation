package com.pawatr.drawer_navigation.feature.order_receive_tote.presentation

import com.pawatr.drawer_navigation.core.viewmodel.BaseViewModel
import com.pawatr.drawer_navigation.feature.order_receive.presentation.OrderReceiveState

class OrderReceiveToteViewModel() : BaseViewModel<OrderReceiveToteAction, OrderReceiveToteEvent, OrderReceiveToteUiState>() {

    override fun initialState() = OrderReceiveToteUiState()

    override fun reduce(action: OrderReceiveToteAction) {
        // todo when action
    }
}