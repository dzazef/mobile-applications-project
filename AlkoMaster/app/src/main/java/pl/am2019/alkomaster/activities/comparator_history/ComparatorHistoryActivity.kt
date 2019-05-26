package pl.am2019.alkomaster.activities.comparator_history

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_comparator_history.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory

class ComparatorHistoryActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {
    private lateinit var adapter : ParentAdapter

    override fun onDatabaseReady(db: AppDatabase) {
        ch_txt_info.visibility = View.GONE
        Thread{
            val list : MutableList<Pair<ComparatorHistory, List<Alcohol>>> = mutableListOf()
            val historyList = db.comparatorHistoryDAO().getAll()
            for (history in historyList) {
                val alcoholList = db.comparatorAlcoholDAO().getAlcoholById(history.id)
                list.add(Pair(history, alcoholList))
            }
            adapter.addAllItem(list)
            runOnUiThread { adapter.notifyDataSetChanged() }
        }.start()
    }

    override fun onDatabaseFail() {
        ch_txt_info.text = "Failed to read database!"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparator_history)

        //Set up ActionBar
        val actionBar = supportActionBar
        actionBar!!.title = "History"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Set up RecyclerView
        adapter = ParentAdapter(mutableListOf(), this)
        ch_rclr.adapter = adapter
        ch_rclr.layoutManager = LinearLayoutManager(this)

        //Show message about waiting for database
        ch_txt_info.visibility = View.VISIBLE
        ch_txt_info.text = "Waiting for database..."

        //Call Database
        OpenDatabase(this).also {
            it.setOpenDatabaseListener(this)
            it.load()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

