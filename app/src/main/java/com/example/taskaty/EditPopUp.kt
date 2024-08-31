package com.example.taskaty

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.taskaty.databinding.FragmentEditPopUpBinding
import com.example.taskaty.databinding.FragmentPopUpBinding


class EditPopUp (val taskData: TaskData,val pos:Int,val taskAdapter:TaskListAdapter): DialogFragment() {

    val taskViewModel:TaskViewModel by activityViewModels()
    private lateinit var binding: FragmentEditPopUpBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout using FragmentPopUpBinding
        binding = FragmentEditPopUpBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounden_bg)
        val enumVals=TaskPriority.values().map { it.displayName }
        val adapter= ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,enumVals)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter=adapter
        binding.nameInput.setText(taskData.name)
        binding.categoryText.setText(taskData.category)
        binding.detailsInput.setText(taskData.description)
        val position=enumVals.indexOf(taskData.priority.displayName)
        binding.prioritySpinner.setSelection(position)
        binding.editTaskBTN.setOnClickListener {
            val name= binding.nameInput.text.toString()
            val description= binding.detailsInput.text.toString()
            val category=binding.categoryText.text.toString()
            val priority=binding.prioritySpinner.selectedItem.toString()
            taskViewModel.editTaskAt(pos,TaskData(name,description,category, TaskPriority.valueOf(priority.uppercase())))
            taskAdapter.notifyItemChanged(pos)
            dismiss()
        }
        return dialog
    }




}