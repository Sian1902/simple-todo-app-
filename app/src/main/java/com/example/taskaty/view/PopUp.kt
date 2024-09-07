package com.example.taskaty.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.taskaty.R
import com.example.taskaty.Room.TaskData
import com.example.taskaty.Room.TaskDatabase
import com.example.taskaty.Helpers.TaskPriority
import com.example.taskaty.Room.TaskRepository
import com.example.taskaty.viewModel.TaskViewModel
import com.example.taskaty.viewModel.TaskViewModelFactory
import com.example.taskaty.databinding.FragmentPopUpBinding

class PopUp(private val initialPriority: TaskPriority) : DialogFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getData(requireContext()).taskDao()))
    }
    private lateinit var binding: FragmentPopUpBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout using FragmentPopUpBinding
        binding = FragmentPopUpBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounden_bg)

        // Set up Spinner adapter
        val enumVals = TaskPriority.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, enumVals)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        // Set the initial priority in the spinner
        binding.prioritySpinner.setSelection(enumVals.indexOf(initialPriority.displayName))

        binding.addtaskBTN.setOnClickListener {
            val name = binding.nameInput.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Can't add task, you can't leave name empty", Toast.LENGTH_LONG).show()
            } else {
                val category = binding.categoryText.text.toString()
                val description = binding.detailsInput.text.toString()
                val priorityString = binding.prioritySpinner.selectedItem.toString()
                val priority = TaskPriority.values().firstOrNull { it.displayName == priorityString }
                if (priority != null) {
                    val task = TaskData(name, description, category, priority)
                    taskViewModel.addTask(task, priority.toString())
                    Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_LONG).show()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Invalid priority selected", Toast.LENGTH_LONG).show()
                }
            }
        }

        return dialog
    }
}

