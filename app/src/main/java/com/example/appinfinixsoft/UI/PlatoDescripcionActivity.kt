package com.example.appinfinixsoft.UI

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appinfinixsoft.adapters.IngredienteAdapter
import com.example.appinfinixsoft.data.entity.ExtendedIngredient
import com.example.appinfinixsoft.data.entity.Recipe
import com.example.appinfinixsoft.databinding.ActivityPlatoDescripcionBinding
import com.example.appinfinixsoft.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlatoDescripcionActivity : AppCompatActivity() {

    companion object {
        const val KEY_ID: String = "infinixSoft.idPlato"
    }

    private lateinit var binding: ActivityPlatoDescripcionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPlatoDescripcionBinding.inflate(LayoutInflater.from(this))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*Obtiene un plato*/
        getPlato()
    }

    /*Llamada a la API para obtener un plato*/
    private fun getPlato(){
        val id: Bundle? = intent.extras

        val call = id?.let {
            ApiClient.getServiceClient().getPlatoDescripcion(it.getInt(KEY_ID))
        }

        val callback =  object :Callback<Recipe>{
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if(response.isSuccessful){

                    response.body()?.let {
                        plato ->
                        val toolbar = binding.toolbarDescripcion.toolbar
                        setSupportActionBar(toolbar).apply {
                            title = plato.sourceName
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        }
                        Glide.with(this@PlatoDescripcionActivity)
                            .load(plato.image)
                            .into(binding.ivFoto)

                        binding.tvNombreComida.text = plato.sourceName
                        binding.tvDescripcion.text = plato.title
                        binding.tvPrecio.text = "$ ${plato.pricePerServing}"

                        if(plato.glutenFree){
                            binding.tvTipo.text = "Apto céliacos"
                        }
                        listaIngredientes(plato.extendedIngredients)
                    }
                }
            }

            override fun onFailure(call: Call<Recipe>, t: Throwable) {
                t.printStackTrace()
            }
        }
        call?.enqueue(callback)
    }

    /*Adaptador lista de ingredientes*/
    private fun listaIngredientes(ingredient: List<ExtendedIngredient>){
        val adapter = IngredienteAdapter(ingredient)
        binding.rvIngredientes.adapter = adapter
        binding.rvIngredientes.layoutManager = LinearLayoutManager(this,
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