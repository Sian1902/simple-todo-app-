package com.example.taskaty

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.taskaty.databinding.FragmentPopUpBinding

class PopUp (): DialogFragment() {

    val taskViewModel:TaskViewModel by activityViewModels()
    private lateinit var binding: FragmentPopUpBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflate the layout using FragmentPopUpBinding
        binding = FragmentPopUpBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounden_bg)
        binding.addtaskBTN.setOnClickListener {
            if (binding.nameInput.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Can't add task, you can't leave name empty", Toast.LENGTH_LONG).show()
                dismiss()
            } else {
                val name = binding.nameInput.text.toString()
                var category= binding.categoryText.text.toString()
                val description=binding.detailsInput.text.toString()
                taskViewModel.addTask(TaskData(name, description, category))
                Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
        return dialog
    }



}
