package com.novelitech.challengeroomone.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.novelitech.challengeroomone.database.daos.CategoryDao
import com.novelitech.challengeroomone.database.daos.ProductDao
import com.novelitech.challengeroomone.database.entities.CategoryEntity
import com.novelitech.challengeroomone.database.entities.ProductEntity

@Database(
    entities = [
        CategoryEntity::class,
        ProductEntity::class,
    ],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao

    abstract val productDao: ProductDao

    companion object {

        val migration1To2 = object : Migration(startVersion = 1, endVersion = 2) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL("ALTER TABLE categories ADD COLUMN description TEXT NOT NULL DEFAULT ''")
            }
        }

        val migration2To3 = object : Migration(startVersion = 2, endVersion = 3) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL("CREATE TABLE IF NOT EXISTS products(" +
                        "id INTEGER NOT NULL PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "enabled INTEGER NOT NULL," +
                        "categoryId INTEGER NOT NULL," +
                        "FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE)")
            }
        }
    }
}