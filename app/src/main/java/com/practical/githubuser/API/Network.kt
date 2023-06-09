package com.practical.githubuser.API

import com.practical.githubuser.data.UserData
import com.practical.githubuser.util.URL.BASE_URL
import com.practical.githubuser.util.URL.FOLLOWERS
import com.practical.githubuser.util.URL.USERPROFILE
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Network {



    @GET(USERPROFILE)
    suspend fun getUserData(@Path("username") username: String): UserData

    @GET(FOLLOWERS)
    suspend fun getFollowers(@Path("username") username: String): List<UserData>

    companion object {
        var apiService: Network? = null
        fun getInstance(): Network {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(Network::class.java)
            }
            return apiService!!
        }
    }

}