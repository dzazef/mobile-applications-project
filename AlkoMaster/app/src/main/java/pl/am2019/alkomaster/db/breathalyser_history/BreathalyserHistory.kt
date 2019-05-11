package pl.am2019.alkomaster.db.breathalyser_history

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * jeśli jakieś pole nie będzie potrzebne to wstawiajcie NULL
 * dlatego po każdym polu dałem '?'
 * żeby się nie bawić w ENUM dałem po prostu String
 */

@Entity(tableName = "breathalyser_history")
data class BreathalyserHistory (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "gender")
    var gender : String?,

    @ColumnInfo(name = "weight")
    var weight : Double?,

    @ColumnInfo(name = "begin_time")
    var beginTime : Date?,

    //pusty - pełny - półpełny xD
    @ColumnInfo(name = "stomach")
    var stomach : String?,

    //ilość czystego alkoholu w gramach
    @ColumnInfo(name = "quantity")
    var quantity : Double?,

    @ColumnInfo(name = "date_time")
    var dateTime : Date?
)