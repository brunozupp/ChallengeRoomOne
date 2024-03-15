package com.novelitech.challengeroomone.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.novelitech.challengeroomone.database.daos.CategoryDao
import com.novelitech.challengeroomone.database.entities.CategoryEntity

@Database(
    entities = [
        CategoryEntity::class
    ],
    version = 2,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao

    companion object {

        val migration1To2 = object : Migration(startVersion = 1, endVersion = 2) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL("ALTER TABLE categories ADD COLUMN description TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}