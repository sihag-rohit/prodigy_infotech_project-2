package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(tasks, { task -> editTask(task) }, { task -> deleteTask(task) })
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = taskAdapter

        binding.add.setOnClickListener {
            showTaskDialog(null, -1)
        }
    }

    private fun showTaskDialog(task: Task?, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val taskTitleInput = dialogView.findViewById<EditText>(R.id.taskTitleInput)
        if (task != null) {
            taskTitleInput.setText(task.title)
        }

        val dialogTitle = if (task == null) "Add Task" else "Edit Task"
        val dialogButtonTitle = if (task == null) "Add" else "Update"

        AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setView(dialogView)
            .setPositiveButton(dialogButtonTitle) { dialog, _ ->
                val taskTitle = taskTitleInput.text.toString()
                if (task == null) {
                    taskAdapter.addTask(Task(taskTitle))
                } else {
                    task.title = taskTitle
                    taskAdapter.updateTask(task, position)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun editTask(task: Task) {
        val position = tasks.indexOf(task)
        showTaskDialog(task, position)
    }

    private fun deleteTask(task: Task) {
        tasks.remove(task)
    }
}
