package pl.am2019.alkomaster.breathalyser

import android.content.Context
import pl.am2019.alkomaster.HumanFactory
import pl.am2019.alkomaster.R

class Breathalyser(val weight: Int, val sex: String, private val start: String) {

    var alcoholList: ArrayList<AlcoholData> = ArrayList()
    private val constBodyWaterInBlood = 0.806
    private val factor = 1.2

    var context: Context? = null

    fun addAlcohol(a: AlcoholData) {
        alcoholList.add(a)
    }

    fun removeAlcohol(a: AlcoholData) {
        alcoholList.remove(a)
    }

    //oblicza poziom alkoholu we krwi w promilach na podstawie godziny konca, zaokraglony do dwoch miejsc po przecinku
    fun getBloodAlcoholContent(end: String) : Double {
        //TODO
        var bloodAlcoholContent = 0.0
        val SD = getTotalAmountOfAlcohol()/10 //ilosc drinkow, kazdy po 10 gram (przyjmuje, ze 1 mililitr wazy 1 gram)
        val human = HumanFactory.createHuman(sex, weight)
        val DP = timeDifference(end) // drinking period in hours

        if(human != null) {
            bloodAlcoholContent = ( (constBodyWaterInBlood * SD * factor) / (human.bodyWaterConstant * weight) - (human.metabolismConstant * DP) ) * 10
        }

        if(bloodAlcoholContent < 0) {
            bloodAlcoholContent = 0.0
        }

        Math.round(bloodAlcoholContent * 100) / 100.0

        return bloodAlcoholContent
    }

    //oblicza poziom alkoholu we krwi w promilach na podstawie ilosci godzin, ktore minely od rozpoczecia, zaokraglony do dwoch miejsc po przecinku
    fun getBloodAlcoholContent(period: Double) : Double {
        //TODO
        var bloodAlcoholContent = 0.0
        val SD = getTotalAmountOfAlcohol()/10 //ilosc drinkow, kazdy po 10 gram (przyjmuje, ze 1 mililitr wazy 1 gram)
        val human = HumanFactory.createHuman(sex, weight)
        val DP = period // drinking period in hours

        if(human != null) {
            bloodAlcoholContent = ( (constBodyWaterInBlood * SD * factor) / (human.bodyWaterConstant * weight) - (human.metabolismConstant * DP) ) * 10
        }

        if(bloodAlcoholContent < 0) {
            bloodAlcoholContent = 0.0
        }

        Math.round(bloodAlcoholContent * 100) / 100.0

        return bloodAlcoholContent
    }

    //ilosc czystego alkoholu w spozytym alkoholu
    fun getTotalAmountOfAlcohol() : Double { //w mililitrach
        var totalAmountOfAlcohol = 0.0
        for(alcoholData in alcoholList) {
            totalAmountOfAlcohol += alcoholData.getTotalAmountOfAlcohol()
        }
        return totalAmountOfAlcohol
    }

    //oblicza, ile godzin minelo od godziny start do godziny end
    fun timeDifference(end: String) : Double { //na api 22 nie dzialaly zadne daty i godziny :(

        val endHour = end.split(":")
        val endHours = Integer.parseInt(endHour[0])
        val endMinutes = Integer.parseInt(endHour[1])

        val startHour = start.split(":")
        val startHours = Integer.parseInt(startHour[0])
        val startMinutes = Integer.parseInt(startHour[1])

        val result: Double
        var numberOfMinutes = 0

        if(endHours > startHours) {
            numberOfMinutes = (endHours * 60 + endMinutes) - (startHours * 60 + startMinutes)
        } else {
            if(endHours < startHours) {
                numberOfMinutes = (endHours * 60 + endMinutes) + ((24-startHours) * 60 + startMinutes)
            } else {
                if(endHours == startHours) {
                    if(endMinutes >= startMinutes) {
                        numberOfMinutes = (endHours * 60 + endMinutes) - (startHours * 60 + startMinutes)
                    } else {
                        numberOfMinutes = (endHours * 60 + endMinutes) + (startHours * 60 + startMinutes)
                    }
                }
            }
        }

        result = numberOfMinutes/60.0

        return result
    }

    fun nextHour(period: Double) : String { //period w godzinach, zwraca godzine start+period
        val startHour = start.split(":")
        var startHours = Integer.parseInt(startHour[0])
        var startMinutes = Integer.parseInt(startHour[1])

        startMinutes += startHours * 60
        startMinutes += (period * 60).toInt()

        startHours = startMinutes/60
        startMinutes -= startHours * 60
        startHours = startHours.rem(24)


        var hourString = startHours.toString()
        var minuteString = startMinutes.toString()
        if(hourString.length < 2) {
            hourString = "0$hourString"
        }
        if(minuteString.length < 2) {
            minuteString = "0$minuteString"
        }
        return  context!!.getString(R.string.godzina, hourString, minuteString)

    }
}