package com.example.taskaty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskaty.Room.TaskData
import com.example.taskaty.Helpers.TaskPriority
import com.example.taskaty.Room.TaskRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Using MediatorLiveData to observe multiple LiveData sources
    val allTasks: MediatorLiveData<List<TaskData>> = MediatorLiveData()

    init {
        // By default, observe all tasks
        getTasksByPriority(TaskPriority.NONE.toString())
    }

    // Add a task and refresh the list
    fun addTask(task: TaskData, priority: String) = viewModelScope.launch {
        repository.insertTask(task)
        getTasksByPriority(priority) // Refresh tasks after insertion
    }

    // Update a task and refresh the list
    fun updateTask(task: TaskData, priority: String) = viewModelScope.launch {
        repository.updateTask(task)
        delay(50)
        getTasksByPriority(priority)
    }

    // Delete a task and refresh the list
    fun deleteTask(task: TaskData, priority: String) = viewModelScope.launch {
        repository.deleteTask(task)
        getTasksByPriority(priority)
    }

    // Get tasks based on priority
    fun getTasksByPriority(priority: String) {
        allTasks.apply {
            // Clear previous sources
            removeSource(allTasks)

            // Add new source based on priority
            val source: LiveData<List<TaskData>> = if (priority == TaskPriority.NONE.toString()) {
                repository.getAllTasks() // Get all tasks
            } else {
                repository.getTasksByPriority(priority) // Filter by priority
            }

            // Observe and update allTasks
            addSource(source) { tasks ->
                value = tasks
            }
        }
    }
}
