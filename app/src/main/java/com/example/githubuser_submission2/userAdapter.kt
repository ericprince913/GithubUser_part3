package com.example.githubuser_submission2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser_submission2.databinding.UseritemBinding

class userAdapter : RecyclerView.Adapter<userAdapter.UserViewHolder>() {

    private val listUserItems = ArrayList<ItemsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUserList(users: ArrayList<ItemsItem>){
        listUserItems.clear()
        listUserItems.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: UseritemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(itemItem: ItemsItem){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(itemItem)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(itemItem.avatarUrl)
                    .centerCrop()
                    .into(gambarUser)
                tvName.text = itemItem.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UseritemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUserItems[position])
    }

    override fun getItemCount(): Int = listUserItems.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }

}