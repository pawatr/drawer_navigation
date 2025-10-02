package com.pawatr.drawer_navigation.navigation

interface Navigator {
    fun toHome()
    fun toMenuOrderReceive()
    fun toOrderReceive()
    fun toOrderReceiveTote()
    fun back(): Boolean
    fun replaceAll(root: Screen)
}