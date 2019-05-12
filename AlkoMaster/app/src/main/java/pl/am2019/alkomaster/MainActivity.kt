package pl.am2019.alkomaster

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

    }

    override fun onDatabaseReady(db: AppDatabase) {
        AsyncTask.execute{
            val a = Alcohol(name = "piwo", capacity = 500, price = 2.5, content = 5.5)
            val pattern = "yyyy-MM-dd HH:mm"
            val d = SimpleDateFormat(pattern, Locale.GERMAN).parse("2018-09-09 23:50")
            val bh = BreathalyserHistory(
                gender = "male",
                weight = 65.5,
                drinkingTime = 2.5,
                quantity = 25.6,
                dateTime = d)
            db.alcoholDAO().insertAll(a)
            db.breathalyserHistoryDAO().insertAll(bh)
            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
            Log.i("DEBUG_INFO", db.breathalyserHistoryDAO().getAll().toString())
        }
    }
}
