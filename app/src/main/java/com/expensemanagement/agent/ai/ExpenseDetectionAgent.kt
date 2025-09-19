package com.expensemanagement.agent.ai

import android.content.Context
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ExpenseDetectionAgent {
    
    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    suspend fun extractTextFromImage(imagePath: String): String = suspendCoroutine { continuation ->
        try {
            val image = InputImage.fromFilePath(null, File(imagePath))
            textRecognizer.process(image)
                .addOnSuccessListener { visionText ->
                    continuation.resume(visionText.text)
                }
                .addOnFailureListener { exception ->
                    continuation.resume("")
                }
        } catch (e: IOException) {
            continuation.resume("")
        }
    }
    
    fun categorizeExpense(description: String): String {
        val lowercaseDescription = description.lowercase()
        
        return when {
            containsKeywords(lowercaseDescription, foodKeywords) -> "Food & Dining"
            containsKeywords(lowercaseDescription, transportKeywords) -> "Transportation"
            containsKeywords(lowercaseDescription, shoppingKeywords) -> "Shopping"
            containsKeywords(lowercaseDescription, entertainmentKeywords) -> "Entertainment"
            containsKeywords(lowercaseDescription, billsKeywords) -> "Bills & Utilities"
            containsKeywords(lowercaseDescription, healthcareKeywords) -> "Healthcare"
            containsKeywords(lowercaseDescription, travelKeywords) -> "Travel"
            else -> "Other"
        }
    }
    
    fun extractAmountFromText(text: String): Double? {
        // Regular expressions to find amounts in different formats
        val patterns = listOf(
            "\\$([0-9]+\\.?[0-9]*)".toRegex(),
            "([0-9]+\\.?[0-9]*)\\s*\\$".toRegex(),
            "total:?\\s*\\$?([0-9]+\\.?[0-9]*)".toRegex(RegexOption.IGNORE_CASE),
            "amount:?\\s*\\$?([0-9]+\\.?[0-9]*)".toRegex(RegexOption.IGNORE_CASE)
        )
        
        for (pattern in patterns) {
            val match = pattern.find(text)
            if (match != null) {
                return match.groupValues[1].toDoubleOrNull()
            }
        }
        return null
    }
    
    fun generateAISuggestion(description: String, amount: Double, category: String): String {
        val suggestions = mutableListOf<String>()
        
        // Budget-based suggestions
        if (amount > 100) {
            suggestions.add("This is a large expense. Consider if it's necessary.")
        }
        
        // Category-specific suggestions
        when (category) {
            "Food & Dining" -> {
                if (amount > 50) suggestions.add("Consider cooking at home to save money.")
            }
            "Transportation" -> {
                suggestions.add("Look for public transport alternatives to reduce costs.")
            }
            "Shopping" -> {
                suggestions.add("Check if you really need this item or if you can find it cheaper elsewhere.")
            }
            "Entertainment" -> {
                suggestions.add("Look for free or low-cost entertainment alternatives.")
            }
        }
        
        return if (suggestions.isNotEmpty()) {
            suggestions.joinToString(" ")
        } else {
            "Keep tracking your expenses to better understand your spending patterns."
        }
    }
    
    private fun containsKeywords(text: String, keywords: List<String>): Boolean {
        return keywords.any { keyword -> text.contains(keyword) }
    }
    
    companion object {
        private val foodKeywords = listOf(
            "restaurant", "food", "pizza", "burger", "coffee", "cafe", "dining", 
            "lunch", "dinner", "breakfast", "grocery", "supermarket", "starbucks",
            "mcdonald", "subway", "kfc", "domino", "delivery", "takeout"
        )
        
        private val transportKeywords = listOf(
            "gas", "fuel", "uber", "lyft", "taxi", "bus", "train", "metro", 
            "parking", "toll", "car", "vehicle", "transport", "flight", "airline"
        )
        
        private val shoppingKeywords = listOf(
            "amazon", "store", "mall", "shopping", "clothes", "clothing", "shoes",
            "electronics", "target", "walmart", "costco", "purchase", "buy"
        )
        
        private val entertainmentKeywords = listOf(
            "movie", "cinema", "theater", "concert", "game", "netflix", "spotify",
            "entertainment", "fun", "party", "bar", "club", "music", "streaming"
        )
        
        private val billsKeywords = listOf(
            "electric", "electricity", "water", "gas", "internet", "phone", "cable",
            "insurance", "rent", "mortgage", "utility", "bill", "payment", "subscription"
        )
        
        private val healthcareKeywords = listOf(
            "doctor", "hospital", "pharmacy", "medicine", "health", "medical",
            "dental", "clinic", "prescription", "healthcare"
        )
        
        private val travelKeywords = listOf(
            "hotel", "flight", "travel", "vacation", "trip", "airbnb", "booking",
            "rental", "cruise", "tour", "airline", "airport"
        )
    }
}