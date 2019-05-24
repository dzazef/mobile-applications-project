package pl.am2019.alkomaster.breathalyser

class Breathalyser(val weight: Int, val sex: String, val start: String) {

    var alcoholList: ArrayList<AlcoholData> = ArrayList()

    fun addAlcohol(a: AlcoholData) {
        alcoholList.add(a)
    }

    fun removeAlcohol(a: AlcoholData) {
        alcoholList.remove(a)
    }

    fun getBloodAlcoholLevel(end: String) : Double {
        //TODO
        return 0.0
    }
}