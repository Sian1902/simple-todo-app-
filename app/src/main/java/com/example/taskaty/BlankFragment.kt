package com.example.taskaty

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskaty.databinding.FragmentBlankBinding


class BlankFragment : Fragment(),TaskListAdapter.IAdapterHelper{
    val binding:FragmentBlankBinding by lazy {
        FragmentBlankBinding.inflate(layoutInflater)
    }
    val taskViewModel:TaskViewModel by activityViewModels()
    val taskList= mutableListOf<TaskData>()
    lateinit var taskAdapter:TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskAdapter= TaskListAdapter(this)
        binding.taskRecycler.adapter=taskAdapter
        binding.taskRecycler.layoutManager=LinearLayoutManager(requireContext())
        taskAdapter.setList(taskList)
        binding.fab.setOnClickListener {
            val popUp= PopUp()
            popUp.show((activity as AppCompatActivity).supportFragmentManager,"New Task")
        }
        taskViewModel.taskList.observe(viewLifecycleOwner){
            taskAdapter.setList(it)
        }
    }

    override fun onItemClick(task: TaskData) {
        val detailsPopup=details_popup(task)
        detailsPopup.show((activity as AppCompatActivity).supportFragmentManager,"Task Details")
    }

    override fun onItemDeleted(position: Int) {
      taskViewModel.deleteAt(position)

    }


}