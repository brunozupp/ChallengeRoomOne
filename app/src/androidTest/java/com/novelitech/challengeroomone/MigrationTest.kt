package com.novelitech.challengeroomone

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
}