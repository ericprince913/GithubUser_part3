package com.example.githubuser_submission2

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteDataUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
): Serializable