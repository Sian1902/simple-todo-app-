package com.example.taskaty

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaskData")
data class TaskData(
    var name: String,
    var description: String = "",
    var category: String = "",
    var priority: TaskPriority = TaskPriority.NONE,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
