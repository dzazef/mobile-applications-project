package pl.am2019.alkomaster.breathalyser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_calculator_history.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.activities.DatabaseNotFoundDialogFragment
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory

class CalculatorHistory : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null
    var bhist = ArrayList<BreathalyserHistory>()
    private lateinit var adapter: HistoryAdapter

    override fun onDatabaseFail() {
        DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
    }

    override fun onDestroy() {
        if (db != null) {
            db!!.close()
        }
        super.onDestroy()
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
        Thread{
            val historyList = db.breathalyserHistoryDAO().getAll()
            //adapter = HistoryAdapter(historyList,this@CalculatorHistory)
            //history_list.adapter=adapter
            runOnUiThread {
                adapter = HistoryAdapter(historyList,this@CalculatorHistory)
                history_list.adapter=adapter
                adapter.notifyDataSetChanged() }
        }.start()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_history)

        history_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
