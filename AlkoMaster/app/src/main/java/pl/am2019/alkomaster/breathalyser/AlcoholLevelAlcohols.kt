package pl.am2019.alkomaster.breathalyser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.alcohol_list.*
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.activities.AddAlcoholDialog
import pl.am2019.alkomaster.activities.DatabaseNotFoundDialogFragment
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import pl.am2019.alkomaster.db.alcohol.Alcohol

/**
 * aktywnosc sluzy do dodawania alkoholi do listy
 */
class AlcoholLevelAlcohols : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {
    override fun onDatabaseFail() {
    }

    private var db: AppDatabase? = null
    private var alcoholList: MutableList<Alcohol>? = null
    private var myAdapter: RecyclerViewAdapter? = null
    private var breathalyser: Breathalyser = Breathalyser(0,"","")
    private var suggestions: Array<String> = Array(3000) {""}

    //dane z poprzedniej aktywnosci
    private var weight : Int = 0 //waga
    private var start : String = "" //poczatek picia
    private var end : String = "" //koniec picia
    private var type : String = "" //plec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alcohol_list)

        actionBar?.hide()

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
                Log.d("DEBUG1", "TU")

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

    //zaczyna aktywnosc prezentujaca wyniki
    fun showResults(@Suppress("UNUSED_PARAMETER") view: View) {
        val myintent = Intent(this, Results::class.java )

        myintent.putExtra("weight", weight)
        myintent.putExtra("sex", type)
        myintent.putExtra("start", start)
        myintent.putExtra("end", end)
        myintent.putExtra("alcohol_list", breathalyser.alcoholList)

        ActivityCompat.startActivityForResult(this, myintent, 111, null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val item = menu.findItem(R.id.action_search)
        search_view.setMenuItem(item)

        search_view.setSuggestions(suggestions)

        search_view.setOnItemClickListener { adapterView, _, i, _ ->
            search_view.dismissSuggestions()
            search_view.closeSearch()
            val index = suggestions.indexOf(adapterView.getItemAtPosition(i).toString())
            breathalyser.addAlcohol(AlcoholData(alcoholList!![index], 1))
            myAdapter?.notifyDataSetChanged()
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add -> {
                if (db != null) {
                    val dialog = AddAlcoholDialog(this, db!!)
                    dialog.show()
                    return true
                } else {
                    DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
                }
            }
            R.id.action_history -> {
                //rozpoczac aktywnosc historii
                val myIntent = Intent(this, CalculatorHistory::class.java)
                startActivity(myIntent)
            }
        }

        return super.onOptionsItemSelected(item)
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

        val size = alcoholList!!.size
        for (i in 0 until size) {
            suggestions[i] = "${alcoholList!![i].name}  ${alcoholList!![i].capacity} ml"
        }
    }

    fun changeSuggestion(old : String, new : String) {
        val idx = suggestions.indexOf(old)
        if (idx != -1) {
            suggestions[idx] = new
        } else {
            Log.e("alkomaster/ERROR", "${this::class.java}: Couldn't change suggestion from: $old to: $new")
        }
    }

    fun getSuggestion(alcohol : Alcohol): String = "${alcohol.name}  ${alcohol.capacity} ml"

    fun changeList(old : Alcohol, new : Alcohol?) {
        if (alcoholList == null) {
            DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
        } else {
            if (new == null) {
                alcoholList!!.remove(old)
            } else {
                alcoholList!![alcoholList!!.indexOf(old)] = new
            }
        }
    }
}