package com.zubriabuz.githubuser_s1.data.local

import android.content.Context
import androidx.room.Room

class ConfigDB (private val  context: Context){
    private val db = Room.databaseBuilder(context, GitDB::class.java, "user-git.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}