package pl.am2019.alkomaster

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