package com.example.appinfinixsoft.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appinfinixsoft.data.entity.ExtendedIngredient
import com.example.appinfinixsoft.databinding.IngredientesBinding

class IngredienteAdapter(var items: List<ExtendedIngredient>):
    RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IngredientesBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: IngredientesBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(ingredient: ExtendedIngredient){
            binding.tvIngredientes.text = "- ${ingredient.original}"
        }
    }
}