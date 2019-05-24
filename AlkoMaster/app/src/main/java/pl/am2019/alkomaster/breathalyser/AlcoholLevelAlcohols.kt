package pl.am2019.alkomaster.breathalyser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol
import android.view.Menu
import kotlinx.android.synthetic.main.alcohol_list.*
import pl.am2019.alkomaster.R
import com.miguelcatalan.materialsearchview.MaterialSearchView
import android.text.TextUtils
import android.speech.RecognizerIntent
import android.content.Intent
import android.app.Activity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.AdapterView

/**
 * aktywnosc sluzy do dodawania alkoholi do listy
 */
class AlcoholLevelAlcohols : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null
    private var alcoholList: List<Alcohol>? = null
    private var myAdapter: RecyclerViewAdapter? = null
    private var breathalyser: Breathalyser = Breathalyser(0,"","")

    //dane z poprzedniej aktywnosci
    private var weight : Int = 0 //waga
    private var start : String = "" //poczatek picia
    private var end : String = "" //koniec picia
    private var type : String = "" //plec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alcohol_list)

        //pobranie wartosci przeslanych przez poprzednia aktywnosc
        weight = intent.getIntExtra("weight", 0)
        start = intent.getStringExtra("start")
        end = intent.getStringExtra("end")
        type = intent.getStringExtra("type")

        breathalyser = Breathalyser(weight, type, start)

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        if(savedInstanceState != null) {
            try {
                breathalyser.alcoholList = savedInstanceState.getParcelableArrayList("added_alcohols")!!

                myAdapter = RecyclerViewAdapter(breathalyser.alcoholList, this)
                recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    adapter = myAdapter
                }

            } catch(e: Throwable) {
            }

        } else {

            //recyclerView
            myAdapter = RecyclerViewAdapter(breathalyser.alcoholList, this)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                adapter = myAdapter
            }
        }

        search_view.setVoiceSearch(true)
        search_view.showSearch(false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelableArrayList("added_alcohols", breathalyser.alcoholList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(pl.am2019.alkomaster.R.menu.menu_item, menu)

        val item = menu.findItem(pl.am2019.alkomaster.R.id.action_search)
        search_view.setMenuItem(item)

        if(alcoholList != null) {
            val size = alcoholList!!.size
            val list: Array<String> = Array(size) {i -> ("${alcoholList!![i].name}  ${alcoholList!![i].capacity} ml") }

            search_view.setSuggestions(list)

            search_view.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    search_view.dismissSuggestions()
                    search_view.closeSearch()

                    val index = list.indexOf(adapterView.getItemAtPosition(i).toString())
                    breathalyser.addAlcohol(AlcoholData(alcoholList!![index], 1))

                    myAdapter?.notifyDataSetChanged()
                }
            })


        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view.setQuery(searchWrd, false)
                }
            }

            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //pobiera wszystkie alkohole z bazy i umieszcza w liscie
    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
        alcoholList = db.alcoholDAO().getAll()
    }
}