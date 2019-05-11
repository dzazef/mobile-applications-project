package pl.am2019.alkomaster

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import pl.am2019.alkomaster.db.AppDatabase

class OpenDatabase(private val context: Context) {
    interface OpenDatabaseListener {
        fun onDatabaseReady(db : AppDatabase)
    }

    private var listener : OpenDatabaseListener? = null

    fun setOpenDatabaseListener(listener : OpenDatabaseListener) {
        this.listener = listener
    }

    fun load() {
        if (listener == null) {
            Log.e("DEBUG_ERROR", "ERROR: Listener not found")
            return
        } else {
            Thread{
                try {
                    val db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "alcomaster.db"
                    ).build()
                    listener?.onDatabaseReady(db)
                } catch (e: Exception) {
                    Log.e("DEBUG_ERROR", e.message)
                }
            }.start()
        }
    }
}