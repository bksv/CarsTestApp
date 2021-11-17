package com.example.carstestapp.model.repositories

import com.example.carstestapp.api.GitHackApi
import com.example.carstestapp.model.entities.CarApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val api: GitHackApi
) {
    fun getCars(): Flowable<CarApiResponse> = api.getCars()
}