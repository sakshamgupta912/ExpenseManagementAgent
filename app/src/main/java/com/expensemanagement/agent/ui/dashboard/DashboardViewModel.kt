package com.expensemanagement.agent.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.expensemanagement.agent.data.database.ExpenseDatabase
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.data.repository.ExpenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ExpenseRepository
    private val _totalExpenses = MutableLiveData<Double>()
    val totalExpenses: LiveData<Double> = _totalExpenses
    
    val recentExpenses: LiveData<List<Expense>>
    
    init {
        val database = ExpenseDatabase.getDatabase(application)
        repository = ExpenseRepository(database.expenseDao(), database.categoryDao())
        recentExpenses = repository.getAllExpenses()
        
        calculateMonthlyTotal()
    }
    
    private fun calculateMonthlyTotal() {
        CoroutineScope(Dispatchers.IO).launch {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfMonth = calendar.time
            
            calendar.add(Calendar.MONTH, 1)
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            val endOfMonth = calendar.time
            
            val total = repository.getTotalExpensesByDateRange(startOfMonth, endOfMonth)
            _totalExpenses.postValue(total)
        }
    }
}