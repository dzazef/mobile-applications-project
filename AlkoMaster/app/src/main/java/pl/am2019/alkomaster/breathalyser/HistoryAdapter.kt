package pl.am2019.alkomaster.breathalyser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory

class HistoryAdapter (val items : List<BreathalyserHistory>, val context: Context) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val bhist = items[position]
        holder.gender.text = bhist.gender
        holder.weight.text = bhist.weight.toString().plus(" kg")
        holder.drinkingtime.text = bhist.drinkingTime.toString().plus(" h")
        holder.quantity.text = bhist.quantity.toString().plus(" l")

    }

    override fun onCreateViewHolder(parent: ViewGroup, convertVie: Int): MyViewHolder {

        val myView = LayoutInflater.from(context).inflate(R.layout.breathalyser_item, parent, false)
        return MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gender = itemView.findViewById(R.id.gender) as TextView
        var weight = itemView.findViewById(R.id.weight) as TextView
        var drinkingtime = itemView.findViewById(R.id.drinkingtime) as TextView
        var quantity = itemView.findViewById(R.id.quantity) as TextView


    }

}
