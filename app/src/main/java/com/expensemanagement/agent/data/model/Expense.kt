package com.expensemanagement.agent.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val category: String,
    val date: Date,
    val isRecurring: Boolean = false,
    val receiptImagePath: String? = null,
    val aiSuggestion: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)