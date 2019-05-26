package pl.am2019.alkomaster.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(dateLong : Long) : Date = Date(dateLong)

    @TypeConverter
    fun fromDate(date : Date) : Long = date.time
}