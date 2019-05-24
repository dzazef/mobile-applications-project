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
            Log.d("DEBUG1", db.alcoholDAO().getAll().toString())
        }
    }

    override fun onDatabaseFail() {
        DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
    }

    fun onClick(v: View) {
        val intent = Intent(this, ComparatorHistoryActivity::class.java)
        startActivity(intent)
    }
}


