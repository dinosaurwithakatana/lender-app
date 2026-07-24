package com.example.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
public val shelves: ImageVector
  get() {
    if (_shelves != null) {
      return _shelves!!
    }
    _shelves =
      ImageVector.Builder(
          name = "shelves",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(3f, 23f)
            verticalLineTo(1f)
            horizontalLineTo(5f)
            verticalLineTo(3f)
            horizontalLineTo(19f)
            verticalLineTo(1f)
            horizontalLineToRelative(2f)
            verticalLineTo(23f)
            horizontalLineTo(19f)
            verticalLineTo(21f)
            horizontalLineTo(5f)
            verticalLineToRelative(2f)
            horizontalLineTo(3f)
            close()
            moveTo(5f, 11f)
            horizontalLineTo(7f)
            verticalLineTo(7f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(4f)
            horizontalLineToRelative(6f)
            verticalLineTo(5f)
            horizontalLineTo(5f)
            verticalLineToRelative(6f)
            close()
            moveToRelative(0f, 8f)
            horizontalLineToRelative(6f)
            verticalLineTo(15f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(4f)
            horizontalLineToRelative(2f)
            verticalLineTo(13f)
            horizontalLineTo(5f)
            verticalLineToRelative(6f)
            close()
            moveTo(9f, 11f)
            horizontalLineToRelative(2f)
            verticalLineTo(9f)
            horizontalLineTo(9f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(4f, 8f)
            horizontalLineToRelative(2f)
            verticalLineTo(17f)
            horizontalLineTo(13f)
            verticalLineToRelative(2f)
            close()
            moveTo(9f, 11f)
            horizontalLineToRelative(2f)
            horizontalLineTo(9f)
            close()
            moveToRelative(4f, 8f)
            horizontalLineToRelative(2f)
            horizontalLineTo(13f)
            close()
          }
        }
        .build()
    return _shelves!!
  }

private var _shelves: ImageVector? = null
