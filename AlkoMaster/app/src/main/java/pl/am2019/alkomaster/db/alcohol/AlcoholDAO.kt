package pl.am2019.alkomaster.db.alcohol

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface AlcoholDAO {

    @Query("select * from alcohol")
    fun getAll() : MutableList<Alcohol>

    @Query("select * from alcohol where name like :name")
    fun getByName(name : String) : MutableList<Alcohol>

    @Insert
    fun insertAll(vararg alcohol : Alcohol) : Array<Long>

    @Insert
    fun insertAll(alcohols : List<Alcohol>) : Array<Long>

    //Uwaga tutaj xD
    @Query("DELETE FROM alcohol")
    fun dropAll()

    @Query("UPDATE alcohol SET name = :name, capacity = :capacity, content = :content, price = :price WHERE id = :id")
    fun update(name : String, capacity : Int, content: Double, price : Double, id : Long)

    @Delete
    fun delete(vararg alcohol : Alcohol)
}