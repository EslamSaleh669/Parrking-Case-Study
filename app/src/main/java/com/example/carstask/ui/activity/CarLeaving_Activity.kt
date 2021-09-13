package com.example.carstask.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carstask.R
import com.example.carstask.room.Enitity.Vehicle
import com.example.carstask.room.carsViewModel
import com.example.carstask.ui.adapter.carsAdapter
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class CarLeaving_Activity : AppCompatActivity(), carsAdapter.CallbackInterface {
    private var recycleItem: RecyclerView? = null
    lateinit var viewModel: carsViewModel


    var money: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_leaving)
        recycleItem = findViewById(R.id.carsrecycler)

        showItems()

    }


    fun showItems() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(carsViewModel::class.java)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycleItem!!.layoutManager = linearLayoutManager


        viewModel.allCars.observe(this, Observer { items ->

            Log.d("****output", items.toString())
            if (items.size > 0) {
                for (i in items) {
                    check24(i)
                }
                viewModel.allCars.observe(this, Observer { items ->
                    if (items.size > 0) {
                        recycleItem!!.adapter = carsAdapter(items, this)
                    } else {
                        Toast.makeText(this, "Parking is Empty", Toast.LENGTH_LONG).show()

                    }
                })

                recycleItem!!.adapter = carsAdapter(items, this)
            } else {
                Toast.makeText(this, "Parking is Empty", Toast.LENGTH_LONG).show()

            }
        })
    }

    fun check24(vehicle: Vehicle) {
        val simpleDateFormatYearTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        val endDate: String = simpleDateFormatYearTime.format(Date())

        val hours: Int = getHoursParking(vehicle.fullDate, endDate)
        if (hours >= 24) {
            deleteItem24(vehicle.plate)
        }

    }

    override fun deletecar(index: Int, startDate: String) {
        val simpleDateFormatYearTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        val endDate: String = simpleDateFormatYearTime.format(Date())

        val hours: Int = getHoursParking(startDate, endDate)

        //// get list of hours
        val date = simpleDateFormatYearTime.parse(startDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, 1)
        var listHours = ArrayList<Date>()

        for (i in 0..hours - 1) {
            val date = simpleDateFormatYearTime.parse(startDate)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.HOUR, i)
            listHours.add(calendar.time)
        }

        getMoney(listHours)
        deleteItem(index)

    }

    fun getHoursParking(startDate: String, endDate: String): Int {
        val simpleDateFormatYearTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

        val date1: Date = simpleDateFormatYearTime.parse(startDate)
        val date2: Date = simpleDateFormatYearTime.parse(endDate)


        val diff: Long = date2.getTime() - date1.getTime()

        val seconds = (diff / 1000).toInt()
        val minutes = seconds / 60
        val x: Int = minutes.toInt() % 60
        var hours: Int = (minutes.toInt() / 60)
        if (x != 0) {
            hours++;
        }
        return hours;
    }

    fun getMoney(listDates: ArrayList<Date>) {
        for (i in listDates) {
            val simpleDateFormatYearTime = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

            val target: LocalTime = LocalTime.parse(simpleDateFormatYearTime.format(i))
            if (target.isAfter(LocalTime.parse("05:59:59"))
                &&
                target.isBefore(LocalTime.parse("10:00:00"))
            ) {
                money = money + 3;
            } else if (target.isAfter(LocalTime.parse("09:59:59"))
                &&
                (target.isBefore(
                    LocalTime.parse("23:59:59")
                ))
            ) {
                money = money + 1;
            } else if (target.isAfter(LocalTime.parse("23:59:59"))
                &&
                target.isBefore(LocalTime.parse("06:00:00"))
            ) {
                money = money + 0;
            }

        }
        Log.d("** m", money.toString());
        Toast.makeText(this, "Total Money : ${money.toString()} ", Toast.LENGTH_LONG).show()

    }

    fun deleteItem(index: Int) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(carsViewModel::class.java)
        viewModel.allCars.observe(this, androidx.lifecycle.Observer { cars ->
            doAsync {
                viewModel.delete(index)
            }


        }
        )

        val intent = Intent(this, CarEnterance_Activity::class.java)
        startActivity(intent)
        finish()

    }
    fun deleteItem24(index: Int) {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(carsViewModel::class.java)
        viewModel.allCars.observe(this, androidx.lifecycle.Observer { cars ->
            doAsync {
                viewModel.delete(index)
            }


        }
        )
        Toast.makeText(this, "Total Money for deleted car : 26 ", Toast.LENGTH_LONG).show()
        val intent = Intent(this, CarEnterance_Activity::class.java)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, CarEnterance_Activity::class.java)
        startActivity(intent)
        finish()
    }
}