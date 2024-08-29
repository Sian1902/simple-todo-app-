package com.example.taskaty

import androidx.recyclerview.widget.DiffUtil

class TaskDiffUtil(val newList:List<TaskData>,val oldList:List<TaskData> ):
    DiffUtil.Callback() {
    override fun getOldListSize()=oldList.size
    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)=newList[newItemPosition]==oldList[oldItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)=oldList[oldItemPosition].description==newList[newItemPosition].description

}