package com.pawatr.drawer_navigation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {
    @Serializable data object Home : Screen
    @Serializable data object MenuOrderReceive : Screen
    @Serializable data object OrderReceive : Screen
    @Serializable data object MenuOrderReceiveTote : Screen
    @Serializable data object OrderReceiveTote : Screen
}