package com.example.taskaty

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val allTasks: LiveData<List<TaskData>> = repository.getAllTasks()

    fun addTask(task: TaskData) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: TaskData) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: TaskData) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun getTasksByPriority(priority: String): LiveData<List<TaskData>> {
        return repository.getTasksByPriority(priority)
    }
}
