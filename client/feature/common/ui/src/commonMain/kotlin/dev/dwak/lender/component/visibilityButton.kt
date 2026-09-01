package dev.dwak.lender.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import dev.dwak.lender.icons.visibility
import dev.dwak.lender.icons.visibility_off

@Composable
fun VisibilityButton(
  isVisible: Boolean,
  onClick: () -> Unit
) {
  IconButton(onClick = onClick) {
    Icon(
      imageVector = if (isVisible) visibility_off else visibility,
      contentDescription = "Reveal key",
    )
  }
}