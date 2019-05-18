package pl.am2019.alkomaster

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.comparator_activity.*
import pl.am2019.alkomaster.activities.AddAlcoholDialog
import pl.am2019.alkomaster.activities.DatabaseNotFoundDialogFragment
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import java.util.*
import kotlin.collections.ArrayList


class ComparatorActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comparator_activity)


        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        var alks = ArrayList<Alcohol>()
        alks.add(Alcohol(1, "2", 500, 5.5, 2.70))
        alks.add(Alcohol(2, "1", 500, 6.0, 1.0))
        alks.add(Alcohol(3, "3", 400, 40.0, 35.0))


        val adapter = MyAdapter(this, alks)
        recyclerView.adapter = adapter







        but2.setOnClickListener {
            if (db != null) {
                val dialog = AddAlcoholDialog(this, db!!)
                dialog.show()
            } else {
                DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
            }
        }


        /**
         * button do sortowania
         * sortuje  wg. wzoru
         *  // wzor: content / 100 * capacity / price
         *  czyli od najbardziej opłacalnego do najmniej
         *  niestety nie pokazuje sie wspołczynnik
         */


        but1.setOnClickListener {
            val adapter = recyclerView.getAdapter() as MyAdapter// fetching adapter

            Collections.sort(adapter.getItems(), object : Comparator<Alcohol> {
                override fun compare(c1: Alcohol, c2: Alcohol): Int {
                    // wzor: content / 100 * capacity / price
                    return -(c1.price.let { it1 -> c2.content.div(100).times(c2.capacity).div(it1).toInt() }).minus(
                        c2.price.let { it2 -> c2.content.div(100).times(c2.capacity).div(it2).toInt() }
                    )
                }
            })
            adapter.notifyDataSetChanged()
        }



    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
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



