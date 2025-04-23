package com.fjlr.firebase.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.entity.PublicacionesEntity
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.PublicacionesItemBinding

class Publicaciones(
    private var publicaciones: MutableList<PublicacionesEntity>
) :
    RecyclerView.Adapter<Publicaciones.PublicacionesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesViewHolder = PublicacionesViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.publicaciones_item, parent, false)
    )

    override fun onBindViewHolder(
        holder: PublicacionesViewHolder,
        position: Int
    ) = holder.bind(publicaciones[position])

    override fun getItemCount(): Int = publicaciones.size

    inner class PublicacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PublicacionesItemBinding.bind(view)
        fun bind(publicaciones: PublicacionesEntity) {
            binding.tvTituloPublicacion.text = publicaciones.titulo
            binding.tvDescripcionPublicacion.text = publicaciones.descripcion
            binding.tvIngredientesPublicacion.text = publicaciones.ingredientes
        }
    }
}