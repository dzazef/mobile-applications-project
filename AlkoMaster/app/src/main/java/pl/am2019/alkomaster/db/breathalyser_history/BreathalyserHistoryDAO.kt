package pl.am2019.alkomaster.db.breathalyser_history

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BreathalyserHistoryDAO {

    @Query("select * from breathalyser_history order by date_time desc")
    fun getAll() : List<BreathalyserHistory>

    @Insert
    fun insertAll(vararg history : BreathalyserHistory) : Array<Long>

    //Uwaga tutaj xD
    @Query("DELETE FROM breathalyser_history")
    fun dropAll()
}