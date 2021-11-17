package com.example.carstestapp.api

import com.example.carstestapp.model.entities.CarApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers


interface GitHackApi {
    @Headers("Content-Type: application/json")
    @GET("jsonplaceholder/cars-api.php")
    fun getCars(): Flowable<CarApiResponse>
}