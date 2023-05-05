package com.zubriabuz.githubuser_s1.helper

import androidx.recyclerview.widget.DiffUtil
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse

class UserDiffCallback(
    private val oldData: MutableList<UserGitResponse.ItemsItem>,
    private val newData: MutableList<UserGitResponse.ItemsItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldData[oldItemPosition].login == newData[newItemPosition].login

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldData[oldItemPosition] == newData[newItemPosition]
}