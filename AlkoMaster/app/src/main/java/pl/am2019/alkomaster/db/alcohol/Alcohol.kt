package pl.am2019.alkomaster.db.alcohol

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

//implementuje interfejs Parcelable potrzebny do zapisu przy rotacji
@Entity(tableName = "alcohol")
data class Alcohol (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "capacity")
    var capacity: Int,

    @ColumnInfo(name = "content")
    var content: Double,

    @ColumnInfo(name = "price")
    var price: Double?
) : Parcelable {
    private constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        name = parcel.readString(),
        capacity = parcel.readInt(),
        content = parcel.readDouble(),
        price = parcel.readDouble()
    )

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Alcohol> {
            override fun createFromParcel(parcel: Parcel) = Alcohol(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Alcohol>(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(capacity)
        parcel.writeDouble(content)
        if(price != null) {
            parcel.writeDouble(price!!)
        }
    }

    override fun describeContents() = 0
}