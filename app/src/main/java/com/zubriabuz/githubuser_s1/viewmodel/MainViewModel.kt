package com.zubriabuz.githubuser_s1.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.zubriabuz.githubuser_s1.data.api.ApiClient
import com.zubriabuz.githubuser_s1.data.local.SettingPreferences
import com.zubriabuz.githubuser_s1.util.ResultProcess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: SettingPreferences): ViewModel() {

    val resultProcessUser = MutableLiveData<ResultProcess>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun getUser() {
        viewModelScope.launch {
                flow {
                    val response = ApiClient
                        .apiService
                        .getUserGit()

                    emit(response)
                }.onStart {
                    resultProcessUser.value = ResultProcess.Loading (true)
                }.onCompletion {
                    resultProcessUser.value = ResultProcess.Loading (false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultProcessUser.value = ResultProcess.Error(it)
                }.collect {
                    resultProcessUser.value = ResultProcess.Success(it)
                }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .searchUserGit(mapOf(
                        "q" to username,
                        "per_page" to 5
                    ))

                emit(response)
            }.onStart {
                resultProcessUser.value = ResultProcess.Loading (true)
            }.onCompletion {
                resultProcessUser.value = ResultProcess.Loading (false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultProcessUser.value = ResultProcess.Error(it)
            }.collect {
                resultProcessUser.value = ResultProcess.Success(it.items)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
            }
}