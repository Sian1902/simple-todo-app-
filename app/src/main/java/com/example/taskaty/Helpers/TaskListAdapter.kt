package com.example.taskaty.Helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.Room.TaskData
import com.example.taskaty.databinding.TaskRowBinding

class TaskListAdapter(val item: IAdapterHelper):RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    interface IAdapterHelper{
        fun onItemClick(task: TaskData)
        fun onItemDeleted(task: TaskData)
        fun onEdit(task: TaskData, position: Int)
    }
   val taskList= mutableListOf<TaskData>()
    fun setList(list:MutableList<TaskData>){
        val diffUtil= TaskDiffUtil(taskList,list)
        val difResult= DiffUtil.calculateDiff(diffUtil)
        taskList.clear()
        taskList.addAll(list)
        difResult.dispatchUpdatesTo(this)
    }

    inner class TaskViewHolder(val binding: TaskRowBinding):ViewHolder(binding.root){
            fun bind(task: TaskData){
                binding.taskTitle.text=task.name
                binding.category.text=task.category
                binding.doneCheckBox.isChecked=false
                binding.doneCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                      item.onItemDeleted(task)


                    }
                }
                binding.detailsButtons.setOnClickListener{
                    item.onItemClick(task)
                }
                binding.editBTN.setOnClickListener {
                  val task=item.onEdit(task,adapterPosition)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding=TaskRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount()=taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }
}