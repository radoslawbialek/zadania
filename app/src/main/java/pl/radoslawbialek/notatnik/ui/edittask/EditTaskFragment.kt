package pl.radoslawbialek.notatnik.ui.edittask

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.radoslawbialek.notatnik.R

@AndroidEntryPoint
class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {

    private val viewModel: EditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskNameEditText = view.findViewById<EditText>(R.id.taskNameEditText)
        val taskPriorityCheckBox = view.findViewById<CheckBox>(R.id.taskPriorityCheckBox)
        val taskDateTextView = view.findViewById<TextView>(R.id.taskDateTextView)

        taskNameEditText.setText(viewModel.taskName, TextView.BufferType.EDITABLE)
        taskPriorityCheckBox.isChecked = viewModel.taskPriority
        taskPriorityCheckBox.jumpDrawablesToCurrentState()
        taskDateTextView.isVisible = viewModel.task != null
        taskDateTextView.text = "Utworzono: ${viewModel.task?.formattedDate}"
    }
}