package pl.am2019.alkomaster

/**
 * przechowuje wage i stale zalezne od plci
 */
class Female(val weight : Int) : Human() {
    override val bodyWaterConstant: Float
        get() = 0.49f
    override val metabolismConstant: Float
        get() = 0.017f
}