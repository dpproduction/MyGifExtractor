package com.example.mygifextractor.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.mygifextractor.ui.mygifextractor.network.DevLifeApi
import com.example.mygifextractor.ui.mygifextractor.network.data.DevLifeProperty
import com.example.mygifextractor.viewModels.base.GifStorageViewModel

class GifViewModel (val section: String?) : GifStorageViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    override suspend fun requestGifs(pagination: Int): List<DevLifeProperty> {
        return if (section != null) {
            DevLifeApi.retrofitService.getPropertiesAsync(section, pagination).await().result
        } else listOf(DevLifeApi.retrofitService.getPropertyAsync().await())
    }
}


