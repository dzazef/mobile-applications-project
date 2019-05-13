package pl.am2019.alkomaster.db.comparator_history

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import pl.am2019.alkomaster.db.alcohol.Alcohol

@Entity(
    tableName = "comparator_alcohol",
    primaryKeys = ["comparator_id", "alcohol_id"],
    foreignKeys = [
        ForeignKey(entity = ComparatorHistory::class, parentColumns = ["id"], childColumns = ["comparator_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Alcohol::class, parentColumns = ["id"], childColumns = ["alcohol_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["comparator_id", "alcohol_id"]), Index(value = ["alcohol_id"]), Index(value = ["comparator_id"])]
)
data class ComparatorAlcohol (
    @ColumnInfo(name = "comparator_id")
    var comparatorId : Long,

    @ColumnInfo(name = "alcohol_id")
    var alcoholId : Long
)