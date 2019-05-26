package pl.am2019.alkomaster.breathalyser

import pl.am2019.alkomaster.Human


/**
 * przechowuje wage i stale zalezne od plci
 */
class Male(override val weight : Int) : Human() {
    override val bodyWaterConstant: Float
        get() = 0.58f
    override val metabolismConstant: Float
        get() = 0.015f
}