package com.example.taskaty.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.taskaty.R
import com.example.taskaty.Room.TaskData
import com.example.taskaty.Room.TaskDatabase
import com.example.taskaty.Helpers.TaskPriority
import com.example.taskaty.Room.TaskRepository
import com.example.taskaty.viewModel.TaskViewModel
import com.example.taskaty.viewModel.TaskViewModelFactory
import com.example.taskaty.databinding.FragmentEditPopUpBinding
import kotlinx.coroutines.launch

class EditPopUp(private val taskData: TaskData, private val initialPriority: TaskPriority) : DialogFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getData(requireContext()).taskDao()))
    }
    private lateinit var binding: FragmentEditPopUpBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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

        // Initialize input fields with current task data
        binding.nameInput.setText(taskData.name)
        binding.categoryText.setText(taskData.category)
        binding.detailsInput.setText(taskData.description)

        // Set the spinner selection to the task's current priority
        val position = enumVals.indexOf(taskData.priority.displayName)
        if (position != -1) {
            binding.prioritySpinner.setSelection(position)
        }

        binding.editTaskBTN.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.detailsInput.text.toString()
            val category = binding.categoryText.text.toString()
            val priorityString = binding.prioritySpinner.selectedItem.toString()
            val priority = TaskPriority.values().firstOrNull { it.displayName == priorityString }

            if (priority != null) {
                val updatedTask = TaskData(name, description, category, priority, taskData.id)

                lifecycleScope.launch {
                    taskViewModel.updateTask(updatedTask, initialPriority.toString())
                }

                dismiss()
            } else {
                // Handle invalid priority selection
                Toast.makeText(requireContext(), "Invalid priority selected", Toast.LENGTH_SHORT).show()
            }
        }

        return dialog
    }
}

