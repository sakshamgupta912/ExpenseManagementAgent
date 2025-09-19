package com.expensemanagement.agent.ui.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.expensemanagement.agent.data.database.ExpenseDatabase
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.data.repository.ExpenseRepository

class ExpenseListViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ExpenseRepository
    val expenses: LiveData<List<Expense>>
    
    init {
        val database = ExpenseDatabase.getDatabase(application)
        repository = ExpenseRepository(database.expenseDao(), database.categoryDao())
        expenses = repository.getAllExpenses()
    }
}