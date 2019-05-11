package pl.am2019.alkomaster.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface AlcoholDAO {

    @Query("select * from alcohol")
    fun getAll() : List<Alcohol>

    @Insert
    fun insertAll(vararg alcohol : Alcohol)
}