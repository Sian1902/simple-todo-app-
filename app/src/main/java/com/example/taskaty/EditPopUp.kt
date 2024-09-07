package com.example.taskaty

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import com.example.taskaty.databinding.FragmentEditPopUpBinding
import kotlinx.coroutines.launch

class EditPopUp(private val taskData: TaskData) : DialogFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getData(requireContext()).taskDao()))
    }
    private lateinit var binding: FragmentEditPopUpBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout using FragmentEditPopUpBinding
        binding = FragmentEditPopUpBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounden_bg)

        // Set up Spinner adapter
        val enumVals = TaskPriority.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, enumVals)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        // Set initial values
        binding.nameInput.setText(taskData.name)
        binding.categoryText.setText(taskData.category)
        binding.detailsInput.setText(taskData.description)
        val position = enumVals.indexOf(taskData.priority.displayName)
        binding.prioritySpinner.setSelection(position)

        // Set button click listener
        binding.editTaskBTN.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.detailsInput.text.toString()
            val category = binding.categoryText.text.toString()
            val priority = binding.prioritySpinner.selectedItem.toString()
            val updatedTask = TaskData(name, description, category, TaskPriority.valueOf(priority.uppercase()),taskData.id)

            // Use lifecycleScope instead of GlobalScope
            lifecycleScope.launch {
                taskViewModel.updateTask(updatedTask)
            }

            dismiss()
        }

        return dialog
    }
}
