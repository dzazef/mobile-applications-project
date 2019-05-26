package pl.am2019.alkomaster.breathalyser

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.AppDatabase
import pl.am2019.alkomaster.db.OpenDatabase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.results_activity.*
import pl.am2019.alkomaster.activities.DatabaseNotFoundDialogFragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import pl.am2019.alkomaster.activities.AddAlcoholDialog
import pl.am2019.alkomaster.db.breathalyser_history.BreathalyserHistory
import java.util.*


class Results : AppCompatActivity(), OpenDatabase.OpenDatabaseListener {
    private var db: AppDatabase? = null
    private var breathalyser: Breathalyser? = null
    private var end: String? = null //godzina konca picia

    override fun onDestroy() {
        if (db != null) {
            db!!.close()
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results_activity)

        actionBar?.hide()

        //pobranie danych z intencji
        val weight: Int = intent.getIntExtra("weight", 0)
        val sex: String = intent.getStringExtra("sex")
        val start: String = intent.getStringExtra("start")
        end = intent.getStringExtra("end")
        val alcoholList = intent.getParcelableArrayListExtra<AlcoholData>("alcohol_list") //lista wypitych alkoholi

        //stworzenie breathalysera
        breathalyser = Breathalyser(weight, sex, start)
        breathalyser?.alcoholList = alcoholList
        breathalyser?.context = this

        //przygotowanie bazy
        val open = OpenDatabase(this)
        open.setOpenDatabaseListener(this)
        open.load()

        //dane do wykresu
        if(end != null && breathalyser != null) {
            val values = ArrayList<Entry>() //dane do wykresu

            var drinkingPeriodInHours: Double = breathalyser!!.timeDifference(end!!)
            val gap = 0.5
            var alcoholContent = breathalyser!!.getBloodAlcoholContent(end!!)
            var maxAlcoholContent = alcoholContent

            val labels = ArrayList<String>()
            labels.add(end!!)
            values.add(Entry(0f, alcoholContent.toFloat()))
            var counter = 1f

            while(alcoholContent > 0) {
                drinkingPeriodInHours += gap
                alcoholContent = breathalyser!!.getBloodAlcoholContent(drinkingPeriodInHours)
                labels.add(breathalyser!!.nextHour(drinkingPeriodInHours))
                values.add(Entry(counter, alcoholContent.toFloat()))
                counter++

                if(maxAlcoholContent < alcoholContent) {
                    maxAlcoholContent = alcoholContent
                }
            }

            val lineDataSet = LineDataSet(values, getString(R.string.poziom_alkoholu_label_chart))
            lineDataSet.setDrawCircles(false)
            lineDataSet.setDrawValues(false)
            val lineData = LineData(lineDataSet)

            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            chart.data = lineData
            val description = Description()
            description.text = ""
            chart.description = description
            chart.notifyDataSetChanged()

            alcohol_amount_textView.text = getString(R.string.alcohol_amount_textView, breathalyser!!.getTotalAmountOfAlcohol().toInt())
            alcohol_blood_content_textView.text = getString(R.string.alcohol_blood_content_textView, maxAlcoholContent)
            time_to_sobriety_textView.text = resources.getQuantityString(R.plurals.time_to_sobriety_textView,
                drinkingPeriodInHours.toInt() - breathalyser!!.timeDifference(end!!).toInt(),
                drinkingPeriodInHours.toInt() - breathalyser!!.timeDifference(end!!).toInt())

        } else {
            Toast.makeText(this, R.string.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.breathalyser_menu_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add -> {
                val dialog = AddAlcoholDialog(this)
                dialog.show()
                return true
            }
            R.id.action_history -> {
                //rozpoczac aktywnosc historii
                val myIntent = Intent(this, CalculatorHistory::class.java)
                startActivity(myIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDatabaseReady(db: AppDatabase) {
        this.db = db

        if(breathalyser != null && end != null) {
            AsyncTask.execute {
                val history = BreathalyserHistory(gender = breathalyser!!.sex, weight = (breathalyser!!.weight).toDouble(),
                    drinkingTime = breathalyser!!.timeDifference(end!!),
                    quantity = breathalyser!!.getTotalAmountOfAlcohol(), dateTime = Calendar.getInstance().time)

                db.breathalyserHistoryDAO().insertAll(history)
                Log.d("test_historii", "insert")
            }
        }
    }

    override fun onDatabaseFail() {
        DatabaseNotFoundDialogFragment().show(supportFragmentManager, "dialog")
    }
}