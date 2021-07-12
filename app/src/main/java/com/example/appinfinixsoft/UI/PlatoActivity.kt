package com.example.appinfinixsoft.UI

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appinfinixsoft.adapters.PlatoAdapter
import com.example.appinfinixsoft.data.entity.PlatoResponse
import com.example.appinfinixsoft.data.entity.Result
import com.example.appinfinixsoft.databinding.ActivityPlatoBinding
import com.example.appinfinixsoft.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlatoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val toolbar = binding.toolbarPromociones.toolbar
        setSupportActionBar(toolbar).apply {
            title = "Promociones del día"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        /*Obteniene una lista de platos*/
        getPlatos()
    }

    /*LLama a la API para obtener una lista de platos*/
    private fun getPlatos(){
        val call = ApiClient.getServiceClient().getPlato()
        val callback = object : Callback<PlatoResponse>{
            override fun onResponse(
                call: Call<PlatoResponse>,
                response: Response<PlatoResponse>){
                if(response.isSuccessful){
                    response.body()?.let {
                        val lista = it.results
                        listaPlato(lista)
                    }
                }
            }

            override fun onFailure(callback: Call<PlatoResponse>, t: Throwable){
                t.printStackTrace()
            }
        }
        call.enqueue(callback)
    }

    /*Adaptador lista de platos */
    private fun listaPlato(platos : List<Result>){
        val adapter = PlatoAdapter(platos)
        binding.rvPlato.adapter = adapter
        binding.rvPlato.layoutManager = LinearLayoutManager(this,
        LinearLayoutManager.VERTICAL, false)
    }

    /*Función para la flecha de volver en el toolbar*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
