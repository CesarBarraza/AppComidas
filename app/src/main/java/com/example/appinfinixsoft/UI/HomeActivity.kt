package com.example.appinfinixsoft.UI

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appinfinixsoft.UI.PlatoDescripcionActivity.Companion.KEY_ID
import com.example.appinfinixsoft.adapters.PlatoHomeAdapter
import com.example.appinfinixsoft.data.entity.PlatoResponse
import com.example.appinfinixsoft.data.entity.Result
import com.example.appinfinixsoft.data.local.SharedPreferencesManager
import com.example.appinfinixsoft.databinding.ActivityHomeBinding
import com.example.appinfinixsoft.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), PlatoHomeAdapter.onItemClickListener {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val nombre = SharedPreferencesManager.getUser(this@HomeActivity)?.nombre

        binding.tvUsuario.apply {
            text = "Hola, ${nombre}"
        }

        /*Ontiene lista de platos*/
        getPlatos()

        /*Navega a la seccion de oferta*/
        binding.tvVerMas.setOnClickListener {
            val intent = Intent(this, PlatoActivity::class.java)
            startActivity(intent)
        }
    }

    /*Llama a la API para obtener una lista de platos*/
    private fun getPlatos(){
        val call = ApiClient.getServiceClient().getPlato()
        val callback = object : Callback<PlatoResponse>{
            override fun onResponse(call: Call<PlatoResponse>, response: Response<PlatoResponse>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        val lista = it.results
                        listaPlatos(lista)
                    }
                }
            }

            override fun onFailure(call: Call<PlatoResponse>, t: Throwable) {
                t.printStackTrace()
            }
        }
        call.enqueue(callback)
    }

    /*Adaptador de lista de platos horizontal*/
    private fun listaPlatos(platos: List<Result>){
        var adapter = PlatoHomeAdapter(platos, this)
        binding.rvImagen.adapter = adapter
        binding.rvImagen.layoutManager = LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL, false)
    }

    /*Función que envia el id del plato selecciona en la lista*/
    override fun onItemClick(item: Result, position: Int) {
        val intent = Intent(this@HomeActivity, PlatoDescripcionActivity::class.java)
        intent.putExtra(KEY_ID, item.id)
        startActivity(intent)
    }
}