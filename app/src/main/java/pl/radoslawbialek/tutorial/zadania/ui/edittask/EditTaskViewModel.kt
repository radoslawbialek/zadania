package pl.radoslawbialek.tutorial.zadania.ui.edittask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.radoslawbialek.tutorial.zadania.data.Task
import pl.radoslawbialek.tutorial.zadania.data.TaskDao
import pl.radoslawbialek.tutorial.zadania.ui.ADD_TASK_RESULT_OK
import pl.radoslawbialek.tutorial.zadania.ui.EDIT_TASK_RESULT_OK

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

    private val editTaskEventChannel = Channel<EditTaskEvent>()
    val editTaskEvent = editTaskEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (taskName.isBlank()) {
            showInvalidInputMessage("Nazwa nie może być pusta")
            return
        }

        if (task != null) {
            val updatedTask = task.copy(name = taskName, priority = taskPriority)
            updateTask(updatedTask)
        } else {
            val newTask = Task(name = taskName, priority = taskPriority)
            createTask(newTask)
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        editTaskEventChannel.send(EditTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        editTaskEventChannel.send(EditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        editTaskEventChannel.send(EditTaskEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditTaskEvent() {
        data class ShowInvalidInputMessage(val message: String) : EditTaskEvent()
        data class NavigateBackWithResult(val result: Int) : EditTaskEvent()
    }
}