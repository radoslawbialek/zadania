package pl.radoslawbialek.notatnik.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.radoslawbialek.notatnik.data.TaskDao
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {
    val tasks = taskDao.getTasks().asLiveData()
}