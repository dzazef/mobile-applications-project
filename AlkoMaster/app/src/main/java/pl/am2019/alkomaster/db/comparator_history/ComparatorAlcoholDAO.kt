package pl.am2019.alkomaster.db.comparator_history

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import pl.am2019.alkomaster.db.alcohol.Alcohol

@Dao
interface ComparatorAlcoholDAO {

    @Query("select * from comparator_alcohol")
    fun getAll() : List<ComparatorAlcohol>

    @Insert
    fun insertAll(vararg history : ComparatorAlcohol) : Array<Long>

    @Query("select * from alcohol where id in (select ca.alcohol_id from comparator_alcohol ca where ca.comparator_id = :id)")
    fun getAlcoholById(id : Long) : List<Alcohol>

    //Uwaga tutaj xD
    @Query("DELETE FROM comparator_alcohol")
    fun dropAll()
}