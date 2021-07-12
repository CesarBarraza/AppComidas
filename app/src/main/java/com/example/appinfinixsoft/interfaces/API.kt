package com.example.appinfinixsoft.interfaces

import com.example.appinfinixsoft.data.entity.Plato
import retrofit2.Call
import retrofit2.http.GET


interface API {
    @GET("apiKey=3d3f4c1c1377490aa1789adfb8c241bf")
    fun getPlatos(): Call<List<Plato>>
}