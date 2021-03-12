package com.havverton.cinemapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        DetailsViewModel::class.java -> DetailsViewModel()
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}