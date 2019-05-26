package pl.am2019.alkomaster.activities.comparator_history

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.ch_parent.view.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol
import pl.am2019.alkomaster.db.comparator_history.ComparatorHistory
import java.text.SimpleDateFormat
import java.util.*

class ParentAdapter(list : MutableList<Pair<ComparatorHistory, List<Alcohol>>>, val context: Context) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {

    private val itemList : MutableList<Pair<ComparatorHistory, List<Alcohol>>> = list
    private val viewPool = RecyclerView.RecycledViewPool()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.ch_parent, p0, false)
        )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val item = itemList[p1]
        holder.date.text = "DateTime: ${dateFormat.format(item.first.dateTime)}"

        val childLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayout.HORIZONTAL, false)

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = ChildAdapter(item.second, context)
            setRecycledViewPool(viewPool)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val date: TextView = itemView.chi_parent_txt1
        val recyclerView : RecyclerView = itemView.chi_parent_rclr
    }

    fun sortItems() {
        itemList.sortBy { it.first }
    }

    fun addItem(comparatorHistory : ComparatorHistory, alcoholList: List<Alcohol>) {
        itemList.add(Pair(comparatorHistory, alcoholList))
    }

    fun addItem(item : Pair<ComparatorHistory, List<Alcohol>>) {
        itemList.add(item)
    }

    fun addAllItem(itemCollection : Collection<Pair<ComparatorHistory, List<Alcohol>>>) {
        itemList.addAll(itemCollection)
    }
}