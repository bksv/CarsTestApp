package com.example.carstestapp.model.entities

import com.google.gson.annotations.SerializedName

data class CarApiResponse (
	@SerializedName("cars") val cars : List<Car>
)