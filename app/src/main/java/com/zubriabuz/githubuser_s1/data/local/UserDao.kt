package com.zubriabuz.githubuser_s1.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserGitResponse.ItemsItem)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<UserGitResponse.ItemsItem>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): UserGitResponse.ItemsItem

    @Delete
    fun delete(user: UserGitResponse.ItemsItem)
}