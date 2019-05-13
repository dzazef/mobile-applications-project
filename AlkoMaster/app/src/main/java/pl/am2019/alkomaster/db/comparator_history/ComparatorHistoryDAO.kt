package pl.am2019.alkomaster.db.comparator_history

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ComparatorHistoryDAO {

    @Query("select * from comparator_history")
    fun getAll() : List<ComparatorHistory>

    @Insert
    fun insertAll(vararg history : ComparatorHistory) : Array<Long>

    //Uwaga tutaj xD
    @Query("DELETE FROM comparator_history")
    fun dropAll()
}