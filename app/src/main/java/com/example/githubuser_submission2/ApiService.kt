package com.example.githubuser_submission2

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Headers("Authorization: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")
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<DataUser>

    @Headers("Authorization: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")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailDataUser>

    @Headers("Authorization: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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @Headers("Authorization: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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}