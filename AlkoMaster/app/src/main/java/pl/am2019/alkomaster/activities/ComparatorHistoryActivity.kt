package pl.am2019.alkomaster.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory
import java.util.*

class ComparatorHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparator_history)
        val actionBar = supportActionBar
        actionBar!!.title = "History"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

class ComparatorHistoryAdapter : RecyclerView.Adapter<ComparatorHistoryAdapter.ViewHolder>() {

    private val itemList : Map<ComparatorHistory, List<Alcohol>> = TreeMap()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        //TODO
    }
}