package com.zubriabuz.githubuser_s1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zubriabuz.githubuser_s1.data.api.ApiClient
import com.zubriabuz.githubuser_s1.data.local.ConfigDB
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse
import com.zubriabuz.githubuser_s1.util.ResultProcess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class DetailViewModel(private val db: ConfigDB) : ViewModel() {

    val resultDetailUser = MutableLiveData<ResultProcess>()
    val resultFollowersUser = MutableLiveData<ResultProcess>()
    val resultFollowingUser = MutableLiveData<ResultProcess>()
    val resultFavouriteSuccess = MutableLiveData<Boolean>()
    val resultFavouriteDelete = MutableLiveData<Boolean>()


    private var isFavorite = false
    fun setFavoriteUser(item: UserGitResponse.ItemsItem) {
        viewModelScope.launch {
            item.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    resultFavouriteDelete.value = true
                } else {
                    db.userDao.insert(item)
                    resultFavouriteSuccess.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavoriteUser(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .getUserDetailGit(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = ResultProcess.Loading(true)
            }.onCompletion {
                resultDetailUser.value = ResultProcess.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = ResultProcess.Error(it)
            }.collect {
                resultDetailUser.value = ResultProcess.Success(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .getUserFollowerGit(username)

                emit(response)
            }.onStart {
                resultFollowersUser.value = ResultProcess.Loading(true)
            }.onCompletion {
                resultFollowersUser.value = ResultProcess.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowersUser.value = ResultProcess.Error(it)
            }.collect {
                resultFollowersUser.value = ResultProcess.Success(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .getUserFollowingGit(username)

                emit(response)
            }.onStart {
                resultFollowingUser.value = ResultProcess.Loading(true)
            }.onCompletion {
                resultFollowingUser.value = ResultProcess.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowingUser.value = ResultProcess.Error(it)
            }.collect {
                resultFollowingUser.value = ResultProcess.Success(it)
            }
        }
    }

    class Factory(private val db: ConfigDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}