package com.example.githubuser_submission2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_submission2.databinding.FavoriteuserBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: FavoriteuserBinding
    private lateinit var adapter : userAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = FavoriteuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = userAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : userAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteActivity, detailActivity::class.java).also {
                    it.putExtra(detailActivity.EXTRA_PERSON, data.login)
                    it.putExtra(detailActivity.EXTRA_USERID, data.id)
                    it.putExtra(detailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerView3.setHasFixedSize(true)
            recyclerView3.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recyclerView3.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this){
            if(it != null){
                val list = mapList(it)
                adapter.setUserList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteDataUser>): ArrayList<ItemsItem> {
        val listUsers = ArrayList<ItemsItem>()
        for(user in users){
            val userMapped = ItemsItem(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}