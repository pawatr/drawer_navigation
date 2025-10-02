package com.pawatr.drawer_navigation.feature.home.presentation

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pawatr.drawer_navigation.core.design.components.ActionTile
import com.pawatr.drawer_navigation.core.design.components.ActionTileCard
import com.pawatr.drawer_navigation.core.design.components.BranchTask
import com.pawatr.drawer_navigation.core.design.components.BranchTaskRow
import com.pawatr.drawer_navigation.core.design.components.FooterInfo
import com.pawatr.drawer_navigation.core.design.components.ScanPill
import com.pawatr.drawer_navigation.core.design.components.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onShowMessage: (String) -> Unit,
    onNavigateOrderReceive: () -> Unit,
    onNavigateOrderReceiveTote: () -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(vm) {
        vm.events.collect { ev ->
            when (ev) {
                is HomeEvent.Toast -> onShowMessage(ev.msg)
                is HomeEvent.NavigateToOrderReceive -> onNavigateOrderReceive()
                is HomeEvent.NavigateToOrderReceiveTote -> onNavigateOrderReceiveTote()
            }
        }
    }

    val quickActions = remember {
        listOf(
            ActionTile(Icons.Outlined.Inventory2, "รับสินค้าจากคลัง") { vm.onReceiveFromWarehouse() },
            ActionTile(Icons.Outlined.Storefront, "เติมสินค้าตามชั้นวาง") { vm.onShelfRefill() }
        )
    }

    val tasks = remember {
        listOf(
            BranchTask(
                icon = Icons.Outlined.Image,
                title = "พิม์ป้าย QR Code",
                actionText = "รอพิมพ์",
                actionColor = Color(0xFF4169E1),
                onAction = { vm.onPrintQr() }
            ),
            BranchTask(
                icon = Icons.Outlined.Image,
                title = "ตรวจสอบสินค้าภายใน Tote",
                actionText = "บันทึก",
                actionColor = Color(0xFFFF9800),
                onAction = { vm.onCheckTote() }
            ),
            BranchTask(
                icon = Icons.Outlined.Image,
                title = "รับสินค้าจากคลัง",
                actionText = "รายละเอียด",
                actionColor = Color(0xFF4169E1),
                onAction = { vm.onReceiveDetails() }
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }

        ScanPill(
            text = "สแกนเพื่อตรวจสอบสินค้า",
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionTileCard(quickActions[0], modifier = Modifier.weight(1f))
            ActionTileCard(quickActions[1], modifier = Modifier.weight(1f))
        }

        SectionHeader(
            title = "งานสาขาวันนี้",
            actionText = "ดูทั้งหมด >",
            onAction = { vm.onRefresh() }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks.size) { index ->
                BranchTaskRow(tasks[index])
            }
        }

        FooterInfo(
            branch = state.branchName,
            version = state.versionName
        )
    }
}