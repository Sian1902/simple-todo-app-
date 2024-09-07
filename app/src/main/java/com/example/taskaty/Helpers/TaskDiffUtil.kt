package com.example.taskaty.Helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.taskaty.Room.TaskData

class TaskDiffUtil(
    private val oldList: List<TaskData>,
    private val newList: List<TaskData>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Assuming TaskData has a unique ID
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare the entire item to check if contents are the same
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
