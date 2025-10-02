package com.pawatr.drawer_navigation.feature.menu_order_receive_tote.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawatr.drawer_navigation.core.design.components.CardMenu
import com.pawatr.drawer_navigation.core.model.MenuCardUi
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceive
import com.pawatr.drawer_navigation.domain.model.menu_order_receive.MenuOrderReceiveType
import com.pawatr.drawer_navigation.feature.menu_order_receive.presentation.MenuOrderReceiveEvent
import com.pawatr.drawer_navigation.feature.menu_order_receive_tote.model.MenuOrderReceiveToteUi
import com.pawatr.drawer_navigation.feature.order_receive_tote.presentation.OrderReceiveToteViewModel

@Composable
fun MenuOrderReceiveToteScreen(
    vm: MenuOrderReceiveToteViewModel = hiltViewModel(),
    onNavigateOrderReceiveTote: () -> Unit
) {
    LaunchedEffect(vm) {
        vm.events.collect { ev ->
            when (ev) {
                MenuOrderReceiveToteEvent.NavigateToOrderReceiveTote -> onNavigateOrderReceiveTote()
            }
        }
    }

    val items = listOf(
        MenuCardUi(
            id = 1,
            icon = Icons.Outlined.Inventory2,
            title = "ตรวจสินค้าภายใน Tote",
            type = MenuOrderReceiveType.WAREHOUSE,
            badgeText = null
        ),
    )

    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items, key = { it.id}) { item ->
            CardMenu(
                item = item,
            ) { item ->
                vm.onClickMenu(item)
            }
        }
    }
}