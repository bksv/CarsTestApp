package com.example.carstestapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.carstestapp.R
import com.example.carstestapp.databinding.CarItemBinding
import com.example.carstestapp.model.entities.Car

class CarAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var cars: List<Car> = listOf()

    private var filterCriteria = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = cars[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun updateData(updatedCars: List<Car>) {
        var carsFiltered: MutableList<Car> = mutableListOf()

        if (filterCriteria.isNotEmpty() && filterCriteria != "No Filter") {
            when (filterCriteria) {
                "Available" -> {
                    for (car in updatedCars) {
                        if (car.state == "available") {
                            carsFiltered.add(car)
                        }
                    }
                    cars = carsFiltered
                    notifyDataSetChanged()
                }
                "Hidden" -> {
                    for (car in updatedCars) {
                        if (car.state == "hidden") {
                            carsFiltered.add(car)
                        }
                    }
                    cars = carsFiltered
                    notifyDataSetChanged()
                }
                "Disabled" -> {
                    for (car in updatedCars) {
                        if (car.state == "disabled") {
                            carsFiltered.add(car)
                        }
                    }
                    cars = carsFiltered
                    notifyDataSetChanged()
                }
            }
        } else {
            cars = updatedCars
            notifyDataSetChanged()
        }
    }

    fun filter(filter: String){
        filterCriteria = filter
        updateData(cars)
    }

    inner class CarViewHolder(var binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.context = binding.root.context
            binding.car = car
            binding.root.setOnClickListener {
                AlertDialog.Builder(fragment.requireContext())
                    .setTitle(car.number.toString())
                    .setMessage("Status: ${car.state} \n${car.date}")
                    .setPositiveButton(R.string.close) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }


}