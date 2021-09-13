package com.example.carstask.room

import androidx.lifecycle.LiveData
import com.example.carstask.room.Enitity.Vehicle
import com.example.kotlin_task.Room.Dao.ItemDao

class CarRepo(private val itemDao: ItemDao) {

    internal val allItems: LiveData<List<Vehicle>> = itemDao.allCars()

    fun insert(car: Vehicle) {
        itemDao.insertCar(car)
    }


    fun delete(carNum: Int){
        itemDao.removeCar(carNum)
    }

}