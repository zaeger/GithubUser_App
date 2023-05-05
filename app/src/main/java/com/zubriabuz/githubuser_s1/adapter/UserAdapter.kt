package com.zubriabuz.githubuser_s1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse
import com.zubriabuz.githubuser_s1.databinding.ItemUserBinding
import com.zubriabuz.githubuser_s1.helper.UserDiffCallback

class UserAdapter(
    private val data: MutableList<UserGitResponse.ItemsItem> = mutableListOf(),
    private val listener: (UserGitResponse.ItemsItem) -> Unit
) :

    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(newData: MutableList<UserGitResponse.ItemsItem>) {
        val diffCallback = UserDiffCallback(this.data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data.clear()
        this.data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class UserViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: UserGitResponse.ItemsItem) {
            v.ivImageProfile.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }

            v.tvUsername.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }
}