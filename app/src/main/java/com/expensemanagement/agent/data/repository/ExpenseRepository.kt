package com.expensemanagement.agent.data.repository

import androidx.lifecycle.LiveData
import com.expensemanagement.agent.data.dao.ExpenseDao
import com.expensemanagement.agent.data.dao.CategoryDao
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.data.model.Category
import java.util.Date

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao
) {
    
    // Expense operations
    fun getAllExpenses(): LiveData<List<Expense>> = expenseDao.getAllExpenses()
    
    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)
    
    fun getExpensesByCategory(category: String): LiveData<List<Expense>> = 
        expenseDao.getExpensesByCategory(category)
    
    fun getExpensesByDateRange(startDate: Date, endDate: Date): LiveData<List<Expense>> = 
        expenseDao.getExpensesByDateRange(startDate, endDate)
    
    suspend fun getTotalExpensesByDateRange(startDate: Date, endDate: Date): Double = 
        expenseDao.getTotalExpensesByDateRange(startDate, endDate) ?: 0.0
    
    suspend fun getCategoryTotalsByDateRange(startDate: Date, endDate: Date): Map<String, Double> {
        val categoryTotals = expenseDao.getCategoryTotalsByDateRange(startDate, endDate)
        return categoryTotals.associate { it.category to it.total }
    }
    
    suspend fun insertExpense(expense: Expense): Long = expenseDao.insertExpense(expense)
    
    suspend fun updateExpense(expense: Expense) = expenseDao.updateExpense(expense)
    
    suspend fun deleteExpense(expense: Expense) = expenseDao.deleteExpense(expense)
    
    suspend fun deleteExpenseById(id: Long) = expenseDao.deleteExpenseById(id)
    
    // Category operations
    fun getAllCategories(): LiveData<List<Category>> = categoryDao.getAllCategories()
    
    suspend fun getCategoryByName(name: String): Category? = categoryDao.getCategoryByName(name)
    
    suspend fun getDefaultCategories(): List<Category> = categoryDao.getDefaultCategories()
    
    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)
    
    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
    
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
}