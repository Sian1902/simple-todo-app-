package com.example.taskaty.Room

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    // Insert a task
    suspend fun insertTask(task: TaskData) {
        taskDao.insertTask(task)
    }

    // Update a task
    suspend fun updateTask(task: TaskData) {
        taskDao.updateTask(task)
    }

    // Delete a task
    suspend fun deleteTask(task: TaskData) {
        taskDao.deleteTask(task)
    }

    // Get all tasks (returns LiveData)
    fun getAllTasks(): LiveData<List<TaskData>> {
        return taskDao.getAllTasks()
    }

    // Get tasks by priority (returns LiveData)
    fun getTasksByPriority(priority: String): LiveData<List<TaskData>> {
        return taskDao.selectPriority(priority)
    }
}
