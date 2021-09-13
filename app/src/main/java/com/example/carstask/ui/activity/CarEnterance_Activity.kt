package com.example.carstask.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.carstask.R
import com.example.carstask.room.Enitity.Vehicle
import com.example.carstask.room.carsViewModel
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.util.*

class CarEnterance_Activity : AppCompatActivity() {

    lateinit var viewModal: carsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // model = ViewModelProviders.of(this).get(carsViewModel::class.java)


        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(carsViewModel::class.java)

        val simpletimeFormat = SimpleDateFormat("HH:mm:ss")
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val simpleDateFormatYearTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")

        val currentTime: String = simpletimeFormat.format(Date())
        val currentDate: String = simpleDateFormat.format(Date())
        val currentTimeDate: String = simpleDateFormatYearTime.format(Date())


        val Platenumber = findViewById<EditText>(R.id.platenumber)


        findViewById<Button>(R.id.carslist).setOnClickListener {
            val intent = Intent(this, CarLeaving_Activity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.enterBtn).setOnClickListener {

            if (Platenumber.text.isEmpty()) {
                Toast.makeText(this, "Enter the plate Number", Toast.LENGTH_LONG).show()
            } else {

                viewModal.allCars.observe(this, androidx.lifecycle.Observer { cars ->
                    if (cars.size < 10) {
                        doAsync {

                            viewModal.insert(
                                Vehicle(
                                    Platenumber.text.toString().toInt(),
                                    currentTime, currentDate, currentTimeDate
                                )
                            )

                        }
                        Platenumber.setText("")


                    } else {
                        Toast.makeText(this, "No Empty Space in Parking", Toast.LENGTH_LONG).show()

                    }
                }
                )

            }
        }


    }


}