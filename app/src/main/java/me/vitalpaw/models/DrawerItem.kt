package me.vitalpaw.models

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val label: String,
    val icon: ImageVector,
    val route: String? = null,
    val onClick: (() -> Unit)? = null,

    )
