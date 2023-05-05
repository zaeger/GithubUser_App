package com.zubriabuz.githubuser_s1.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserGitResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: MutableList<ItemsItem>
) {
	@Entity(tableName = "user")
	@Parcelize
	data class ItemsItem(

		@field:ColumnInfo(name = "Id")
		@field:PrimaryKey
		@field:SerializedName("id")
		val id: Int,

		@field:ColumnInfo(name = "Login")
		@field:SerializedName("login")
		val login: String,

		@field:ColumnInfo(name = "avatar_url")
		@field:SerializedName("avatar_url")
		val avatarUrl: String?,

		) :Parcelable

}


