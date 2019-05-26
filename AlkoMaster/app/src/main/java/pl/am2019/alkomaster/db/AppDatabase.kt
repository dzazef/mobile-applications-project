package pl.am2019.alkomaster.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.alcohol.AlcoholDAO
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistoryDAO
import pl.am2019.alkomaster.db.comparator_history.ComparatorAlcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorAlcoholDAO
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistoryDAO

@Database(entities = [(Alcohol::class), (BreathalyserHistory::class), (ComparatorHistory::class), (ComparatorAlcohol::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alcoholDAO() : AlcoholDAO
    abstract fun breathalyserHistoryDAO() : BreathalyserHistoryDAO
    abstract fun comparatorHistoryDAO() : ComparatorHistoryDAO
    abstract fun comparatorAlcoholDAO() : ComparatorAlcoholDAO
}