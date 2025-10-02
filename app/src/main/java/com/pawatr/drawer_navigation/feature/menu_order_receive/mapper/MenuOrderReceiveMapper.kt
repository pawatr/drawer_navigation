package com.pawatr.drawer_navigation.feature.menu_order_receive.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Storefront
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceive
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceiveType
import com.pawatr.drawer_navigation.core.model.MenuCardUi

fun MenuOrderReceive.toUi(): MenuCardUi {
    val icon = when (type) {
        MenuOrderReceiveType.WAREHOUSE -> Icons.Outlined.Inventory2
        else -> Icons.Outlined.Storefront
    }
    val badge = if (badgeCount > 0) badgeCount.toString() else null
    return MenuCardUi(id = id, icon = icon, type = type, title = title, badgeText = badge)
}