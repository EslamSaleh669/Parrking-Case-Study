package com.example.carstask.room.Enitity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Cars")
data class Vehicle(


    @PrimaryKey
    @ColumnInfo(name = "carplate")
    var plate:Int,

    @ColumnInfo(name = "enterancetime")
    var time : String,

    @ColumnInfo(name = "enterancedate")
    var date : String,

    @ColumnInfo(name = "enterfulldate")
    var fullDate : String,



    )