package com.zubriabuz.githubuser_s1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse

@Database(entities = [UserGitResponse.ItemsItem::class], version = 1, exportSchema = false)
abstract class GitDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}