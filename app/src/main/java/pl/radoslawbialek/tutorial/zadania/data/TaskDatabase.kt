package pl.radoslawbialek.tutorial.zadania.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.radoslawbialek.tutorial.zadania.di.ApplicationScope
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
                dao.insert(Task("Zaktualizować aplikację w Google Play", priority = true))
                dao.insert(Task("Sprawdzić oferty pracy", priority = true))
                dao.insert(Task("Kupić 1kg witaminy C", priority = true, completion = true))
                dao.insert(Task("Oddać książki do biblioteki", priority = true, completion = true))
                dao.insert(Task("Sprzedać laptopa", priority = true, completion = true))
                dao.insert(Task("Napisać artykuł o RecyclerView", completion = true))
                dao.insert(Task("Zebrać informacje o Jetpack Compose"))
                dao.insert(Task("Przejrzeć i usunąć stare e-maile"))
            }
        }
    }
}