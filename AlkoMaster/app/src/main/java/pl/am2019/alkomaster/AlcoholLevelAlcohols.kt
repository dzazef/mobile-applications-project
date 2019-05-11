package pl.am2019.alkomaster

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AlcoholLevelAlcohols : AppCompatActivity() {
    private var weight : Int = 0 //waga
    private var start : String = "" //poczatek picia
    private var end : String = "" //koniec picia
    private var type : String = "" //plec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alcohol_level_alcohols)

        weight = intent.getIntExtra("weight", 0)
        start = intent.getStringExtra("start")
        end = intent.getStringExtra("end")
        type = intent.getStringExtra("type")

    }
}