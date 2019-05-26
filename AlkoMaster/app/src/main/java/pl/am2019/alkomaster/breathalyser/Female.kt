package pl.am2019.alkomaster.breathalyser

import pl.am2019.alkomaster.Human


/**
 * przechowuje wage i stale zalezne od plci
 */
class Female(override val weight : Int) : Human() {
    override val bodyWaterConstant: Float
        get() = 0.49f
    override val metabolismConstant: Float
        get() = 0.017f
}