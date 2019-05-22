package pl.am2019.alkomaster.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pl.am2019.alkomaster.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View) {
        if (v == compbutton){
            val myIntent = Intent(this, ComparatorActivity::class.java)
            startActivity(myIntent)
        }
    }
}


