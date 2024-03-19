package com.novelitech.challengeroomone.ui.components

import android.annotation.SuppressLint
import androidx.annotation.Nullable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.novelitech.challengeroomone.ui.theme.BlueApp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    showTopBar: Boolean = true,
    titleTopBar: String = "",
    showFloatingActionButton: Boolean = true,
    onTapFloatingActionButton: () -> Unit = {},
    body: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {

            if(showTopBar) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueApp)
                        .padding(16.dp),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        if(navController.previousBackStackEntry != null) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Go back",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clickable {
                                        navController.popBackStack()
                                    }
                            )
                        }

                        Text(
                            text = titleTopBar,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        },

        floatingActionButton = {
            if(showFloatingActionButton) {
                FloatingActionButton(
                    onClick = {
                        onTapFloatingActionButton()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new item")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            body()
        }

    }
}