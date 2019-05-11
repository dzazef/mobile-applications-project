package pl.am2019.alkomaster.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.alcohol.AlcoholDAO
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistoryDAO

@Database(entities = [(Alcohol::class), (BreathalyserHistory::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alcoholDAO() : AlcoholDAO
    abstract fun breathalyserHistoryDAO() : BreathalyserHistoryDAO
}