package com.aditya.data

sealed class ViewModelResult<out T> {
    data object Loading : ViewModelResult<Nothing>()
    data class Success<out T>(val data: T) : ViewModelResult<T>()
    data class Error(val exception: Throwable) : ViewModelResult<Nothing>()
}