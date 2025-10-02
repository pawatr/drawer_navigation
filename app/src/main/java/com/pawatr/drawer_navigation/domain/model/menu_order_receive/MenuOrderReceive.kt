package com.pawatr.drawer_navigation.domain.model.menu_order_receive


enum class MenuOrderReceiveType { WAREHOUSE, SUPPLIER }

data class MenuOrderReceive(
    val id: Int,
    val type: MenuOrderReceiveType,
    val title: String,
    val badgeCount: Int
)