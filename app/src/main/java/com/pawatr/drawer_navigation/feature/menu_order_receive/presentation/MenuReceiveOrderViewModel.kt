package com.pawatr.drawer_navigation.feature.menu_order_receive.presentation

import androidx.lifecycle.viewModelScope
import com.pawatr.drawer_navigation.core.viewmodel.BaseViewModel
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceiveType
import com.pawatr.drawer_navigation.domain.usecase.menu_order_receive.MenuOrderReceiveUseCase
import com.pawatr.drawer_navigation.feature.menu_order_receive.mapper.toUi
import com.pawatr.drawer_navigation.core.model.MenuCardUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MenuReceiveOrderViewModel @Inject constructor(
    private val menuOrderReceiveUseCase: MenuOrderReceiveUseCase
) : BaseViewModel<MenuOrderReceiveAction, MenuOrderReceiveEvent, MenuOrderReceiveState>() {

    override fun initialState(): MenuOrderReceiveState = MenuOrderReceiveState()

    init {
        viewModelScope.launch {
            val listMenu = menuOrderReceiveUseCase.invoke()
            val listUiMenu = listMenu.map { it.toUi() }
            setState { copy(menuList = listUiMenu) }
        }
    }

    override fun reduce(action: MenuOrderReceiveAction) {
        when(action) {
            is MenuOrderReceiveAction.OpenOrderReceive -> {
                viewModelScope.launch {
                    send(MenuOrderReceiveEvent.NavigateToOrderReceive)
                }
            }
        }
    }

    fun onClickMenu(item: MenuCardUi) {
        when(item.type) {
            MenuOrderReceiveType.WAREHOUSE -> {
                reduce(MenuOrderReceiveAction.OpenOrderReceive)
            }
            else -> {
                // todo nav to PurchaseInvoice
            }
        }
    }
}