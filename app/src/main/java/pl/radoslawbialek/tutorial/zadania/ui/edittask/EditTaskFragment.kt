package pl.radoslawbialek.tutorial.zadania.ui.edittask

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import pl.radoslawbialek.tutorial.R
import pl.radoslawbialek.tutorial.zadania.util.exhaustive

@AndroidEntryPoint
class EditTaskFragment : Fragment(R.layout.fragment_edit_task) {

    private val viewModel: EditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskNameEditText = view.findViewById<EditText>(R.id.taskNameEditText)
        val taskPriorityCheckBox = view.findViewById<CheckBox>(R.id.taskPriorityCheckBox)
        val taskDateTextView = view.findViewById<TextView>(R.id.taskDateTextView)
        val saveTaskFloatingActionButton = view.findViewById<FloatingActionButton>(R.id.saveTaskFloatingActionButton)

        taskNameEditText.setText(viewModel.taskName, TextView.BufferType.EDITABLE)
        taskPriorityCheckBox.isChecked = viewModel.taskPriority
        taskPriorityCheckBox.jumpDrawablesToCurrentState()
        taskDateTextView.isVisible = viewModel.task != null
        taskDateTextView.text = "Utworzono: ${viewModel.task?.formattedDate}"

        taskNameEditText.addTextChangedListener {
            viewModel.taskName = it.toString()
        }

        taskPriorityCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.taskPriority = isChecked
        }

        saveTaskFloatingActionButton.setOnClickListener {
            viewModel.onSaveClick()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editTaskEvent.collect { event ->
                when (event) {
                    is EditTaskViewModel.EditTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is EditTaskViewModel.EditTaskEvent.NavigateBackWithResult -> {
                        taskNameEditText.clearFocus()
                        setFragmentResult("edit_request", bundleOf("edit_result" to event.result))
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}