package pl.radoslawbialek.notatnik.ui.edittask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pl.radoslawbialek.notatnik.data.Task
import pl.radoslawbialek.notatnik.data.TaskDao

class EditTaskViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val task = state.get<Task>("task")

    var taskName = state.get<String>("taskName") ?: task?.name ?: ""
        set(value) {
            field = value
            state.set("taskName", value)
        }

    var taskPriority = state.get<Boolean>("taskPriority") ?: task?.priority ?: false
        set(value) {
            field = value
            state.set("taskPriority", value)
        }

}