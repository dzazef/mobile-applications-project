package pl.am2019.alkomaster.activities

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.add_alcohol_dialog.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol

class AddAlcoholDialog(a : Activity, private val db : AppDatabase) : Dialog(a), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.add_alcohol_dialog)
        addalc_btn_add.setOnClickListener(this)
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

        Thread {
            db.alcoholDAO().insertAll(
                Alcohol(
                    name = addalc_ptxt_name.text.toString(),
                    capacity = capacity!!,
                    content = content!!,
                    price = price!!
                )
            )
            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
        }.start()

        dismiss()
    }
}
