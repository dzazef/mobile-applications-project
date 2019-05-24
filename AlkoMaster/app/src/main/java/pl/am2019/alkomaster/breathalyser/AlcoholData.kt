package pl.am2019.alkomaster.breathalyser

import android.os.Parcel
import android.os.Parcelable
import pl.am2019.alkomaster.db.alcohol.Alcohol

/**
 * klasa przechowuje informacje o rodzaju wypitego alkoholu i jego ilosci w sztukach
 * obiekt tej klasy odpowiada itemowi w recycler view
 */
class AlcoholData(var type: Alcohol, var amount: Int) : Parcelable {

    /**
     * zwraca ilosc czystego alkoholu w tej pozycji
     */
    fun getTotalAmountOfAlcohol() : Double {
        return type.capacity * (type.content/100) * amount
    }

    private constructor(parcel: Parcel) : this(
        type = parcel.readParcelable<Alcohol>(Alcohol::class.java.classLoader),
        amount = parcel.readInt()
    )

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<AlcoholData> {
            override fun createFromParcel(parcel: Parcel) = AlcoholData(parcel)
            override fun newArray(size: Int) = arrayOfNulls<AlcoholData>(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeFloat(rating)
        parcel.writeParcelable(type, flags)
        parcel.writeInt(amount)
    }

    override fun describeContents() = 0
}