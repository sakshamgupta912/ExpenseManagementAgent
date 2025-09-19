package com.expensemanagement.agent.ui.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.expensemanagement.agent.data.database.ExpenseDatabase
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.data.repository.ExpenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExpenseViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ExpenseRepository
    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> = _insertResult
    
    init {
        val database = ExpenseDatabase.getDatabase(application)
        repository = ExpenseRepository(database.expenseDao(), database.categoryDao())
    }
    
    fun insertExpense(expense: Expense) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertExpense(expense)
                _insertResult.postValue(true)
            } catch (e: Exception) {
                _insertResult.postValue(false)
            }
        }
    }
}