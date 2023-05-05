package com.zubriabuz.githubuser_s1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zubriabuz.githubuser_s1.data.local.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val preferences: SettingPreferences) : ViewModel() {

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDark)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val preferences: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ThemeViewModel(preferences) as T
    }
}