package pl.am2019.alkomaster.activities

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol


class MyAdapter(val context: Context, val alcohols: List<Alcohol>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val alko = alcohols[position]
        holder.name.text = alko.name
        //holder.price.text = alko.price.toString()
        //holder.content.text = alko.content.toString()
        //holder.capacity.text = alko.capacity.toString()
        holder.capacity.text = context.getString(R.string.alcohol_capacity_text, alko.capacity)
        holder.content.text = context.getString(R.string.alcohol_content_text, alko.content)
        holder.price.text = context.getString(R.string.alcohol_price_text, alko.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, convertVie: Int): MyViewHolder {

        val myView = LayoutInflater.from(context).inflate(R.layout.alkohol_item, parent, false)
        return MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return alcohols.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById(R.id.name) as TextView
        var capacity = itemView.findViewById(R.id.capacity) as TextView
        var content = itemView.findViewById(R.id.content) as TextView
        var price = itemView.findViewById(R.id.price) as TextView
    }

    fun getItems(): List<Alcohol> {
        return alcohols

    }
}