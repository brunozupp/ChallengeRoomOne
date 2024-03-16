package com.novelitech.challengeroomone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.novelitech.challengeroomone.database.AppDatabase
import com.novelitech.challengeroomone.pages.categories.CategoriesPage
import com.novelitech.challengeroomone.pages.categories.CategoriesViewModel
import com.novelitech.challengeroomone.ui.navigation.AppNavHost
import com.novelitech.challengeroomone.ui.theme.ChallengeRoomOneTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "challengeroomone.db"
        )
            .addMigrations(
                AppDatabase.migration1To2,
            )
            .build()
    }

    private val categoriesViewModel by viewModels<CategoriesViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CategoriesViewModel(db.categoryDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeRoomOneTheme {
                AppNavHost(
                    navController = rememberNavController(),
                    categoriesViewModel = categoriesViewModel,
                )
            }
        }
    }
}
