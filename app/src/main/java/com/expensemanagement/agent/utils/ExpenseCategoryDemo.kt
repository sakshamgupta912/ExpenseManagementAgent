package com.expensemanagement.agent.utils

import com.expensemanagement.agent.ai.ExpenseDetectionAgent

/**
 * Demo utility to showcase the AI expense categorization functionality
 * This can be used to test the AI agent without requiring camera functionality
 */
class ExpenseCategoryDemo {
    
    private val aiAgent = ExpenseDetectionAgent()
    
    fun demonstrateAICategorizationWithExamples(): List<DemoExpense> {
        val sampleExpenses = listOf(
            "Coffee at Starbucks",
            "Uber ride to airport",
            "Grocery shopping at Walmart", 
            "Movie tickets for Avengers",
            "Electric bill payment",
            "Doctor appointment",
            "Flight to New York",
            "Amazon purchase",
            "Gas station fill-up",
            "Pizza delivery",
            "Netflix subscription",
            "Parking meter"
        )
        
        return sampleExpenses.map { description ->
            val category = aiAgent.categorizeExpense(description)
            val amount = generateSampleAmount(category)
            val suggestion = aiAgent.generateAISuggestion(description, amount, category)
            
            DemoExpense(
                description = description,
                suggestedCategory = category,
                amount = amount,
                aiSuggestion = suggestion
            )
        }
    }
    
    private fun generateSampleAmount(category: String): Double {
        return when (category) {
            "Food & Dining" -> (5.0..50.0).random()
            "Transportation" -> (10.0..100.0).random()
            "Shopping" -> (20.0..200.0).random()
            "Entertainment" -> (15.0..80.0).random()
            "Bills & Utilities" -> (50.0..300.0).random()
            "Healthcare" -> (30.0..500.0).random()
            "Travel" -> (100.0..1000.0).random()
            else -> (10.0..100.0).random()
        }.let { 
            Math.round(it * 100.0) / 100.0 // Round to 2 decimal places
        }
    }
    
    data class DemoExpense(
        val description: String,
        val suggestedCategory: String,
        val amount: Double,
        val aiSuggestion: String
    )
}

fun ClosedRange<Double>.random(): Double {
    return Math.random() * (endInclusive - start) + start
}