package com.novelitech.challengeroomone.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.novelitech.challengeroomone.database.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: CategoryEntity) : Flow<Unit>

    @Delete
    fun delete(category: CategoryEntity) : Flow<Unit>

    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAll() : Flow<List<CategoryEntity>>
}