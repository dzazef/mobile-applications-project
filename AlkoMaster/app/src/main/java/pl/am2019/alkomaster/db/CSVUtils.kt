package pl.am2019.alkomaster.db

import android.content.Context
import pl.am2019.alkomaster.R
import pl.am2019.alkomaster.db.alcohol.Alcohol
import java.io.BufferedReader
import java.io.InputStreamReader

class CSVUtils(private val context : Context) {
    fun getAlcoholList(): List<Alcohol> {
        val alcoholList = mutableListOf<Alcohol>()
        val istream = context.resources.openRawResource(R.raw.beer)
        val breader = BufferedReader(InputStreamReader(istream))
        var line = breader.readLine()
        while(line != null) {
            val values = line.split(";")
            alcoholList.add(
                Alcohol(
                    name = values[0],
                    capacity = values[1].toInt(),
                    content = values[2].toDouble(),
                    price = 0.0
                )
            )
            line = breader.readLine()
        }
        istream.close()
        breader.close()
        return alcoholList
    }
}