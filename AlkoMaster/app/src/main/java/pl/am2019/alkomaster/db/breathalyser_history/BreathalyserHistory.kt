package pl.am2019.alkomaster.db.breathalyser_history

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "breathalyser_history")
data class BreathalyserHistory (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "gender")
    var gender : String,

    @ColumnInfo(name = "weight")
    var weight : Double,

    //w godzinach
    @ColumnInfo(name = "drinking_time")
    var drinkingTime : Double,


    //ilość czystego alkoholu w gramach
    @ColumnInfo(name = "quantity")
    var quantity : Double,

    //tylko po to żeby był porządek w recycler view
    @ColumnInfo(name = "date_time")
    var dateTime : Date
)
