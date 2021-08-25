package pl.radoslawbialek.notatnik.ui.deleteallcompleted

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.radoslawbialek.notatnik.data.TaskDao
import pl.radoslawbialek.notatnik.di.ApplicationScope

class DeleteAllCompletedViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {
    fun onConfirmClick() = applicationScope.launch {
        taskDao.deleteCompletedTasks()
    }
}