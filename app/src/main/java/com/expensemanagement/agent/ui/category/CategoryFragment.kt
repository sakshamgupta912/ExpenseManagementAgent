package com.expensemanagement.agent.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(context)
        textView.text = "Categories\n\nThis section will show expense categories and allow you to manage them."
        textView.textSize = 16f
        textView.setPadding(32, 32, 32, 32)
        return textView
    }
}