package com.example.data_local.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data_local.db.category.CategoryDao
import com.example.data_local.db.category.CategoryEntity
import com.example.data_local.db.task.TaskDao
import com.example.data_local.db.task.TaskEntity
import com.example.data_local.utils.Converters

@Database(
    entities = [TaskEntity::class, CategoryEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    abstract fun categoryDao(): CategoryDao
}