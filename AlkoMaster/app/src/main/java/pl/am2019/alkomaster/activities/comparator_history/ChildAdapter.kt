package pl.am2019.alkomaster.activities.comparator_history

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.ch_child.view.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol

class ChildAdapter(list : List<Alcohol>, val context: Context) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    private val itemList : List<Alcohol> = list

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.ch_child, p0, false)
        )

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val item = itemList[p1]
        holder.name.text = item.name
        //holder.capacity.text = item.capacity.toString()
        //holder.content.text = item.content.toString()
        //holder.price.text = item.price.toString()

        holder.capacity.text = context.getString(R.string.alcohol_capacity_text, item.capacity)
        holder.content.text = context.getString(R.string.alcohol_content_text, item.content)
        holder.price.text = context.getString(R.string.alcohol_price_text, item.price)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.chc_name
        val capacity : TextView = itemView.chc_capacity
        val content : TextView = itemView.chc_content
        val price : TextView = itemView.chc_price
    }
}