package com.expensemanagement.agent.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.expensemanagement.agent.data.dao.CategoryDao
import com.expensemanagement.agent.data.dao.ExpenseDao
import com.expensemanagement.agent.data.model.Category
import com.expensemanagement.agent.data.model.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Expense::class, Category::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null
        
        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.categoryDao())
                    }
                }
            }
        }
        
        private suspend fun populateDatabase(categoryDao: CategoryDao) {
            val defaultCategories = listOf(
                Category("Food & Dining", "#FF8A80", "food", true),
                Category("Transportation", "#82B1FF", "transport", true),
                Category("Shopping", "#B388FF", "shopping", true),
                Category("Entertainment", "#FF8A65", "entertainment", true),
                Category("Bills & Utilities", "#FFD54F", "bills", true),
                Category("Healthcare", "#A5D6A7", "healthcare", true),
                Category("Travel", "#90CAF9", "travel", true),
                Category("Other", "#BCAAA4", "other", true)
            )
            categoryDao.insertCategories(defaultCategories)
        }
    }
}