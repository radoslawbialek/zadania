package pl.radoslawbialek.tutorial.zadania.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "task_table")
@Parcelize
data class Task(
    val name: String,
    val priority: Boolean = false,
    val completion: Boolean = false,
    val date: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val formattedDate: String
        get() = DateFormat.getDateTimeInstance().format(date)
}