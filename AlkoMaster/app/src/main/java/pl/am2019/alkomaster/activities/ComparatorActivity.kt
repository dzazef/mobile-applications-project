package pl.am2019.alkomaster.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.alkohol_item.view.*
import kotlinx.android.synthetic.main.comparator_activity.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import java.util.*
import kotlin.collections.ArrayList


class ComparatorActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null
    var alks = ArrayList<Alcohol>()
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comparator_activity)

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.setOnSwipeToDelete()

        alks.add(Alcohol(1, "2", 500, 5.5, 2.70))
        alks.add(Alcohol(2, "1", 500, 6.0, 1.0))
        alks.add(Alcohol(3, "3", 400, 40.0, 35.0))

        adapter = MyAdapter(this, alks)
        recyclerView.adapter = adapter
    }


    private fun RecyclerView.setOnSwipeToDelete() {
        val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.adapterPosition
                alks.removeAt(pos)
                adapter!!.notifyDataSetChanged()
            }
        }
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(this)
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db

    }

    fun onAddNewProductClick(@Suppress("UNUSED_PARAMETER") v: View) {
        if (db != null) {
            val dialog = AddAlcoholDialog(this, db!!)
            dialog.show()
        } else {
            DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
        }
    }

    /**
     *  button do sortowania
     *  sortuje  wg. wzoru
     *  // wzor: content / 100 * capacity / price
     *  czyli od najbardziej opłacalnego do najmniej
     *  niestety nie pokazuje sie wspołczynnik
     */
    fun onCompareClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val adapter = recyclerView.getAdapter() as MyAdapter// fetching adapter
        Collections.sort(adapter.getItems(), object : Comparator<Alcohol> {
            override fun compare(c1: Alcohol, c2: Alcohol): Int {
                // wzor: content / 100 * capacity / price
                return -(c1.price.let { it1 -> c2.content.div(100).times(c2.capacity).div(it1).toInt() }).minus(
                    c2.price.let { it2 -> c2.content.div(100).times(c2.capacity).div(it2).toInt() }
                )
            }
        })
        adapter.notifyDataSetChanged()
    }

    fun onDeleteAllClick(@Suppress("UNUSED_PARAMETER") v: View) {
//        val result = StringBuilder()
        for (i in alks.size - 1 downTo 0) {
            if (recyclerView.getChildAt(i).checkBox.isChecked()) {
                alks.removeAt(i)
                recyclerView.getChildAt(i).checkBox.isChecked = false
            }
        }
        Toast.makeText(this, "Usunięto", Toast.LENGTH_LONG).show()
        adapter.notifyDataSetChanged()
    }
}



