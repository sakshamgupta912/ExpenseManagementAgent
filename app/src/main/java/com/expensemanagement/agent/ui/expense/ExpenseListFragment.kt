package com.expensemanagement.agent.ui.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expensemanagement.agent.databinding.FragmentExpenseListBinding

class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var expenseListViewModel: ExpenseListViewModel
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        expenseListViewModel = ViewModelProvider(this)[ExpenseListViewModel::class.java]
        
        setupRecyclerView()
        observeData()
        
        return root
    }
    
    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter { expense ->
            // Handle expense item click - navigate to detail view
        }
        
        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseAdapter
        }
    }
    
    private fun observeData() {
        expenseListViewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            if (expenses.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvExpenses.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvExpenses.visibility = View.VISIBLE
                expenseAdapter.submitList(expenses)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}