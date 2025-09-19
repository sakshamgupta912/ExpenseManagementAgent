package com.expensemanagement.agent

import com.expensemanagement.agent.ai.ExpenseDetectionAgent
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExpenseDetectionTest {
    
    private val expenseDetectionAgent = ExpenseDetectionAgent()
    
    @Test
    fun testExpenseCategorizationFood() {
        val description = "Coffee at Starbucks"
        val category = expenseDetectionAgent.categorizeExpense(description)
        assertEquals("Food & Dining", category)
    }
    
    @Test
    fun testExpenseCategorizationTransport() {
        val description = "Uber ride to downtown"
        val category = expenseDetectionAgent.categorizeExpense(description)
        assertEquals("Transportation", category)
    }
    
    @Test
    fun testExpenseCategorizationShopping() {
        val description = "Amazon purchase electronics"
        val category = expenseDetectionAgent.categorizeExpense(description)
        assertEquals("Shopping", category)
    }
    
    @Test
    fun testAmountExtractionFromText() {
        val receiptText = "Thank you for shopping!\nTotal: $15.67\nHave a great day!"
        val amount = expenseDetectionAgent.extractAmountFromText(receiptText)
        assertEquals(15.67, amount!!, 0.01)
    }
    
    @Test
    fun testAmountExtractionWithDollarSign() {
        val receiptText = "Amount paid: $42.50"
        val amount = expenseDetectionAgent.extractAmountFromText(receiptText)
        assertEquals(42.50, amount!!, 0.01)
    }
    
    @Test
    fun testAISuggestionGeneration() {
        val description = "Expensive restaurant dinner"
        val amount = 85.0
        val category = "Food & Dining"
        val suggestion = expenseDetectionAgent.generateAISuggestion(description, amount, category)
        
        assertTrue("Suggestion should contain budget advice", 
            suggestion.contains("large expense") || suggestion.contains("cooking at home"))
    }
    
    @Test
    fun testDefaultCategoryForUnknownExpense() {
        val description = "Random unknown expense type"
        val category = expenseDetectionAgent.categorizeExpense(description)
        assertEquals("Other", category)
    }
}