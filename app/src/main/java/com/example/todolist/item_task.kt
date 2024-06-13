package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class TaskAdapter(private val tasks: MutableList<Task>,
                  private val editListener: (Task) -> Unit,
                  private val deleteListener: (Task) -> Unit)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskInput: TextInputEditText = itemView.findViewById(R.id.taskInput)
        val editButton: ImageButton = itemView.findViewById(R.id.editbtn)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deletebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskInput.setText(task.title)
        holder.editButton.setOnClickListener { editListener(task) }
        holder.deleteButton.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            deleteListener(task)
        }
    }

    override fun getItemCount() = tasks.size

    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun updateTask(task: Task, position: Int) {
        tasks[position] = task
        notifyItemChanged(position)
    }
}
