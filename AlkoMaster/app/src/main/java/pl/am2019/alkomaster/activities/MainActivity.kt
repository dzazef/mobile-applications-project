package pl.am2019.alkomaster.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.activities.comparator_history.ComparatorHistoryActivity
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorAlcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenDatabase(this).also { it.setOpenDatabaseListener(this) }.also { it.load() }
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
        AsyncTask.execute{
            db.alcoholDAO().dropAll()
            db.breathalyserHistoryDAO().dropAll()
            db.comparatorAlcoholDAO().dropAll()
            db.comparatorHistoryDAO().dropAll()

            val pattern = "yyyy-MM-dd HH:mm"
            val d = SimpleDateFormat(pattern, Locale.US).parse("2018-09-09 23:50")

            val a1 = Alcohol(name = "piwo1", capacity = 500, content = 5.5, price = 2.5)
            val a2 = Alcohol(name = "piwo2", capacity = 500, content = 4.5, price = 3.5)
            val a3 = Alcohol(name = "piwo2", capacity = 500, content = 4.5, price = 3.5)

            val aID = db.alcoholDAO().insertAll(a1, a2, a3)

            val ch = ComparatorHistory(dateTime = d)
            val chID = db.comparatorHistoryDAO().insertAll(ch).first()

            for (id in aID) {
                db.comparatorAlcoholDAO().insertAll(ComparatorAlcohol(comparatorId = chID, alcoholId = id))
            }

            val test = db.comparatorAlcoholDAO().getAlcoholById(chID)

            Log.d("DEBUG1", test.toString())
        }
    }

    override fun onDatabaseFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onClick(v: View) {
        val intent = Intent(this, ComparatorHistoryActivity::class.java)
        startActivity(intent)
    }
}


