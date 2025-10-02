package com.pawatr.drawer_navigation.core.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceiveType

data class MenuCardUi(
    val id: Int,
    val icon: ImageVector,
    val type: MenuOrderReceiveType,
    val title: String,
    val badgeText: String?
)