package com.expensemanagement.agent.ui.expense

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expensemanagement.agent.data.model.Expense
import com.expensemanagement.agent.databinding.ItemExpenseBinding
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(private val onItemClick: (Expense) -> Unit) : 
    ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpenseViewHolder(private val binding: ItemExpenseBinding) : 
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: Expense) {
            binding.apply {
                tvExpenseDescription.text = expense.description
                tvExpenseCategory.text = expense.category
                tvExpenseAmount.text = String.format("$%.2f", expense.amount)
                
                // Format date
                val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                tvExpenseDate.text = dateFormat.format(expense.date)
                
                // Set category color
                val categoryColor = getCategoryColor(expense.category)
                categoryIndicator.setBackgroundColor(Color.parseColor(categoryColor))
                
                root.setOnClickListener {
                    onItemClick(expense)
                }
            }
        }
        
        private fun getCategoryColor(category: String): String {
            return when (category) {
                "Food & Dining" -> "#FF8A80"
                "Transportation" -> "#82B1FF"
                "Shopping" -> "#B388FF"
                "Entertainment" -> "#FF8A65"
                "Bills & Utilities" -> "#FFD54F"
                "Healthcare" -> "#A5D6A7"
                "Travel" -> "#90CAF9"
                else -> "#BCAAA4"
            }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}