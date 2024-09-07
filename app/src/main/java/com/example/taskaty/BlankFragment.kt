package com.example.taskaty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskaty.databinding.FragmentBlankBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlankFragment : Fragment(), TaskListAdapter.IAdapterHelper {

    private lateinit var binding: FragmentBlankBinding
    private lateinit var taskAdapter: TaskListAdapter

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
            val popUp = PopUp()
            popUp.show((activity as AppCompatActivity).supportFragmentManager, "New Task")
        }

        // Observe LiveData from ViewModel
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.setList(tasks.toMutableList())
        }

        binding.all.setOnClickListener {
            updatePrioritySelection(binding.all)
            taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->

                taskAdapter.setList(tasks.toMutableList())
            }
        }

        binding.highPriority.setOnClickListener {
            updatePrioritySelection(binding.highPriority)
            taskViewModel.getTasksByPriority(TaskPriority.HIGH.toString()).observe(viewLifecycleOwner) { tasks ->
                taskAdapter.setList(tasks.toMutableList())
            }
        }

        binding.mediumPriority.setOnClickListener {
            updatePrioritySelection(binding.mediumPriority)
            taskViewModel.getTasksByPriority(TaskPriority.MEDIUM.toString()).observe(viewLifecycleOwner) { tasks ->
                taskAdapter.setList(tasks.toMutableList())
            }
        }

        binding.lowPriority.setOnClickListener {
            updatePrioritySelection(binding.lowPriority)
            taskViewModel.getTasksByPriority(TaskPriority.LOW.toString()).observe(viewLifecycleOwner) { tasks ->
                taskAdapter.setList(tasks.toMutableList())
            }
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
        taskViewModel.deleteTask(task)
    }


    override fun onEdit(task: TaskData, position: Int) {
        val popUp = EditPopUp(task)
        popUp.show((activity as AppCompatActivity).supportFragmentManager, "Edit Task")
    }
}
