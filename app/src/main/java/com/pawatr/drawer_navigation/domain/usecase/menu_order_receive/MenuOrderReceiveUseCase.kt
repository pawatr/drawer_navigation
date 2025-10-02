package com.pawatr.drawer_navigation.domain.usecase.menu_order_receive

import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceive
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceiveType
import jakarta.inject.Inject

class MenuOrderReceiveUseCase @Inject constructor() {
    operator fun invoke(): List<MenuOrderReceive> {
        return listOf(
            MenuOrderReceive(id = 1, type = MenuOrderReceiveType.WAREHOUSE, title = "รับสินค้าจากคลัง",     badgeCount = 0),
            MenuOrderReceive(id = 2, type = MenuOrderReceiveType.SUPPLIER,  title = "รับสินค้าจากผู้จำหน่าย", badgeCount = 0)
        )
    }
}