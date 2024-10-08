package com.example.taskaty.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.taskaty.R
import com.example.taskaty.Room.TaskData
import com.example.taskaty.databinding.FragmentDetailsPopupBinding

class details_popup(val item: TaskData) : DialogFragment() {

    private var _binding: FragmentDetailsPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDetailsPopupBinding.inflate(LayoutInflater.from(context))

        // Use the inflated view for the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        // Populate the views with data
        binding.name.text = item.name
        binding.category.text = item.category
        binding.details.text = item.description
        binding.priority.text=item.priority.displayName

        dialog.window?.setBackgroundDrawableResource(R.drawable.rounden_bg) // Customize dialog background
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
