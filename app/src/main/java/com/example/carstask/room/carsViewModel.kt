package com.example.carstask.room

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.carstask.room.Enitity.Vehicle
import com.example.kotlin_task.Room.DateBase.AppDateBase

class carsViewModel(application: Application) : AndroidViewModel(application) {
     val db: AppDateBase = AppDateBase.getInstance(application)
    val destroy = AppDateBase.destroyInstance()

    val allCars: LiveData<List<Vehicle>>
    val repository: CarRepo

    // on below line we are initializing
    // our dao, repository and all notes
    init {
        val dao = db.itemDao()
        repository = CarRepo(dao)
        allCars = repository.allItems
    }


    fun insert(car: Vehicle) {
        repository.insert(car)
    }

    fun delete(carNum: Int) {
        repository.delete(carNum)
    }


}