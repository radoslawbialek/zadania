package pl.radoslawbialek.tutorial.zadania.ui.deleteallcompleted

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Potwierdź usunięcie")
            .setMessage("Czy na pewno usunąć wszystkie ukończone zadania?")
            .setNegativeButton("Anuluj", null)
            .setPositiveButton("Tak") { _, _ ->
                viewModel.onConfirmClick()
            }
            .create()
}