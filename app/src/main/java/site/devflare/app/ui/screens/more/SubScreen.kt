package site.devflare.app.ui.screens.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import site.devflare.app.ui.theme.TextPrimary

@Composable
fun BackAction(onBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onBack)
            .padding(8.dp),
    ) {
        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back", tint = TextPrimary)
    }
}
