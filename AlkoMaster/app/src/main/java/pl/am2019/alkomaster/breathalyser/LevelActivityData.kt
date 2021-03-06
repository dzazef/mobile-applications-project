package pl.am2019.alkomaster.breathalyser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.alcohol_level_activity.*
import kotlinx.android.synthetic.main.alcohol_level_activity.view.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.activities.AddAlcoholDialog
import pl.am2019.alkomaster.activities.DatabaseNotFoundDialogFragment
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import java.text.SimpleDateFormat
import java.util.*

/**
 * aktywnosc umozliwia wprowadzenie podstawowych danych (plec, waga i czas) potrzebnych do obliczenia poziomu alkoholu we krwi
 */
class LevelActivityData : AppCompatActivity() {
    private var type : String? = "female" //domyslnie female

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alcohol_level_activity)
        actionBar?.hide()

        val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        (linearLayout_weight.editText_waga).setText(sharedPref.getInt("weight", 0).toString())
        type = sharedPref.getString("sex", "female")
        if(type!=null) {
            when(type) {
                "male" -> {
                    radioButton_male.isChecked = true
                }
                "female" -> {
                    radioButton_female.isChecked = true
                }
            }
        }

        //ustawienie godziny rozpoczecia i konca na aktualna godzine
        val dateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        (linearLayout2.linearLayout_poczatek.textClock_poczatek).text = dateFormat.format(cal.time)
        (linearLayout2.linearLayout_koniec.textClock_koniec).text = dateFormat.format(cal.time)

        if(savedInstanceState != null) {
            type = savedInstanceState.getString("type")
            (linearLayout_weight.editText_waga).setText(savedInstanceState.getString("weight"))
            (linearLayout2.linearLayout_poczatek.textClock_poczatek).text = savedInstanceState.getString("start")
            (linearLayout2.linearLayout_koniec.textClock_koniec).text = savedInstanceState.getString("end")

            when(type) {
                "male" -> radioButton_male.isChecked = true
                "female" -> radioButton_female.isChecked = true
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.breathalyser_menu_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add -> {
                val dialog = AddAlcoholDialog(this, null)
                dialog.show()
            }
            R.id.action_history -> {
                //rozpoczac aktywnosc historii
                val myIntent = Intent(this, CalculatorHistory::class.java)
                startActivity(myIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("type", type)
        outState?.putString("weight", (linearLayout_weight.editText_waga).text.toString())
        outState?.putString("start", (linearLayout2.linearLayout_poczatek.textClock_poczatek).text.toString())
        outState?.putString("end", (linearLayout2.linearLayout_koniec.textClock_koniec).text.toString())
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioButton_female ->
                    if (checked) {
                        type = "female"
                    }
                R.id.radioButton_male ->
                    if (checked) {
                        type = "male"
                    }
            }
        }
    }

    fun showTimePickerDialog(view: View) {
        if(view is TextView) {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.textClock = view
            timePickerFragment.show(supportFragmentManager, "timePicker")
        }
    }

    fun startNextActivity(@Suppress("UNUSED_PARAMETER") view: View) {
        try {
            val weight: Int = (linearLayout_weight.editText_waga).text.toString().toInt()
            val start: String = (linearLayout2.linearLayout_poczatek.textClock_poczatek).text.toString()
            val end: String = (linearLayout2.linearLayout_koniec.textClock_koniec).text.toString()

            val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor : SharedPreferences.Editor = sharedPref.edit()
            editor.putInt("weight", weight)
            editor.putString("sex", type)
            editor.apply()

            val myIntent = Intent(this, AlcoholLevelAlcohols::class.java)
            myIntent.putExtra("type", type)
            myIntent.putExtra("weight", weight)
            myIntent.putExtra("start", start)
            myIntent.putExtra("end", end)

            startActivity(myIntent)
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Enter weight", Toast.LENGTH_SHORT).show()
        }
    }

}
