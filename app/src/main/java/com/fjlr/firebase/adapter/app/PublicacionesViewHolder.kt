package com.fjlr.firebase.adapter.app

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.AjustesVistaModelo
import com.squareup.picasso.Picasso

class PublicacionesViewHolder(private val binding: PublicacionesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavorito
    val ajustesVistaModelo = AjustesVistaModelo()

    fun bind(publicacion: PublicacionesModelo) {
        ajustesVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuario.text = nombre ?: "Nombre no disponible"
        }
        binding.tvTituloPublicacion.text = publicacion.titulo
        binding.tvDescripcionPublicacion.text = publicacion.descripcion
        binding.tvIngredientesPublicacion.text = publicacion.ingredientes
        binding.tvPreparacionPublicacion.text = publicacion.preparacion
        Picasso.get().load("https://robohash.org/fran").into(binding.ivFoto)

        itemView.setOnClickListener {
            Adaptador.abrirAjustesDesdeItem(itemView, publicacion.autor)
        }
    }
}