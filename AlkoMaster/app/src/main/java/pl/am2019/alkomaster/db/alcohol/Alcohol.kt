package pl.am2019.alkomaster.db.alcohol

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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
)