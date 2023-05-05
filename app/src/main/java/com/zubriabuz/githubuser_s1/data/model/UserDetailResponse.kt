@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection")

package com.zubriabuz.githubuser_s1.data.model

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection", "SpellCheckingInspection")
data class UserDetailResponse(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("followers")
	val followers: Long? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Long? = null,

	@field:SerializedName("name")
	val name: String? = null,

)
