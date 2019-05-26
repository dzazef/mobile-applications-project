package pl.am2019.alkomaster.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import pl.am2019.alkomaster.R

//USAGE: DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
class DatabaseNotFoundDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(getString(R.string.database_load_error))
                .setNeutralButton(getString(R.string.return_dialog)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Null activity")
    }
}