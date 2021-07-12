package com.example.appinfinixsoft.data.local

import android.content.Context
import com.example.appinfinixsoft.data.entity.Usuario
import com.google.gson.Gson

object SharedPreferencesManager {
    private const val DATOS_EN_MEMORIA: String ="InfinixSoft_db"
    private const val USUARIO_KEY: String = "Infinix.usuario"
    private val gson = Gson()

    fun getPreferences(context: Context) =
        context.getSharedPreferences(DATOS_EN_MEMORIA, Context.MODE_PRIVATE)

    /*Función para guardar un usuario en memoria*/
    fun saveUser(context: Context, usuario: Usuario?){
        getPreferences(context)
            .edit()
            .putString(USUARIO_KEY, gson.toJson(usuario))
            .apply()
    }

    /*Función para recuperar un usuario que esta en memoria*/
    fun getUser(context: Context): Usuario?{
        val userInJson = getPreferences(context)
            .getString(USUARIO_KEY, "")
        return gson.fromJson(userInJson, Usuario::class.java)
    }

    fun clearData(context: Context){
        getPreferences(context).edit().clear().apply()
    }
}