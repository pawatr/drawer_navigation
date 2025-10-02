package com.pawatr.drawer_navigation.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

class AppNavigator(private val backStack: NavBackStack<NavKey>) : Navigator {

    override fun toHome() {
        backStack.add(Screen.Home)
    }

    override fun toMenuOrderReceive() {
        backStack.add(Screen.MenuOrderReceive)
    }

    override fun toOrderReceive() {
        backStack.add(Screen.OrderReceive)
    }

    override fun toOrderReceiveTote() {
        backStack.add(Screen.OrderReceiveTote)
    }

    override fun back(): Boolean {
        return if (backStack.size > 1) {
            backStack.removeLastOrNull()
            true
        } else {
            false
        }
    }

    override fun replaceAll(root: Screen) {
        backStack.clear()
        backStack.add(root)
    }
}