package com.expensemanagement.agent.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expensemanagement.agent.databinding.FragmentDashboardBinding
import com.expensemanagement.agent.ui.expense.AddExpenseActivity
import com.expensemanagement.agent.ui.expense.ExpenseAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        
        setupRecyclerView()
        setupClickListeners()
        observeData()
        
        return root
    }
    
    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter { expense ->
            // Handle expense item click - navigate to detail view
        }
        
        binding.rvRecentExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.btnAddExpense.setOnClickListener {
            val intent = Intent(requireContext(), AddExpenseActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnScanReceipt.setOnClickListener {
            val intent = Intent(requireContext(), AddExpenseActivity::class.java)
            intent.putExtra("scan_receipt", true)
            startActivity(intent)
        }
    }
    
    private fun observeData() {
        dashboardViewModel.totalExpenses.observe(viewLifecycleOwner) { total ->
            binding.tvTotalAmount.text = String.format("$%.2f", total)
        }
        
        dashboardViewModel.recentExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses.take(5)) // Show only 5 recent expenses
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}