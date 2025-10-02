package com.pawatr.drawer_navigation.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.pawatr.drawer_navigation.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeAction, HomeEvent, HomeState>() {

    override fun initialState() = HomeState()

    fun onScan() = sendToast("สแกนรายการ")
    fun onShelfRefill() = sendToast("เติมสินค้าตามชั้นวาง")
    fun onViewAllBranchWork() = sendToast("ดูงานสาขาทั้งหมด")
    fun onPrintQr() = sendToast("พิมพ์ป้าย QR Code")
    fun onCheckTote() = reduce(HomeAction.OpenMenuOrderReceiveTote)
    fun onReceiveDetails() = sendToast("รายละเอียดการรับสินค้า")
    fun onRefresh() = reduce(HomeAction.Refresh)

    override fun reduce(action: HomeAction) {
        when (action) {
            HomeAction.Refresh -> refresh()
            HomeAction.OpenMenuOrderReceive -> viewModelScope.launch {
                send(HomeEvent.NavigateToOrderReceive)
            }
            HomeAction.OpenMenuOrderReceiveTote -> viewModelScope.launch {
                send(HomeEvent.NavigateToOrderReceiveTote)
            }
        }
    }

    fun onReceiveFromWarehouse() {
        reduce(HomeAction.OpenMenuOrderReceive)
    }

    private fun refresh() = viewModelScope.launch {
        setState { copy(loading = true) }
        delay(400)
        setState { copy(loading = false, greeting = "Updated!") }
        send(HomeEvent.Toast("Refreshed"))
    }

    private fun sendToast(msg: String) = viewModelScope.launch {
        send(HomeEvent.Toast(msg))
    }
}
