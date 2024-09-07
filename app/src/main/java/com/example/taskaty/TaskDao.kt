package com.example.taskaty
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskaty.TaskData

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: TaskData)

    @Update
    suspend fun updateTask(task: TaskData)

    @Delete
    suspend fun deleteTask(task: TaskData) // Assuming you want to delete the entire task object

    @Query("SELECT * FROM TaskData WHERE priority = :priority")
     fun selectPriority(priority: String):LiveData< List<TaskData>>

    @Query("SELECT * FROM TaskData")
    fun getAllTasks(): LiveData<List<TaskData>>
}
