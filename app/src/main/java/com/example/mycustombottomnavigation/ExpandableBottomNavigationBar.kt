package com.example.mycustombottomnavigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import java.util.*
import kotlin.random.Random

data class BottomMenuItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    var badgeCount: Int = 0
)

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ExpandableBottomNavigationBar(
    menuItems: List<BottomMenuItem>,
    modifier: Modifier = Modifier,
    navController: NavController,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderColor: Color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
    roundedShape: RoundedCornerShape = RoundedCornerShape(
        topEnd = 16.dp, topStart = 16.dp, bottomEnd = 4.dp, bottomStart = 4.dp
    ),
    onBottomMenuItemClicked: (BottomMenuItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .graphicsLayer { shape = roundedShape }
            .clip(roundedShape)
            .border(
                2.dp,
                borderColor,
                roundedShape
            ),
        elevation = 8.dp,
        backgroundColor = backgroundColor,
    ) {
        menuItems.forEachIndexed { index, menuItem ->
            val selected = backStackEntry.value?.destination?.route == menuItem.route
            when (index) {
                0 -> {
                    BottomItem(
                        modifier = modifier.padding(start = 16.dp, top = 16.dp),
                        selected = selected,
                        menuItem = menuItem
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
                menuItems.lastIndex -> {
                    BottomItem(
                        modifier = modifier.padding(end = 16.dp, top = 16.dp),
                        selected = selected,
                        menuItem = menuItem
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
                else -> {
                    BottomItem(
                        modifier = modifier.padding(top = 16.dp),
                        selected = selected,
                        menuItem = menuItem
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun BottomItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    menuItem: BottomMenuItem,
    color: Color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
    onSelected: () -> Unit
) {
    val itemColor by animateColorAsState(targetValue = if (selected) color else color.copy(0.5f))

    var badgeNum by remember {
        mutableStateOf(menuItem.badgeCount)
    }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .animateContentSize()
            .selectable(
                selected = selected,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = true,
                role = Role.Tab,
                onClick = {
                    onSelected.invoke()
                }
            )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (badgeNum > 0) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(color)
                        .align(Alignment.TopEnd)
                )
            }
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.label,
                tint = itemColor
            )
        }
        AnimatedVisibility(visible = selected) {
            Text(
                menuItem.label.uppercase(Locale.getDefault()),
                fontSize = 12.sp,
                color = itemColor,
                letterSpacing = 4.sp
            )
            LaunchedEffect(key1 = badgeNum) {
                delay(400)
                badgeNum = 0
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    val navController = rememberNavController()
    ExpandableBottomNavigationBar(menuItems = listOfMenus, navController = navController) {

    }
}

val listOfMenus = listOf(
    BottomMenuItem(
        "Home",
        Icons.Default.Home,
        "home",
        Random.nextInt(2)
    ),
    BottomMenuItem(
        "calls",
        Icons.Default.Call,
        "calls",
        Random.nextInt(2)
    ),
    BottomMenuItem(
        "Profile",
        Icons.Default.Person,
        "profile",
        Random.nextInt(2)
    ),
    BottomMenuItem(
        "Settings",
        Icons.Default.Settings,
        "settings",
        Random.nextInt(2)
    ),
    BottomMenuItem(
        "Notification",
        Icons.Default.Notifications,
        "notification",
        Random.nextInt(2)
    )
)