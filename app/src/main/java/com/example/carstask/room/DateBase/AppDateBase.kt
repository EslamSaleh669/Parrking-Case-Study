package com.example.kotlin_task.Room.DateBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carstask.room.Enitity.Vehicle
import com.example.kotlin_task.Room.Dao.ItemDao


@Database(entities = [Vehicle::class], version = 2, exportSchema = false)
abstract class AppDateBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
         var INSTANCE: AppDateBase? = null
        fun getInstance(context: Context): AppDateBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDateBase::class.java,
                    "roomdb"
                ).build()
            }

            return INSTANCE as AppDateBase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}