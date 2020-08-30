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
        isAtFirstPage.value = true
    }

    fun onNext() {
        if (showFromCache()) {
            _property.value = cache[++currentPage]
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
        isAtFirstPage.value = currentPage == 0
        _status.value = DevLifeApiStatus.DONE
    }

    fun fetchData() = viewModelScope.launch {
        try {
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