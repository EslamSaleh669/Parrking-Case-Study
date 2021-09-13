package com.example.kotlin_task.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carstask.room.Enitity.Vehicle

@Dao
interface ItemDao {

    @Query("SELECT * FROM Cars")
    fun allCars(): LiveData<List<Vehicle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(car: Vehicle)

    @Query("Delete FROM Cars where carplate LIKE :quote_no")
    fun removeCar(quote_no: Int?)

}