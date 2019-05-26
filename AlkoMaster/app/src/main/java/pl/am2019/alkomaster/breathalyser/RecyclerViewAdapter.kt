package pl.am2019.alkomaster.breathalyser

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.activities.EditAlcoholDialog
import pl.am2019.alkomaster.db.alcohol.Alcohol

class RecyclerViewAdapter(private val dataset: ArrayList<AlcoholData>, val activity: AlcoholLevelAlcohols) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(), EditAlcoholDialog.EditAlcoholDialogCallback {

    //taka se funkcja wywyoływana gdy dialog zwróci wynik
    override fun onAlcoholEditedCallback(old: Alcohol, new: Alcohol?, result: Int, position: Int?) {
        when (result) {
            EditAlcoholDialog.ITEM_UNCHANGED -> Unit
            EditAlcoholDialog.ITEM_DELETED -> {
                Log.d("DEBUG1", "DELETE")
                activity.changeSuggestion(activity.getSuggestion(old), "") //zmiana sugestii w wyszukiwarce
                activity.changeList(old, new) //zmiana listy używanej do dodania alamentu do recycler view
                dataset.removeAt(position!!) //zmiana w recycler view
                notifyDataSetChanged()
            }
            EditAlcoholDialog.ITEM_EDITED -> { //analogicznie
                Log.d("DEBUG1", "EDIT")
                activity.changeSuggestion(activity.getSuggestion(old), activity.getSuggestion(new!!))
                activity.changeList(old, new)
                dataset[position!!].type = new
                notifyDataSetChanged()
            }
        }
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val buttonPlus: ImageButton = view.findViewById(R.id.plus_button)
        private val buttonMinus: ImageButton = view.findViewById(R.id.minus_button)
        private val buttonX: ImageButton =  view.findViewById(R.id.x_button)
        private val layout: ConstraintLayout = view.findViewById(R.id.b_root_alcohol_item)

        init {
            view.setOnClickListener(this)
            buttonMinus.setOnClickListener(this)
            buttonPlus.setOnClickListener(this)
            buttonX.setOnClickListener(this)
            layout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //TODO edycja procentow (wtedy trzeba dodac pole procenty w klasie AlcoholData)

            when(v) {
                buttonPlus -> {
                    dataset[adapterPosition].amount++
                    notifyDataSetChanged()
                }
                buttonMinus -> {
                    if(dataset[adapterPosition].amount > 1) {
                        dataset[adapterPosition].amount--
                        notifyDataSetChanged()
                    }
                }
                buttonX -> {
                    dataset.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
                layout -> {
                    val dialog = EditAlcoholDialog(activity = activity, callback = this@RecyclerViewAdapter, alcohol = dataset[this.adapterPosition].type, position = adapterPosition)
                    dialog.show()
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.breathalyser_alcohol_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //przypisanie wartosci w layoucie
        holder.view.findViewById<TextView>(R.id.alcohol_item_name).text = dataset[position].type.name

        holder.view.findViewById<TextView>(R.id.alcohol_item_capacity).text =
            activity.getString(R.string.alcohol_capacity_text, (dataset[position].type.capacity))

        holder.view.findViewById<TextView>(R.id.alcohol_item_content).text =
                activity.getString(R.string.alcohol_content_text, dataset[position].type.content)

        holder.view.findViewById<TextView>(R.id.alcohol_item_amount).text =
            activity.getString(R.string.alcohol_amount_text, dataset[position].amount)
    }

    override fun getItemCount(): Int = dataset.size
}