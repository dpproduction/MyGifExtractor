package com.example.mygifextractor.viewModels

import android.text.TextUtils
import com.example.mygifextractor.ui.mygifextractor.network.DevLifeApi
import com.example.mygifextractor.ui.mygifextractor.network.data.DevLifeProperty
import com.example.mygifextractor.viewModels.base.GifStorageViewModel

class GifViewModel(private val section: String?) : GifStorageViewModel() {

    override suspend fun requestGifs(pagination: Int): List<DevLifeProperty> =
        if (TextUtils.isEmpty(section))
            listOf(DevLifeApi.retrofitService.getPropertyAsync().await())
        else
            DevLifeApi.retrofitService.getPropertiesAsync(section, pagination).await().result
}


