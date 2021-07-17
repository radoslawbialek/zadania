package pl.radoslawbialek.notatnik.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.radoslawbialek.notatnik.ui.tasks.SortOrder

@Dao
interface TaskDao {

    fun getTasks(searchQuery: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_NAME -> getTasksByName(searchQuery, hideCompleted)
            SortOrder.BY_DATE -> getTasksByDate(searchQuery, hideCompleted)
        }

    @Query("SELECT * FROM task_table WHERE (completion != :hideCompleted OR completion = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY priority DESC, name")
    fun getTasksByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (completion != :hideCompleted OR completion = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY priority DESC, date")
    fun getTasksByDate(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}