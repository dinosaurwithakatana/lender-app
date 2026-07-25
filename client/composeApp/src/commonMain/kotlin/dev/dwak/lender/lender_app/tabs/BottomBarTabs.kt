package dev.dwak.lender.lender_app.tabs

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.test.crowdsource
import com.example.test.groups
import com.example.test.person
import dev.dwak.lender.icons.home

enum class BottomBarTabs(val label: String, val icon: ImageVector) {
  HOME(
    "Home",
      home
  ),
  LENDS(
    "Lends",
      crowdsource
  ),
  GROUPS(
    "Groups",
      groups
  ),
  PROFILE(
    "Profile",
      person
  )
}