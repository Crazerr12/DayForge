package com.example.data_local.db.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(category: CategoryEntity)

    @Delete
    fun deleteCategory(category: CategoryEntity)

    @Query("SELECT * FROM CATEGORIES")
    fun getAll(): List<CategoryEntity>
}