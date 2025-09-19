package com.expensemanagement.agent.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val name: String,
    val color: String,
    val icon: String,
    val isDefault: Boolean = false,
    val monthlyBudget: Double? = null
)