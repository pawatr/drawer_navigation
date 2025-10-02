package com.pawatr.drawer_navigation.core.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pawatr.drawer_navigation.core.model.MenuCardUi

@Composable
fun CardMenu(
    item: MenuCardUi,
    modifier: Modifier = Modifier,
    cardCorner: Dp = 20.dp,
    badgeColor: Color = Color(0xFFB71C1C),
    onClick: (MenuCardUi) -> Unit = {}
) {
    val shape = RoundedCornerShape(cardCorner)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        ElevatedCard(
            onClick = { onClick.invoke(item) },
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp, bottom = 14.dp, start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(44.dp)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        item.badgeText?.takeIf { it.isNotEmpty() }?.let { txt ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x =  8.dp,
                        y = (-10).dp
                    )
                    .clip(CircleShape)
                    .background(badgeColor)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = txt,
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}