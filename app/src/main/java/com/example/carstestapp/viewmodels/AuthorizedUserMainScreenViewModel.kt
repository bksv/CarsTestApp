package com.example.carstestapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carstestapp.model.entities.CarApiResponse
import com.example.carstestapp.model.repositories.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class AuthorizedUserMainScreenViewModel @Inject constructor(
    private val repository: CarRepository
) : ViewModel() {

    private val _cars: MutableLiveData<CarApiResponse> = MutableLiveData()
    val cars: LiveData<CarApiResponse> = _cars

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    fun getCars() {
        val disposable = repository.getCars()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {
                    _error.postValue(it)
                },
                onComplete = {},
                onNext = {
                    _cars.postValue(it)
                }
            )
    }
}