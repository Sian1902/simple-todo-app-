package com.example.taskaty

import java.util.UUID

data class TaskData(var name:String,var description:String="",var category:String="",var priority: TaskPriority=TaskPriority.NONE, val id: String = UUID.randomUUID().toString())
