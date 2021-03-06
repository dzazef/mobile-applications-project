package pl.am2019.alkomaster.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Window
import kotlinx.android.synthetic.main.edit_alcohol_dialog.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol

class EditAlcoholDialog(
    private val callback: EditAlcoholDialogCallback,
    private val alcohol: Alcohol,
    private val activity: Activity,
    private val position: Int?
) : Dialog(activity),
    OpenDatabase.OpenDatabaseListener {
    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
    }

    override fun onDatabaseFail() {
        showDialog()
    }

    private var db : AppDatabase? = null

    companion object {
        const val ITEM_UNCHANGED = 0
        const val ITEM_EDITED = 1
        const val ITEM_DELETED = 2
    }

    interface EditAlcoholDialogCallback {
        fun onAlcoholEditedCallback(old : Alcohol, new : Alcohol?, result : Int, position : Int?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.edit_alcohol_dialog)

        editalc_btn_add.setOnClickListener{onAddClick()}
        editalc_btn_delete.setOnClickListener{onDeleteClick()}

        editalc_ptxt_name.setText(alcohol.name)
        editalc_ptxt_capacity.setText(alcohol.capacity.toString())
        editalc_ptxt_content.setText(alcohol.content.toString())
        editalc_ptxt_price.setText(alcohol.price.toString())

        OpenDatabase(context).also {
            it.setOpenDatabaseListener(this)
            it.load()
        }
    }

    private fun onDeleteClick() {
        if (db == null) {
            showDialog()
        } else {
            Thread{
                db!!.alcoholDAO().delete(alcohol)
                db!!.close()
            }.start()
            callback.onAlcoholEditedCallback(alcohol, null, ITEM_DELETED, position)
            dismiss()
        }
    }

    private fun onAddClick() {
        var r = false
        var capacity: Int? = null
        var content: Double? = null
        var price: Double? = null

        try {
            capacity = editalc_ptxt_capacity.text.toString().toInt()
            if (capacity < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            editalc_ptxt_capacity.setHint(R.string.incorrect_value)
        }

        try {
            content = editalc_ptxt_content.text.toString().toDouble()
            if (content < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            editalc_ptxt_content.setHint(R.string.incorrect_value)
        }


        try {
            price = editalc_ptxt_price.text.toString().toDouble()
            if (price < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            editalc_ptxt_price.setHint(R.string.incorrect_value)
        }

        if (r) return

        val newAlcohol = Alcohol(
            id = alcohol.id,
            name = editalc_ptxt_name.text.toString(),
            capacity = capacity!!,
            content = content!!,
            price = price!!
        )

        if (newAlcohol == alcohol) {
            callback.onAlcoholEditedCallback(alcohol, alcohol, ITEM_UNCHANGED, position)
            Thread {
                if (db != null) {
                    db!!.close()
                }
            }.start()
            dismiss()
        } else {
            if (db == null) {
                showDialog()
            } else {
                Thread {
                    db!!.alcoholDAO().update(name = newAlcohol.name, capacity = newAlcohol.capacity, content = newAlcohol.content, price = newAlcohol.price, id = newAlcohol.id)
                    db!!.close()
                }.start()
                callback.onAlcoholEditedCallback(alcohol, newAlcohol, ITEM_EDITED, position)
                dismiss()
            }
        }
    }


    private fun showDialog() {
        val activity = getActivity(context)
        if (activity != null) {
            DatabaseNotFoundDialogFragment().show((activity as FragmentActivity).supportFragmentManager, "dialog")
        }
    }

    private fun getActivity(context : Context): Activity? {
        if (context is Activity) {
            return context
        } else if (context is ContextThemeWrapper) {
            return getActivity(context.baseContext)
        }
        return null
    }
}