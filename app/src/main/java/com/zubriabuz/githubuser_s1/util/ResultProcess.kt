package com.zubriabuz.githubuser_s1.util

sealed class ResultProcess {
    data class Success<out T>(val data: T) : ResultProcess()
    data class Error(val exception: Throwable) : ResultProcess()
    data class Loading(val isLoading: Boolean) : ResultProcess()

}