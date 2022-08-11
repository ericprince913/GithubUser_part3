package com.example.githubuser_submission2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert
    suspend fun addFavorite(userFavorite: FavoriteDataUser)

    @Query("select * from favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteDataUser>>

    @Query("select count(*) from favorite_user where favorite_user.id = :id")
    suspend fun checkUser(id: Int):Int

    @Query("delete from favorite_user where favorite_user.id = :id")
    suspend fun removeFavorite(id: Int): Int
}