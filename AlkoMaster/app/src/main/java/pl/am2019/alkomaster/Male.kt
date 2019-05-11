package pl.am2019.alkomaster

/**
 * przechowuje wage i stale zalezne od plci
 */
class Male(val weight : Int) : Human() {
    override val bodyWaterConstant: Float
        get() = 0.58f
    override val metabolismConstant: Float
        get() = 0.015f
}