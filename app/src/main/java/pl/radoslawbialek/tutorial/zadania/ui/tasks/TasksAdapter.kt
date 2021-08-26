package pl.radoslawbialek.tutorial.zadania.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.radoslawbialek.tutorial.R
import pl.radoslawbialek.tutorial.zadania.data.Task

class TasksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var priorityView: View
        var completionCheckBox: CheckBox
        var nameTextView: TextView

        init {
            priorityView = itemView.findViewById(R.id.itemTaskPriorityView)
            completionCheckBox = itemView.findViewById(R.id.itemTaskCompletionCheckBox)
            nameTextView = itemView.findViewById(R.id.itemTaskNameTextView)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onItemClick(task)
                }
            }

            completionCheckBox.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onCheckBoxClick(task, completionCheckBox.isChecked)
                }
            }
        }

        fun bind(task: Task) {
            priorityView.isVisible = task.priority
            completionCheckBox.isChecked = task.completion
            nameTextView.text = task.name
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}