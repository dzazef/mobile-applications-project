package pl.am2019.alkomaster.breathalyser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_calculator_history.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory

class CalculatorHistory : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null
    var bhist = ArrayList<BreathalyserHistory>()
    private lateinit var adapter: HistoryAdapter

    override fun onDatabaseFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
        Thread{
            val historyList = db.breathalyserHistoryDAO().getAll()
            adapter = HistoryAdapter(historyList,this@CalculatorHistory)
            history_list.adapter=adapter
            runOnUiThread { adapter.notifyDataSetChanged() }
        }.start()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_history)

        history_list.layoutManager = LinearLayoutManager(this)

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
