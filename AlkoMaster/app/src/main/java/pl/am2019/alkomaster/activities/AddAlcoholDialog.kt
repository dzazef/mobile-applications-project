package pl.am2019.alkomaster.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.add_alcohol_dialog.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol

class AddAlcoholDialog(context : Activity, private val callback : AddAlcoholDialogCallback?) : Dialog(context), View.OnClickListener, OpenDatabase.OpenDatabaseListener {

    interface AddAlcoholDialogCallback {
        fun onAlcoholAdded(new : Alcohol)
    }

    private var db : AppDatabase? = null

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
    }

    override fun onDatabaseFail() {
        showDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.add_alcohol_dialog)
        addalc_btn_add.setOnClickListener(this)
        OpenDatabase(context).also {
            it.setOpenDatabaseListener(this)
            it.load()
        }
    }


    override fun onClick(v: View?) {
        var r = false
        var capacity: Int? = null
        var content: Double? = null
        var price: Double? = null

        try {
            capacity = addalc_ptxt_capacity.text.toString().toInt()
            if (capacity < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            addalc_ptxt_capacity.setHint(R.string.incorrect_value)
        }

        try {
            content = addalc_ptxt_content.text.toString().toDouble()
            if (content < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            addalc_ptxt_content.setHint(R.string.incorrect_value)
        }


        try {
            price = addalc_ptxt_price.text.toString().toDouble()
            if (price < 0) throw Exception()
        } catch (e: Exception) {
            Log.e("DEBUG_ERROR", e.toString())
            r = true
            addalc_ptxt_price.setHint(R.string.incorrect_value)
        }

        if (r) return

        if (db != null) {
            val newAlcohol = Alcohol(
                name = addalc_ptxt_name.text.toString(),
                capacity = capacity!!,
                content = content!!,
                price = price!!
            )
            Thread {
                db!!.alcoholDAO().insertAll(newAlcohol)
                Log.i("DEBUG_INFO", db!!.alcoholDAO().getAll().toString())
                db!!.close()
            }.start()
            callback?.onAlcoholAdded(newAlcohol)
            dismiss()
        } else {
            showDialog()
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
