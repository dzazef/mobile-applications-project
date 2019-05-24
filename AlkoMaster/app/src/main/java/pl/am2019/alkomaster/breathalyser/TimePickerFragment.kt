package pl.am2019.alkomaster.breathalyser

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TextView
import android.widget.TimePicker
import pl.am2019.alkomaster.R
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var textClock : TextView? = null //clock view, ktore pokaze wybrana godzine

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        //ustawienie wybranego czasu w textClock
        var hourString = hourOfDay.toString()
        var minuteString = minute.toString()
        if(hourString.length < 2) {
            hourString = "0$hourString"
        }
        if(minuteString.length < 2) {
            minuteString = "0$minuteString"
        }
        textClock?.text = getString(R.string.godzina, hourString, minuteString)
    }
}