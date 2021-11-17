package com.example.carstestapp.model.entities

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("number") val number: Int,
    @SerializedName("date") val date: String,
    @SerializedName("state") val state: String
)