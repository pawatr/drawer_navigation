package com.pawatr.drawer_navigation.feature.order_receive.presentation

import androidx.lifecycle.viewModelScope
import com.pawatr.drawer_navigation.core.viewmodel.BaseViewModel
import com.pawatr.drawer_navigation.data.local.CacheHelper
import com.pawatr.drawer_navigation.data.local.CacheKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OrderReceiveViewModel @Inject constructor(
    private val cacheHelper: CacheHelper
) : BaseViewModel<OrderReceiveAction, OrderReceiveEvent, OrderReceiveState>() {

    override fun initialState() = OrderReceiveState()

    init {
        viewModelScope.launch {
            cacheHelper.getCache<List<String>>(CacheKeys.BARCODE_LIST)?.let { cached ->
                if (cached.isNotEmpty()) {
                    setState { copy(showResumeDialog = true, cachedBarcodes = cached) }
                }
            }
        }
    }

    override fun reduce(action: OrderReceiveAction) {
        when (action) {
            is OrderReceiveAction.InputChanged -> setState { copy(input = action.text) }
            is OrderReceiveAction.Submit -> submit(action.text)
            is OrderReceiveAction.Remove -> remove(action.code)
            is OrderReceiveAction.AcceptCached -> acceptCached()
            is OrderReceiveAction.DiscardCached -> discardCached()
            is OrderReceiveAction.DismissDialog -> setState { copy(showResumeDialog = false) }
        }
    }

    fun onInputChange(text: String) = reduce(OrderReceiveAction.InputChanged(text))
    fun onSubmit(text: String) = reduce(OrderReceiveAction.Submit(text))
    fun onRemove(code: String) = reduce(OrderReceiveAction.Remove(code))
    fun onAcceptCached()  = reduce(OrderReceiveAction.AcceptCached)
    fun onDiscardCached() = reduce(OrderReceiveAction.DiscardCached)
    fun onDismissDialog() = reduce(OrderReceiveAction.DismissDialog)


    private fun acceptCached() = viewModelScope.launch {
        val cachedBarcodes = state.value.cachedBarcodes.orEmpty()
        setState { copy(
            barcodes = cachedBarcodes,
            showResumeDialog = false,
            cachedBarcodes = null
        ) }
    }

    private fun discardCached() = viewModelScope.launch {
        cacheHelper.clearCache(CacheKeys.BARCODE_LIST)
        setState { copy(
            barcodes = emptyList(),
            showResumeDialog = false,
            cachedBarcodes = null
        ) }
    }

    private fun submit(text: String) = viewModelScope.launch {
        val code = text.trim()
        if (code.isEmpty()) {
            send(OrderReceiveEvent.Toast("กรุณาสแกน/พิมพ์บาร์โค้ด"))
            return@launch
        }
        if (code in state.value.barcodes) {
            send(OrderReceiveEvent.Toast("บาร์โค้ดนี้ถูกเพิ่มแล้ว"))
            setState { copy(input = "") }
            return@launch
        }

        val newList = state.value.barcodes + code
        setState { copy(barcodes = newList, input = "") }

        cacheHelper.saveCache(CacheKeys.BARCODE_LIST, newList)
    }

    private fun remove(code: String) = viewModelScope.launch {
        val newList = state.value.barcodes - code
        setState { copy(barcodes = newList) }

        cacheHelper.saveCache(CacheKeys.BARCODE_LIST, newList)
    }
}
