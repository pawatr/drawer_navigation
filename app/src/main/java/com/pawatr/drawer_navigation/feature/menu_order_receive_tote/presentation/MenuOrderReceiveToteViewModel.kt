package com.pawatr.drawer_navigation.feature.menu_order_receive_tote.presentation

import androidx.lifecycle.viewModelScope
import com.pawatr.drawer_navigation.core.model.MenuCardUi
import com.pawatr.drawer_navigation.core.viewmodel.BaseViewModel
import com.pawatr.drawer_navigation.feature.order_receive.presentation.OrderReceiveState
import com.pawatr.drawer_navigation.feature.order_receive_tote.presentation.OrderReceiveToteAction
import com.pawatr.drawer_navigation.feature.order_receive_tote.presentation.OrderReceiveToteEvent
import com.pawatr.drawer_navigation.feature.order_receive_tote.presentation.OrderReceiveToteUiState
import kotlinx.coroutines.launch

class MenuOrderReceiveToteViewModel() : BaseViewModel<MenuOrderReceiveToteAction, MenuOrderReceiveToteEvent, MenuOrderReceiveToteUiState>() {

    override fun initialState() = MenuOrderReceiveToteUiState()

    override fun reduce(action: MenuOrderReceiveToteAction) {
        when(action) {
            is MenuOrderReceiveToteAction.OpenOrderReceiveTote -> viewModelScope.launch {
                send(MenuOrderReceiveToteEvent.NavigateToOrderReceiveTote)
            }
        }
    }

    fun onClickMenu(item: MenuCardUi) {
        reduce(MenuOrderReceiveToteAction.OpenOrderReceiveTote)
    }
}