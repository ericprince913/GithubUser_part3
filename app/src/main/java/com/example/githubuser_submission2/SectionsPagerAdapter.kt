package com.example.githubuser_submission2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter (activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity) {

    private var bundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = followersActivity()
            1 -> fragment = followingActivity()
        }
        fragment?.arguments = this.bundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }




}