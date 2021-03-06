package com.example.appinfinixsoft.network

import com.example.appinfinixsoft.data.entity.PlatoResponse
import com.example.appinfinixsoft.data.entity.Recipe
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object ApiClient {
    private const val API_BASE_URL ="https://api.spoonacular.com/recipes/"
    private const val API_KEY ="?apiKey=b506f413c4ac43e5bfee812d1d8b192d"

    private var appInterface : AppService
    private var retrofitAdapter : Retrofit = Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        appInterface = retrofitAdapter.create(AppService::class.java)
    }

    fun getServiceClient() = appInterface

    interface AppService {

        /*Obteinen una lista de platos*/
        @GET("complexSearch${API_KEY}")
        fun getPlato() : Call<PlatoResponse>

        /*Obtiene un plato*/
        @GET("${API_BASE_URL}/{id}/information${API_KEY}")
        fun getPlatoDescripcion(@Path("id") id: Int): Call<Recipe>
    }
}