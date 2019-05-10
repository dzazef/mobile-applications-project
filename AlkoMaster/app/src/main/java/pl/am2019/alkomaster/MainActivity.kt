package pl.am2019.alkomaster

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    //narazie tylko button, ktory uuchamia aktywnosc do obliczania promili
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startAlcoholLevelActivity(view: View) {
        val myIntent = Intent(this, LevelActivityData::class.java )
        startActivity(myIntent)
    }

}
