package com.example.githubuser_submission2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_submission2.databinding.FollowerfollowingLayoutBinding

class followersActivity : Fragment(R.layout.followerfollowing_layout) {

    private var bindingg : FollowerfollowingLayoutBinding? = null
    private val binding get() = bindingg!!

    private lateinit var viewModel: followerViewModel
    private lateinit var adapter: userAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(detailActivity.EXTRA_PERSON).toString()
        bindingg = FollowerfollowingLayoutBinding.bind(view)

        adapter = userAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            recyclerView2.setHasFixedSize(true)
            recyclerView2.layoutManager = LinearLayoutManager(activity)
            recyclerView2.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(followerViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null)
                adapter.setUserList(it)
            showLoading(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingg = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar2.visibility = View.VISIBLE
        }else{
            binding.progressBar2.visibility = View.GONE
        }
    }
}

class followingActivity : Fragment(R.layout.followerfollowing_layout){
    private var bindingg : FollowerfollowingLayoutBinding? = null
    private val binding get() = bindingg!!

    private lateinit var viewModel: followingViewModel
    private lateinit var adapter: userAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(detailActivity.EXTRA_PERSON).toString()
        bindingg = FollowerfollowingLayoutBinding.bind(view)

        adapter = userAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            recyclerView2.setHasFixedSize(true)
            recyclerView2.layoutManager = LinearLayoutManager(activity)
            recyclerView2.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(followingViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null)
                adapter.setUserList(it)
            showLoading(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingg = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar2.visibility = View.VISIBLE
        }else{
            binding.progressBar2.visibility = View.GONE
        }
    }
}