package pl.am2019.alkomaster

import pl.am2019.alkomaster.breathalyser.Female
import pl.am2019.alkomaster.breathalyser.Male

/**
 * statyczna metoda do tworzenia obiektu Human z podana waga
 */
object HumanFactory {
    fun createHuman(type: String, weight: Int) : Human? {
        var human : Human? = null
        when(type) {
            "male" -> human = Male(weight)
            "female" -> human = Female(weight)
        }
        return human
    }
}