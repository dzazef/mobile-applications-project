package pl.am2019.alkomaster.activities

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

//USAGE: DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
class DatabaseNotFoundDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage("Database not found")
                .setNeutralButton("Return") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Null activity")
    }
}