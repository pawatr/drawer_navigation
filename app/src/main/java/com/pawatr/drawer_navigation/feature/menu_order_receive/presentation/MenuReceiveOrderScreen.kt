package com.pawatr.drawer_navigation.feature.menu_order_receive.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawatr.drawer_navigation.core.design.components.CardMenu

@Composable
fun MenuReceiveOrderScreen(
    vm: MenuReceiveOrderViewModel = hiltViewModel(),
    onNavigateOrderReceive: () -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(vm) {
        vm.events.collect { ev ->
            when (ev) {
                MenuOrderReceiveEvent.NavigateToOrderReceive -> onNavigateOrderReceive()
            }
        }
    }

    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(state.menuList, key = { it.id}) { item ->
            CardMenu(
                item = item,
            ) { item ->
                vm.onClickMenu(item)
            }
        }
    }
}