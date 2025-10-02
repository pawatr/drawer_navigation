package com.pawatr.drawer_navigation.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface UiAction; interface UiEvent; interface UiState

abstract class BaseViewModel<A: UiAction, E: UiEvent, S: UiState> : ViewModel() {
    protected val _state = MutableStateFlow(initialState())
    val state: StateFlow<S> = _state.asStateFlow()
    private val _events = Channel<E>(Channel.BUFFERED)
    val events: Flow<E> = _events.receiveAsFlow()

    abstract fun initialState(): S
    abstract fun reduce(action: A)

    protected fun setState(reducer: S.() -> S) { _state.update(reducer) }
    protected suspend fun send(event: E) { _events.send(event) }
}