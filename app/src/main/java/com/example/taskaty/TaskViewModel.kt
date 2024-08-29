package com.example.taskaty

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskViewModel:ViewModel(){
    private val _taskList:MutableLiveData<MutableList<TaskData>> = MutableLiveData(mutableListOf())
    val taskList:MutableLiveData<MutableList<TaskData>> get() = _taskList
    fun addTask(task:TaskData){
        val currList = _taskList.value?.toMutableList() ?: mutableListOf()
        currList.add(task)
        _taskList.value = currList
    }
    fun deleteAt(pos:Int){
        _taskList.value?.removeAt(pos)
    }
    fun getTaskAt(pos: Int): TaskData? {
        return _taskList.value?.getOrNull(pos)
    }

    fun setList(taskList:MutableList<TaskData>){
        _taskList.value?.clear()
        _taskList.value?.addAll(taskList)
    }
    fun saveTaskList(context: Context) {
        val gson = Gson()
        val jsonString = gson.toJson(_taskList.value)
        val sharedPreferences = context.getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("taskList", jsonString)
        editor.apply()
    }
    fun getTaskList(context: Context) {
        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("taskList", null)
        val type = object : TypeToken<MutableList<TaskData>>() {}.type
        _taskList.value=gson.fromJson(savedString, type) ?: mutableListOf<TaskData>()
    }


}