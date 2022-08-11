package com.example.githubuser_submission2

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class searchViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()
    fun setSearchUsers(query: String){
        val client = ApiConfig.getApiService().getSearchUsers(query)
            client.enqueue(object: Callback<DataUser> {
                override fun onResponse(
                    call: Call<DataUser>,
                    response: Response<DataUser>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items as ArrayList<ItemsItem>?)
                    }
                }

                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<ItemsItem>> {
        return listUsers
    }

}

class detailViewModel (application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailDataUser>()

    private var userDao: NoteDao?
    private var noteRoomDb: NoteRoomDatabase?

    init {
        noteRoomDb = NoteRoomDatabase.getDatabase(application)
        userDao = noteRoomDb?.userFavoriteDao()
    }

    fun setUserDetail(username: String?){
        val client = ApiConfig.getApiService()
            .getUserDetail(username.toString())
            .enqueue(object : Callback<DetailDataUser> {
                override fun onResponse(
                    call: Call<DetailDataUser>,
                    response: Response<DetailDataUser>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailDataUser>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailDataUser> {
        return user
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteDataUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}

class followerViewModel : ViewModel() {
    val Followers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful){
                        Followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<ItemsItem>> {
        return Followers
    }
}

class followingViewModel : ViewModel() {
    val Following = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful){
                        Following.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemsItem>> {
        return Following
    }
}

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: NoteDao?
    private var noteRoomDb: NoteRoomDatabase?

    init {
        noteRoomDb = NoteRoomDatabase.getDatabase(application)
        userDao = noteRoomDb?.userFavoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteDataUser>>?{
        return userDao?.getFavoriteUser()
    }
}

class SettingViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}