package pl.am2019.alkomaster.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(Alcohol::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alcoholDAO() : AlcoholDAO
}