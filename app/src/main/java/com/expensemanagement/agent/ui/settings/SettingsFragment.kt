package com.expensemanagement.agent.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(context)
        textView.text = "Settings\n\nThis section will allow you to configure app preferences, budget limits, and export options."
        textView.textSize = 16f
        textView.setPadding(32, 32, 32, 32)
        return textView
    }
}