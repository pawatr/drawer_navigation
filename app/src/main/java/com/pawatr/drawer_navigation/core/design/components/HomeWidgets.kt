package com.pawatr.drawer_navigation.core.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// ---------- Models ----------
data class ActionTile(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit = {}
)

data class BranchTask(
    val icon: ImageVector = Icons.Outlined.Image,
    val title: String,
    val actionText: String,
    val actionColor: Color,
    val onAction: () -> Unit = {}
)

// ---------- Widgets ----------
@Composable
fun ScanPill(
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp)
) {
    val borderColor = MaterialTheme.colorScheme.outlineVariant
    Box(
        modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(shape)
            .border(1.dp, borderColor, shape)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun ActionTileCard(
    item: ActionTile,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = item.onClick,
        modifier = modifier
            .widthIn(min = 120.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            // Icon area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(42.dp)
                )
            }
            // Title area with subtle bg
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f))
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    actionText: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.weight(1f))
        TextButton(onClick = onAction) {
            Text(actionText)
        }
    }
}

@Composable
fun BranchTaskRow(
    task: BranchTask,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.40f),
        shape = shape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = task.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = task.onAction,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = task.actionColor,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(task.actionText, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun FooterInfo(
    branch: String,
    version: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "สาขา: $branch",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Text(
            text = "เวอร์ชั่น: $version",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
