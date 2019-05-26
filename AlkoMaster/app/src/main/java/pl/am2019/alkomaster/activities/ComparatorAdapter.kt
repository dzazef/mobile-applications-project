package pl.am2019.alkomaster.activities

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol


class ComparatorAdapter(private val activity: ComparatorActivity, private val alcohols: MutableList<Alcohol>) : RecyclerView.Adapter<ComparatorAdapter.MyViewHolder>(),
    EditAlcoholDialog.EditAlcoholDialogCallback {
    override fun onAlcoholEditedCallback(old: Alcohol, new: Alcohol?, result: Int, position: Int?) {
        when(result) {
            EditAlcoholDialog.ITEM_UNCHANGED -> Unit
            EditAlcoholDialog.ITEM_DELETED -> Unit
            EditAlcoholDialog.ITEM_EDITED -> {
                activity.changeSuggestion(activity.getSuggestion(old), activity.getSuggestion(new!!))
                activity.changeList(old, new)
                alcohols[position!!] = new
                notifyDataSetChanged()
            }
        }
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val alko = alcohols[position]
        holder.name.text = alko.name
        //holder.price.text = alko.price.toString()
        //holder.content.text = alko.content.toString()
        //holder.capacity.text = alko.capacity.toString()
        holder.capacity.text = activity.getString(R.string.alcohol_capacity_text, alko.capacity)
        holder.content.text = activity.getString(R.string.alcohol_content_text, alko.content)
        holder.price.text = activity.getString(R.string.alcohol_price_text, alko.price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, convertVie: Int): MyViewHolder {

        val myView = LayoutInflater.from(activity).inflate(R.layout.comparator_alcohol_item, parent, false)
        return MyViewHolder(myView)
    }

    override fun getItemCount(): Int {
        return alcohols.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name = itemView.findViewById(R.id.name) as TextView

        var capacity = itemView.findViewById(R.id.capacity) as TextView
        var content = itemView.findViewById(R.id.content) as TextView
        var price = itemView.findViewById(R.id.price) as TextView
        private val root = itemView.findViewById<ConstraintLayout>(R.id.c_root_alcohol_item)

        init {
            root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val dialog = EditAlcoholDialog(activity = activity, callback = this@ComparatorAdapter, alcohol = alcohols[adapterPosition], position = adapterPosition)
            dialog.show()
        }
    }



    fun getItems(): List<Alcohol> {
        return alcohols
    }
}