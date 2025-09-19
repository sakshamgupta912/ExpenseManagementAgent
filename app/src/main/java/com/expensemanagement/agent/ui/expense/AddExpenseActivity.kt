package com.expensemanagement.agent.ui.expense

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.expensemanagement.agent.R
import com.expensemanagement.agent.ai.ExpenseDetectionAgent
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.databinding.ActivityAddExpenseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var viewModel: AddExpenseViewModel
    private lateinit var expenseDetectionAgent: ExpenseDetectionAgent
    private var selectedDate = Date()
    
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // TODO: Launch camera for receipt scanning
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.camera_permission_required), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.title = getString(R.string.add_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        viewModel = ViewModelProvider(this)[AddExpenseViewModel::class.java]
        expenseDetectionAgent = ExpenseDetectionAgent()
        
        setupCategorySpinner()
        setupDatePicker()
        setupClickListeners()
        observeViewModel()
        
        // Check if launched for receipt scanning
        if (intent.getBooleanExtra("scan_receipt", false)) {
            checkCameraPermissionAndScan()
        }
    }
    
    private fun setupCategorySpinner() {
        val categories = arrayOf(
            "Food & Dining",
            "Transportation", 
            "Shopping",
            "Entertainment",
            "Bills & Utilities",
            "Healthcare",
            "Travel",
            "Other"
        )
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        binding.spinnerCategory.setAdapter(adapter)
    }
    
    private fun setupDatePicker() {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        binding.etDate.setText(dateFormat.format(selectedDate))
        
        binding.etDate.setOnClickListener {
            showDatePicker()
        }
    }
    
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedDate = calendar.time
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                binding.etDate.setText(dateFormat.format(selectedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    
    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            saveExpense()
        }
        
        binding.btnCancel.setOnClickListener {
            finish()
        }
        
        binding.btnScanReceipt.setOnClickListener {
            checkCameraPermissionAndScan()
        }
        
        // Update AI suggestion when description changes
        binding.etDescription.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                updateAISuggestion()
            }
        }
    }
    
    private fun checkCameraPermissionAndScan() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == 
                PackageManager.PERMISSION_GRANTED -> {
                // TODO: Launch camera for receipt scanning
                Toast.makeText(this, "Camera feature coming soon!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    private fun updateAISuggestion() {
        val description = binding.etDescription.text.toString()
        val amountStr = binding.etAmount.text.toString()
        
        if (description.isNotEmpty()) {
            val suggestedCategory = expenseDetectionAgent.categorizeExpense(description)
            binding.spinnerCategory.setText(suggestedCategory, false)
            
            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toDoubleOrNull() ?: 0.0
                val suggestion = expenseDetectionAgent.generateAISuggestion(description, amount, suggestedCategory)
                
                binding.tvAiSuggestion.text = suggestion
                binding.cardAiSuggestion.visibility = View.VISIBLE
            }
        }
    }
    
    private fun saveExpense() {
        val amount = binding.etAmount.text.toString().toDoubleOrNull()
        val description = binding.etDescription.text.toString()
        val category = binding.spinnerCategory.text.toString()
        
        when {
            amount == null || amount <= 0 -> {
                binding.etAmount.error = "Please enter a valid amount"
                return
            }
            description.isEmpty() -> {
                binding.etDescription.error = "Please enter a description"
                return
            }
            category.isEmpty() -> {
                Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
                return
            }
        }
        
        val expense = Expense(
            amount = amount,
            description = description,
            category = category,
            date = selectedDate,
            aiSuggestion = binding.tvAiSuggestion.text.toString().takeIf { it.isNotEmpty() }
        )
        
        viewModel.insertExpense(expense)
    }
    
    private fun observeViewModel() {
        viewModel.insertResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}