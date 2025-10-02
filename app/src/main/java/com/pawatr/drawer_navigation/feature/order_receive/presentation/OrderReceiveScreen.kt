package com.pawatr.drawer_navigation.feature.order_receive.presentation

import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OrderReceiveScreen(
    vm: OrderReceiveViewModel = hiltViewModel(),
    onShowMessage: (String) -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(vm) {
        vm.events.collect { ev ->
            if (ev is OrderReceiveEvent.Toast) onShowMessage(ev.msg)
        }
    }

    if (state.showResumeDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            onDismissRequest = { vm.onDismissDialog() },
            title = { Text("พบข้อมูลค้างอยู่") },
            text  = { Text("คุณต้องการทำงานต่อจากรายการก่อนหน้าหรือไม่?") },
            confirmButton = {
                TextButton(onClick = { vm.onAcceptCached() }) { Text("ทำงานต่อ") }
            },
            dismissButton = {
                TextButton(onClick = { vm.onDiscardCached() }) { Text("เริ่มใหม่") }
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(state.title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        NoImeScanField(
            text = state.input,
            onTextChange = vm::onInputChange,
            onSubmit = { code -> vm.onSubmit(code) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.barcodes, key = { it }) { code ->
                Surface(
                    tonalElevation = 1.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = code,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { vm.onRemove(code) }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "ลบ")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoImeScanField(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            EditText(context).apply {
                isSingleLine = true
                hint = "สแกนบาร์โค้ด"
                showSoftInputOnFocus = false
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                imeOptions = EditorInfo.IME_ACTION_DONE
                doAfterTextChanged { s -> onTextChange(s?.toString().orEmpty()) }
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        onSubmit(text)
                        true
                    } else false
                }
                setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_UP &&
                        (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER)
                    ) {
                        onSubmit(this.text?.toString().orEmpty())
                        true
                    } else false
                }
                post { requestFocus() }
            }
        },
        update = { edit ->
            if (edit.text?.toString() != text) {
                edit.setText(text)
                edit.setSelection(edit.text?.length ?: 0)
            }
        }
    )
}
