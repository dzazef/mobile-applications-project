package pl.am2019.alkomaster.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.Menu
import android.view.View
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.alkohol_item.view.*
import kotlinx.android.synthetic.main.comparator_activity.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import java.util.*
import kotlin.collections.ArrayList


class ComparatorActivity : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {
    override fun onDatabaseFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var db: AppDatabase? = null
    var alks = ArrayList<Alcohol>() //do adpatera recycler view
    private lateinit var myadapter: MyAdapter
    private var suggestions: Array<String> = Array(2000) {""}
    private var alcoholList: List<Alcohol>? = null //lista alkoholi, ktora pozniej przechowuje wszystkie alkohole z bazy


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(pl.am2019.alkomaster.R.layout.comparator_activity)
        actionBar?.hide()

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        if(savedInstanceState != null) {
            alks = savedInstanceState.getParcelableArrayList("added_alcohols")!!
            myadapter = MyAdapter(this, alks)
            //recyclerView_comparator.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            //recyclerView_comparator.adapter = adapter
            //recyclerView_comparator.setHasFixedSize(true)
            recyclerView_comparator.apply {
                adapter = myadapter
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                setOnSwipeToDelete()
            }

        } else {
            //val recyclerView = findViewById<RecyclerView>(pl.am2019.alkomaster.R.id.recyclerView_comparator)
            //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            //  recyclerView.setOnSwipeToDelete()

            myadapter = MyAdapter(this, alks)

            recyclerView_comparator.apply {
                adapter = myadapter
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                setOnSwipeToDelete()
            }
            //recyclerView.adapter = myadapter
            //recyclerView_comparator.setHasFixedSize(true)
        }

        //przygotowanie search bara
        search_view_comparator.setVoiceSearch(true)
        search_view_comparator.showSearch(false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelableArrayList("added_alcohols", alks)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.comparator_menu_item, menu)

        val item = menu.findItem(R.id.action_search)
        search_view_comparator.setMenuItem(item)

        search_view_comparator.setSuggestions(suggestions)

        search_view_comparator.setOnItemClickListener { adapterView, _, i, _ ->
            search_view_comparator.dismissSuggestions()
            search_view_comparator.closeSearch()
            val index = suggestions.indexOf(adapterView.getItemAtPosition(i).toString())
            alks.add(alcoholList!![index])
            myadapter.notifyDataSetChanged()
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view_comparator.setQuery(searchWrd, false)
                }
            }

            return
        }
        super.onActivityResult(requestCode, resultCode, data)
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



    fun onAddNewProductClick(@Suppress("UNUSED_PARAMETER") v: View) {
        if (db != null) {
            val dialog = AddAlcoholDialog(this, db!!)
            dialog.show()
        } else {
            DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
        }
    }


    fun onCompareClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val adapter = recyclerView_comparator.adapter as MyAdapter// fetching adapter
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


    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db

        alcoholList = db.alcoholDAO().getAll()

        val size = alcoholList!!.size
        for (i in 0 until size) {
            suggestions[i] = "${alcoholList!![i].name}  ${alcoholList!![i].capacity} ml"
        }
    }



    //bylo zakomentowane, nie wiem, czy dziala
    fun onDeleteAllClick(@Suppress("UNUSED_PARAMETER") v: View) {
       val result = StringBuilder()
        for (i in alks.size - 1 downTo 0) {
            if (recyclerView_comparator.getChildAt(i).checkBox.isChecked) {
                alks.removeAt(i)
                recyclerView_comparator.getChildAt(i).checkBox.isChecked = false
            }
        }
        myadapter.notifyDataSetChanged()
    }


    /*
    /**
     * dupa jasiu karuzela, nie działa
     */
    fun Search() {

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, ount: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Thread {

                    var itemsList = db?.alcoholDAO()!!.getByName(s.toString() + "%")

                    runOnUiThread {

                        recyclerView.adapter = MyAdapter(this@ComparatorActivity, itemsList)
                        adapter.notifyDataSetChanged()
                    }
                }.start()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
    */
}

