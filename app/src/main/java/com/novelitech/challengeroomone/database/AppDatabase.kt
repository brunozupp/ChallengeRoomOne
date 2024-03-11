package com.novelitech.challengeroomone.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novelitech.challengeroomone.database.daos.CategoryDao
import com.novelitech.challengeroomone.database.entities.CategoryEntity

@Database(
    entities = [
        CategoryEntity::class
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao
}