package com.example.appinfinixsoft.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appinfinixsoft.data.entity.Result
import com.example.appinfinixsoft.databinding.ItemPlatoBinding
import com.example.appinfinixsoft.databinding.PlatoHomeBinding

class PlatoAdapter(var items: List<Result>): RecyclerView.Adapter<PlatoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlatoBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemPlatoBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(plato:Result){
            binding.tvTitle.text = plato.title
            Glide.with(binding.ivPlatoImg.context)
                .load(plato.image)
                .into(binding.ivPlatoImg)
        }
    }
}

/*Adaptador para la lista de platos en la Home*/
class PlatoHomeAdapter(var items: List<Result>, var clickListener:
      onItemClickListener): RecyclerView.Adapter<PlatoHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlatoHomeBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], clickListener)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: PlatoHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(plato:Result, action: onItemClickListener){
            binding.btnVerDetalle.setOnClickListener {
                action.onItemClick(plato, adapterPosition)
            }
            Glide.with(binding.ivFoto.context)
                .load(plato.image)
                .into(binding.ivFoto)
        }

    }

    interface onItemClickListener {
        fun onItemClick(item: Result, position: Int)
    }
}