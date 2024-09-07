package com.example.taskaty

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.taskaty.databinding.FragmentPopUpBinding

class PopUp : DialogFragment() {

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

        binding.addtaskBTN.setOnClickListener {
            if (binding.nameInput.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Can't add task, you can't leave name empty", Toast.LENGTH_LONG).show()
            } else {
                val name = binding.nameInput.text.toString()
                val category = binding.categoryText.text.toString()
                val description = binding.detailsInput.text.toString()
                val priority = binding.prioritySpinner.selectedItem.toString()
                taskViewModel.addTask(TaskData(name, description, category, TaskPriority.valueOf(priority.uppercase())))
                Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_LONG).show()
                dismiss()
            }
        }

        return dialog
    }
}
