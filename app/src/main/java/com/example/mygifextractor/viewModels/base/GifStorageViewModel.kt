package com.example.mygifextractor.viewModels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygifextractor.ui.mygifextractor.network.data.DevLifeProperty
import kotlinx.coroutines.launch

enum class DevLifeApiStatus { LOADING, ERROR, DONE, EMPTY }

abstract class GifStorageViewModel : ViewModel() {

    var isAtFirstPage = MutableLiveData<Boolean>()

    private var queryPageNumber = -1

    private var currentPage = -1
    private val cache = ArrayList<DevLifeProperty>()

    private val _status = MutableLiveData<DevLifeApiStatus>()

    val status: LiveData<DevLifeApiStatus>
        get() = _status

    private val _property = MutableLiveData<DevLifeProperty>()

    val property: LiveData<DevLifeProperty>
        get() = _property

    init {
        println("baseViewModel initialized")
        isAtFirstPage.value = true
        fetchData()
    }

    fun onNext() {
        if (showFromCache()) {
            _property.value = cache[++currentPage]
            println("onNext false class $javaClass")
            isAtFirstPage.value = false
            _status.value = DevLifeApiStatus.DONE
        } else {
            fetchData()
        }
    }

    fun onBack() {
        if (currentPage > 0) {
            if (_status.value == DevLifeApiStatus.DONE) {
                currentPage--
            }
            _property.value = cache[currentPage]
        }
        println("onBack isAtFirstPage ${currentPage == 0} class $javaClass")
        isAtFirstPage.value = currentPage == 0
        _status.value = DevLifeApiStatus.DONE
    }

    private fun fetchData() = viewModelScope.launch {
        try {
            println("fetchData   isAtFirstPage.value ${  isAtFirstPage.value }")
            _status.value = DevLifeApiStatus.LOADING
            val data = requestGifs(++queryPageNumber)
            if (data.isEmpty()) {
                _status.value = DevLifeApiStatus.EMPTY
            } else {
                _property.value = data.first()
                cache.addAll(data)
                _status.value = DevLifeApiStatus.DONE
                currentPage++
                if (currentPage != 0) {
                    println("fetchData isAtFirstPage false class $javaClass")
                    isAtFirstPage.value = false
                }
            }
        } catch (e: Exception) {
            queryPageNumber--
            _status.value = DevLifeApiStatus.ERROR
            _property.value = null
        }
    }

    private fun showFromCache() =
        cache.isNotEmpty() && currentPage < cache.size - 1

    abstract suspend fun requestGifs (pagination: Int): List<DevLifeProperty>


}