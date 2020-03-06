package com.example.admin.weathershow.data.apiservice

import com.example.admin.weathershow.data.entity.FollowersEntity
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("users/HexlDL/followers")
    fun getFollowers(): Observable<MutableList<FollowersEntity>>
}