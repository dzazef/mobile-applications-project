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
import android.util.Log


/**
 * aktywnosc sluzy do dodawania alkoholi do listy
 */
class AlcoholLevelAlcohols : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {

    private var db: AppDatabase? = null
    private var alcoholList: List<Alcohol>? = null

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

        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Do some magic
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })

        search_view.setVoiceSearch(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(pl.am2019.alkomaster.R.menu.menu_item, menu)

        val item = menu.findItem(pl.am2019.alkomaster.R.id.action_search)
        search_view.setMenuItem(item)

        if(alcoholList != null) {
            val size = alcoholList!!.size
            val list: Array<String> = Array(size) {i -> ("${alcoholList!![i].name}  ${alcoholList!![i].capacity} ml") }

            search_view.setSuggestions(list)
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

    /* moze sie przyda, jesli nie, to usunac
    override fun onBackPressed() {
        if (search_view.isSearchOpen) {
            search_view.closeSearch()

        } else {
            super.onBackPressed()
        }
    }
    */

    //pobiera wszystkie alkohole z bazy i umieszcza w liscie
    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db
        alcoholList = db.alcoholDAO().getAll()

        //test
        alcoholList?.let {
            for (alcohol in it) {
                Log.d("test", alcohol.name)
            }
        }
        //
    }
}