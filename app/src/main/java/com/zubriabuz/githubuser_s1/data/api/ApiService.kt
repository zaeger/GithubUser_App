package com.zubriabuz.githubuser_s1.data.api

import com.zubriabuz.githubuser_s1.BuildConfig
import com.zubriabuz.githubuser_s1.data.model.UserDetailResponse
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse
import retrofit2.http.*

interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGit(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserGitResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getUserDetailGit(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserDetailResponse

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getUserFollowerGit(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserGitResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getUserFollowingGit(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserGitResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGit(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserGitResponse
}