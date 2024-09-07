package com.example.taskaty.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskaty.R
import com.example.taskaty.Room.TaskData
import com.example.taskaty.Room.TaskDatabase
import com.example.taskaty.Helpers.TaskListAdapter
import com.example.taskaty.Helpers.TaskPriority
import com.example.taskaty.Room.TaskRepository
import com.example.taskaty.viewModel.TaskViewModel
import com.example.taskaty.viewModel.TaskViewModelFactory
import com.example.taskaty.databinding.FragmentBlankBinding

class BlankFragment : Fragment(), TaskListAdapter.IAdapterHelper {

    private lateinit var binding: FragmentBlankBinding
    private lateinit var taskAdapter: TaskListAdapter
    private  var selectedPriority= TaskPriority.NONE
    // Initialize ViewModel using activityViewModels() to share with other fragments
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getData(requireContext()).taskDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskAdapter = TaskListAdapter(this)
        binding.taskRecycler.adapter = taskAdapter
        binding.taskRecycler.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener {
            val popUp = PopUp(selectedPriority)
            popUp.show((activity as AppCompatActivity).supportFragmentManager, "New Task")
        }

        // Observe LiveData from ViewModel
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.setList(tasks.toMutableList())
        }

        binding.all.setOnClickListener {
            selectedPriority= TaskPriority.NONE
            updatePrioritySelection(binding.all)
            taskViewModel.getTasksByPriority(TaskPriority.NONE.toString())
        }

        binding.highPriority.setOnClickListener {
            selectedPriority= TaskPriority.HIGH
            updatePrioritySelection(binding.highPriority)
            taskViewModel.getTasksByPriority(TaskPriority.HIGH.toString())
        }

        binding.mediumPriority.setOnClickListener {
            selectedPriority= TaskPriority.MEDIUM
            updatePrioritySelection(binding.mediumPriority)
            taskViewModel.getTasksByPriority(TaskPriority.MEDIUM.toString())
        }

        binding.lowPriority.setOnClickListener {
            selectedPriority= TaskPriority.LOW
            updatePrioritySelection(binding.lowPriority)
            taskViewModel.getTasksByPriority(TaskPriority.LOW.toString())
        }
    }

    private fun updatePrioritySelection(selectedTextView: TextView) {
        val selectedTextColor = ContextCompat.getColor(requireContext(), R.color.blue)
        val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        val selectedBackgroundColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        val defaultBackgroundColor = ContextCompat.getColor(requireContext(), R.color.blue)

        val priorityTextViews = listOf(
            binding.all,
            binding.highPriority,
            binding.mediumPriority,
            binding.lowPriority
        )

        priorityTextViews.forEach { textView ->
            if (textView == selectedTextView) {
                textView.setTextColor(selectedTextColor)
                textView.setBackgroundColor(selectedBackgroundColor)
            } else {
                textView.setTextColor(defaultTextColor)
                textView.setBackgroundColor(defaultBackgroundColor)
            }
        }
    }

    override fun onItemClick(task: TaskData) {
        val detailsPopup = details_popup(task)
        detailsPopup.show((activity as AppCompatActivity).supportFragmentManager, "Task Details")
    }

    override fun onItemDeleted(task: TaskData) {
        taskViewModel.deleteTask(task,selectedPriority.toString())

    }


    override fun onEdit(task: TaskData, position: Int) {
        val popUp = EditPopUp(task,selectedPriority)
        popUp.show((activity as AppCompatActivity).supportFragmentManager, "Edit Task")
    }


}
