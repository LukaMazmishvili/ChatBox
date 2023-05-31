package com.example.chatbox.common

sealed class Resource<T> {
    data class Success<T>(val userToken: T) : Resource<T>()
    data class Error<T>(val errorMsg: String) : Resource<T>()
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()
}
