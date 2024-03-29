package com.novelitech.challengeroomone

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.novelitech.challengeroomone.database.AppDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test"

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
        listOf(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1_test_insertOneCategory() {

        val db = helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO categories(name) VALUES('toy')")
        }

        db.query("SELECT * FROM categories").apply {
            Truth.assertThat(moveToFirst()).isTrue()
        }
    }

    @Test
    fun migration1_test_insertRightValueInFieldName() {

        val db = helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO categories(name) VALUES('toy')")
        }

        db.query("SELECT * FROM categories").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("name"))).isEqualTo("toy")
        }
    }

    @Test
    fun migration1To2_insertColumnDescriptionWithDefaultValueEmpty() {

        helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO categories(name) VALUES('toy')")
            close()
        }

        val db = helper.runMigrationsAndValidate(DB_NAME, 2, true, AppDatabase.migration1To2)

        db.query("SELECT * FROM categories").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("description"))).isEqualTo("")
        }
    }

    @Test
    fun migration1To2_insertColumnDescriptionWithSpecificValue() {

        helper.createDatabase(DB_NAME, 1).apply {
            close()
        }

        val db = helper.runMigrationsAndValidate(DB_NAME, 2, true, AppDatabase.migration1To2)

        db.apply {
            execSQL("INSERT INTO categories(name,description) VALUES('toy','category 1')")
        }

        db.query("SELECT * FROM categories").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("description"))).isEqualTo("category 1")
        }
    }

    @Test
    fun migration2_createdDatabaseDirectlyInVersion2() {

        var db = helper.createDatabase(DB_NAME, 2).apply {
            execSQL("INSERT INTO categories(name,description) VALUES('toy','category 1')")
        }

        db.query("SELECT * FROM categories").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("name"))).isEqualTo("toy")
            Truth.assertThat(getString(getColumnIndex("description"))).isEqualTo("category 1")
        }

        db.close()
    }

    @Test
    fun migration3_createdDatabaseDirectlyInVersion3() {

        var db = helper.createDatabase(DB_NAME, 3).apply {
            execSQL("INSERT INTO categories(name,description) VALUES('toy','category 1')")
        }

        var id = 0

        db.query("SELECT id FROM categories").apply {
            moveToFirst()
            id = getInt(getColumnIndex("id"))
        }

        db.execSQL("INSERT INTO products(name,enabled,categoryId) VALUES ('Urso', 1, $id)")

        db.query("SELECT * FROM products").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("name"))).isEqualTo("Urso")
            Truth.assertThat(getInt(getColumnIndex("enabled"))).isEqualTo(1)
            Truth.assertThat(getInt(getColumnIndex("categoryId"))).isEqualTo(id)
        }
    }

    @Test
    fun migrations_updateAllManually() {

        helper.createDatabase(DB_NAME, 1).apply {
            close()
        }

        helper.runMigrationsAndValidate(DB_NAME, 2, true, AppDatabase.migration1To2)

        val db = helper.runMigrationsAndValidate(DB_NAME, 3, true, AppDatabase.migration2To3)

        db.execSQL("INSERT INTO categories(name,description) VALUES('toy','category 1')")

        var id = 0

        db.query("SELECT id FROM categories").apply {
            moveToFirst()
            id = getInt(getColumnIndex("id"))
        }

        db.execSQL("INSERT INTO products(name,enabled,categoryId) VALUES ('Urso', 1, $id)")

        db.query("SELECT * FROM products").apply {
            moveToFirst()
            Truth.assertThat(getString(getColumnIndex("name"))).isEqualTo("Urso")
            Truth.assertThat(getInt(getColumnIndex("enabled"))).isEqualTo(1)
            Truth.assertThat(getInt(getColumnIndex("categoryId"))).isEqualTo(id)
        }
    }

    @Test
    fun testAllMigrations() {

        helper.createDatabase(DB_NAME, 1).apply { close() }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            DB_NAME,
        ).addMigrations(AppDatabase.migration1To2, AppDatabase.migration2To3)
            .build()
            .apply {
                openHelper.writableDatabase.close()
            }
    }
}