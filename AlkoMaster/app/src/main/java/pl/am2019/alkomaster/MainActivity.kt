package pl.am2019.alkomaster

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import pl.am2019.alkomaster.breathalyser.LevelActivityData
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory
import pl.am2019.alkomaster.db.comparator_history.ComparatorAlcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    //narazie tylko button, ktory uruchamia aktywnosc do obliczania promili
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

    }

    override fun onDatabaseReady(db: AppDatabase) {
        /*
        AsyncTask.execute{
            db.alcoholDAO().dropAll()
            db.breathalyserHistoryDAO().dropAll()
            db.comparatorAlcoholDAO().dropAll()
            db.comparatorHistoryDAO().dropAll()
            val a = Alcohol(name = "piwo", capacity = 500, price = 2.5, content = 5.5)

            //do testow
            val b = Alcohol(name = "wino", capacity = 500, price = 2.5, content = 5.5)
            val c = Alcohol(name = "wodka", capacity = 500, price = 2.5, content = 5.5)
            val dd = Alcohol(name = "cydr", capacity = 500, price = 2.5, content = 5.5)
            val bb = Alcohol(name = "wino2", capacity = 500, price = 2.5, content = 5.5)
            val cc = Alcohol(name = "wodka2", capacity = 500, price = 2.5, content = 5.5)
            val ddd = Alcohol(name = "cydr2", capacity = 500, price = 2.5, content = 5.5)

            val pattern = "yyyy-MM-dd HH:mm"
            val d = SimpleDateFormat(pattern, Locale.GERMAN).parse("2018-09-09 23:50")
            val bh = BreathalyserHistory(
                gender = "male",
                weight = 65.5,
                drinkingTime = 2.5,
                quantity = 25.6,
                dateTime = d)
            //dodane do testow
            db.alcoholDAO().insertAll(a, b, c, dd, bb, cc, ddd)
            db.breathalyserHistoryDAO().insertAll(bh)
            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
            Log.i("DEBUG_INFO", db.breathalyserHistoryDAO().getAll().toString())
            val ch = ComparatorHistory(dateTime = d)
            db.comparatorAlcoholDAO().insertAll(ComparatorAlcohol(db.comparatorHistoryDAO().insertAll(ch)[0],1))
            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
            Log.i("DEBUG_INFO", db.breathalyserHistoryDAO().getAll().toString())
            Log.i("DEBUG_INFO", db.comparatorHistoryDAO().getAll().toString())
            Log.i("DEBUG_INFO", db.comparatorAlcoholDAO().getAll().toString())
        }
        */
    }

    fun startAlcoholLevelActivity(view: View) {
        val myIntent = Intent(this, LevelActivityData::class.java )
        startActivity(myIntent)
    }

}
