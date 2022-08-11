package com.example.githubuser_submission2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser_submission2.databinding.DetailuserBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class detailActivity : AppCompatActivity() {

    private lateinit var binding: DetailuserBinding
    private lateinit var viewModel: detailViewModel
    var ToogleCheked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailuser)
        binding = DetailuserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(EXTRA_PERSON)
        val id = intent.getIntExtra(EXTRA_USERID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_PERSON,username)

        viewModel = ViewModelProvider(this).get(detailViewModel::class.java)
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this){ detailDataUser ->
            if (detailDataUser!=null){
                binding.apply {
                    namauser.text = detailDataUser.name
                    usernameuser.text = detailDataUser.login
                    tampilfollower.text = "${detailDataUser.followers} \n Followers"
                    tampilfollowing.text = "${detailDataUser.following} \n Following"
                    tempattinggal.text = detailDataUser.location
                    kantor.text = detailDataUser.company
                    repository.text = "${detailDataUser.publicRepos.toString()} \n repository"

                    Glide.with(this@detailActivity)
                        .load(detailDataUser.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(fotouser)
                }
            }
        }


        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count > 0){
                        binding.toggleButton.isChecked = true
                        ToogleCheked = true
                    }else{
                        binding.toggleButton.isChecked = false
                        ToogleCheked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener{
            ToogleCheked = !ToogleCheked
            if(ToogleCheked){
                viewModel.addFavorite(username.toString(), id, avatarUrl.toString())
            }else{
                viewModel.removeFavorite(id)
            }
            binding.toggleButton.isChecked = ToogleCheked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this,bundle)
        binding.apply {
            viewPager2.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout1,viewPager2){tab,position ->
                tab.text = resources.getString(TAB_TITTLE[position])
            }.attach()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.setting_menu -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_PERSON = "extra_person"
        const val EXTRA_USERID = "extra_userid"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITTLE = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }


}