package pl.radoslawbialek.notatnik.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.radoslawbialek.notatnik.R
import pl.radoslawbialek.notatnik.data.Task

class TasksAdapter : ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    lateinit var priorityView: View
    lateinit var completionCheckBox: CheckBox
    lateinit var nameTextView: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TasksViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
    )

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        priorityView = holder.itemView.findViewById(R.id.itemTaskPriorityView)
        completionCheckBox = holder.itemView.findViewById(R.id.itemTaskCompletionCheckBox)
        nameTextView = holder.itemView.findViewById(R.id.itemTaskNameTextView)

        holder.bind(getItem(position))
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            priorityView.isVisible = task.priority
            completionCheckBox.isChecked = task.completion
            nameTextView.text = task.name
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}