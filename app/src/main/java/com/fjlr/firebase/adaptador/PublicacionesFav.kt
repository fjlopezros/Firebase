package com.fjlr.firebase.adaptador

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.entity.PublicacionesEntity

class PublicacionesFav (private var publicacionesFav: MutableList<PublicacionesEntity>):
RecyclerView.Adapter<PublicacionesFav.PublicacionesFavViewHolder>(){

    inner class PublicacionesFavViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = PublicacionesItemBinding.bind(view)
        val icono: LottieAnimationView = binding.ibBotonFavorito

        fun bind(publicaciones: PublicacionesEntity) {
            binding.tvTituloPublicacion.text = publicaciones.titulo
            binding.tvDescripcionPublicacion.text = publicaciones.descripcion
            binding.tvIngredientesPublicacion.text = publicaciones.ingredientes
            binding.tvPreparacionPublicacion.text = publicaciones.preparacion
        }
    }
    private fun animacionIcono(
        imageView: LottieAnimationView,
        animation: Int,
        like: Boolean
    ): Boolean {
        return if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
            true
        } else {
            imageView.setImageResource(R.drawable.favorito)
            false
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesFavViewHolder = PublicacionesFavViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.publicaciones_item, parent, false)
    )

    override fun onBindViewHolder(
        holder: PublicacionesFavViewHolder,
        position: Int
    ) {
        val publicacion = publicacionesFav[position]
        holder.bind(publicacion)

        holder.icono.setOnClickListener {
            val nuevoEstado = animacionIcono(holder.icono, R.raw.favorito, publicacion.esFavorito)
            publicacion.esFavorito = nuevoEstado
        }
    }

    override fun getItemCount(): Int = publicacionesFav.size

    fun getFavoritos(): List<PublicacionesEntity> {
        return publicacionesFav.filter { it.esFavorito }
    }
}