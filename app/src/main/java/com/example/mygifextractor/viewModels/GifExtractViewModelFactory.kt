package com.example.mygifextractor.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GifExtractViewModelFactory(

    private val section: String?) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GifViewModel::class.java)) {
            return GifViewModel(section) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}