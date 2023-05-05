package com.zubriabuz.githubuser_s1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zubriabuz.githubuser_s1.data.local.ConfigDB

class FavoriteViewModel(private val configDB: ConfigDB) : ViewModel() {

    fun getUserFavorite() = configDB.userDao.loadAll()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val db: ConfigDB) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T

    }
}