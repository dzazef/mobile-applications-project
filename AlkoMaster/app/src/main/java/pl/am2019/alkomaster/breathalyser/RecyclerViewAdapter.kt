package pl.am2019.alkomaster.breathalyser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import pl.am2019.alkomaster.R

class RecyclerViewAdapter(private val dataset: ArrayList<AlcoholData>, val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val buttonPlus: ImageButton = view.findViewById(R.id.plus_button)
        private val buttonMinus: ImageButton = view.findViewById(R.id.minus_button)
        private val buttonX: ImageButton =  view.findViewById(R.id.x_button)

        init {
            view.setOnClickListener(this)
            buttonMinus.setOnClickListener(this)
            buttonPlus.setOnClickListener(this)
            buttonX.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //TODO edycja procentow (wtedy trzeba dodac pole procenty w klasie AlcoholData)


            /*
            if(v == buttonPlus) {
                //Toast.makeText(v.context, "ok!", Toast.LENGTH_SHORT).show()
                dataset[view.tag as Int].amount++
                Toast.makeText(view.context, dataset[view.tag as Int].amount.toString(), Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
            */

            when(v) {
                buttonPlus -> {
                    dataset[view.tag as Int].amount++
                    notifyDataSetChanged()
                }
                buttonMinus -> {
                    if(dataset[view.tag as Int].amount > 1) {
                        dataset[view.tag as Int].amount--
                        notifyDataSetChanged()
                    }
                }
                buttonX -> {
                    dataset.removeAt(view.tag as Int)
                    notifyDataSetChanged()
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alcohol_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //przypisanie wartosci w layoucie
        holder.view.findViewById<TextView>(R.id.alcohol_item_name).text = dataset[position].type.name

        holder.view.findViewById<TextView>(R.id.alcohol_item_capacity).text =
            context.getString(R.string.alcohol_capacity_text, (dataset[position].type.capacity))

        holder.view.findViewById<TextView>(R.id.alcohol_item_content).text =
                context.getString(R.string.alcohol_content_text, dataset[position].type.content)

        holder.view.findViewById<TextView>(R.id.alcohol_item_amount).text =
            context.getString(R.string.alcohol_amount_text, dataset[position].amount)

        holder.view.tag = position
    }

    override fun getItemCount(): Int = dataset.size
}