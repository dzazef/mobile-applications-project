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
    var price: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeInt(capacity)
        parcel.writeDouble(content)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Alcohol

        if (id != other.id) return false
        if (name != other.name) return false
        if (capacity != other.capacity) return false
        if (content != other.content) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + capacity
        result = 31 * result + content.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }

    override fun toString(): String = "[ID: $id, name: $name, capacity: $capacity, content: $content, price: $price]"

    companion object CREATOR : Parcelable.Creator<Alcohol> {
        override fun createFromParcel(parcel: Parcel): Alcohol {
            return Alcohol(parcel)
        }

        override fun newArray(size: Int): Array<Alcohol?> {
            return arrayOfNulls(size)
        }
    }
}
