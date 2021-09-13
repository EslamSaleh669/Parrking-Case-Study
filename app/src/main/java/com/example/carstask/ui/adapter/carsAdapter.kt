package com.example.carstask.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carstask.R
import com.example.carstask.room.Enitity.Vehicle

class carsAdapter(val Cars: List<Vehicle>, val callbackInterface: CallbackInterface) :
    RecyclerView.Adapter<carsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): carsAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_viewshape, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: carsAdapter.ViewHolder, position: Int) {
        holder.PlateNum.text = Cars[position].plate.toString()
        holder.Datee.text = Cars[position].date
        holder.Timee.text = Cars[position].time



        holder.Exit.setOnClickListener(
            View.OnClickListener {
                callbackInterface.deletecar(Cars[position].plate,Cars[position].fullDate)

            }
        )
    }



    override fun getItemCount(): Int {
        return Cars.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val PlateNum = itemView.findViewById<TextView>(R.id.carnum)
        val Timee = itemView.findViewById<TextView>(R.id.cartime)
        val Datee = itemView.findViewById<TextView>(R.id.cardate)
        val Exit = itemView.findViewById<Button>(R.id.carexit)

    }

    interface CallbackInterface {
        fun deletecar(index: Int,date:String)
    }

}