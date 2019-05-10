package pl.am2019.alkomaster

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextClock


/**
 * aktywnosc umozliwia wprowadzenie podstawowych danych (plec, waga i czas) potrzebnych do obliczenia poziomu alkoholu we krwi
 */
class LevelActivityData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alcohol_level_activity)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioButton_female ->
                    if (checked) {
                        //TODO wybrany kobieta
                    }
                R.id.radioButton_male ->
                    if (checked) {
                        //TODO wybrany mezczyzna
                    }
            }
        }
    }

    fun showTimePickerDialog(view: View) {
        if(view is TextClock) {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.textClock = view
            timePickerFragment.show(supportFragmentManager, "timePicker")
        }
    }

    fun startNextActivity(view: View) {
        //TODO wysyla zabrane dane i rozpoczyna nowa aktywnosc
    }

}
