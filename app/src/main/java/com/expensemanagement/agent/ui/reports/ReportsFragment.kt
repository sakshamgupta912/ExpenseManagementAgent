package com.expensemanagement.agent.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ReportsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(context)
        textView.text = "Reports & Analytics\n\nThis section will show charts, graphs, and financial insights based on your spending patterns."
        textView.textSize = 16f
        textView.setPadding(32, 32, 32, 32)
        return textView
    }
}