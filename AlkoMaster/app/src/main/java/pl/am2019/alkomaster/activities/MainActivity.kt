package pl.am2019.alkomaster.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pl.am2019.alkomaster.ComparatorActivity
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase

class MainActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenDatabase(this).also { it.setOpenDatabaseListener(this) }.also { it.load() }
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
//        AsyncTask.execute{
//            db.alcoholDAO().dropAll()
//            db.breathalyserHistoryDAO().dropAll()
//            db.comparatorAlcoholDAO().dropAll()
//            db.comparatorHistoryDAO().dropAll()
//            val a = Alcohol(name = "piwo", capacity = 500, price = 2.5, content = 5.5)
//            val pattern = "yyyy-MM-dd HH:mm"
//            val d = SimpleDateFormat(pattern, Locale.GERMAN).parse("2018-09-09 23:50")
//            val bh = BreathalyserHistory(
//                gender = "male",
//                weight = 65.5,
//                drinkingTime = 2.5,
//                quantity = 25.6,
//                dateTime = d)
//            db.alcoholDAO().insertAll(a)
//            db.breathalyserHistoryDAO().insertAll(bh)
//            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
//            Log.i("DEBUG_INFO", db.breathalyserHistoryDAO().getAll().toString())
//            val ch = ComparatorHistory(dateTime = d)
//            db.comparatorAlcoholDAO().insertAll(ComparatorAlcohol(db.comparatorHistoryDAO().insertAll(ch)[0],1))
//            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
//            Log.i("DEBUG_INFO", db.breathalyserHistoryDAO().getAll().toString())
//            Log.i("DEBUG_INFO", db.comparatorHistoryDAO().getAll().toString())
//            Log.i("DEBUG_INFO", db.comparatorAlcoholDAO().getAll().toString())
//        }
    }

    fun onClick(v: View) {
        if (db != null) {
            val dialog = AddAlcoholDialog(this, db!!)
            dialog.show()
        } else {
            DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
        }
        if (v == compbutton){
            val myIntent = Intent(this, ComparatorActivity::class.java)
            startActivity(myIntent)
        }
    }
}


