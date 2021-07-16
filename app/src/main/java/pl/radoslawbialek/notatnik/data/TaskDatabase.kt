package pl.radoslawbialek.notatnik.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.radoslawbialek.notatnik.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().taskDao()
            applicationScope.launch {
                dao.insert(Task("Przykładowe pierwsze zadanie", priority = true))
                dao.insert(Task("Przykładowe drugie zadanie", priority = true))
                dao.insert(Task("Przykładowe trzecie zadanie", priority = true))
                dao.insert(Task("Przykładowe czwarte zadanie", priority = true, completion = true))
                dao.insert(Task("Przykładowe piąte zadanie", priority = true, completion = true))
                dao.insert(Task("Przykładowe szóste zadanie", priority = true, completion = true))
                dao.insert(Task("Przykładowe siódme zadanie", completion = true))
                dao.insert(Task("Przykładowe ósme zadanie", completion = true))
                dao.insert(Task("Przykładowe dziewiąte zadanie", completion = true))
                dao.insert(Task("Przykładowe dziesiąte zadanie"))
                dao.insert(Task("Przykładowe jedenaste zadanie"))
                dao.insert(Task("Przykładowe dwunaste zadanie"))
            }
        }
    }
}