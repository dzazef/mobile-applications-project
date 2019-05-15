package pl.am2019.alkomaster

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.comparator_activity.*
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol


class ComparatorActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comparator_activity)


        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        var alks = ArrayList<Alcohol>()
        alks.add(Alcohol(1, "Piast", 500, 5.5, 3.00))
        alks.add(Alcohol(2, "Żubr", 500, 4.5, 2.50))

        val adapter = MyAdapter(this, alks)
        recyclerView.adapter = adapter
        but2.setOnClickListener {

        }
        but1.setOnClickListener {
/*
            val intent = Intent(this,ResultActivity::class.java)
            // intent.putExtra("result", result)
            startActivity(intent)*/


        }
    }


    override fun onDatabaseReady(db: AppDatabase) {
        AsyncTask.execute {
            db.alcoholDAO().dropAll()
            db.breathalyserHistoryDAO().dropAll()
            db.comparatorAlcoholDAO().dropAll()
            db.comparatorHistoryDAO().dropAll()
            val a = Alcohol(name = "piwo", capacity = 500, price = 2.5, content = 5.5)
            db.alcoholDAO().insertAll(a)


        }
    }
}



