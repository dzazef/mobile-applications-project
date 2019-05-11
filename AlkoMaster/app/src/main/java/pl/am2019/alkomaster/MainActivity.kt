package pl.am2019.alkomaster

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import pl.am2019.alkomaster.db.AppDatabase

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
            Log.i("DEBUG_INFO", db.alcoholDAO().getAll().toString())
        }
    }
}
