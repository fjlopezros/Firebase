package com.fjlr.firebase.adapter.favoritos

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.AjustesVistaModelo

class PublicacionesViewHolderFav(private val binding: PublicacionesItemFavoritosBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavoritoFav
    val ajustesVistaModelo = AjustesVistaModelo()

    fun bind(publicacion: PublicacionesModelo) {
        ajustesVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuarioFav.text = nombre ?: "Nombre no disponible"
        }
        binding.tvTituloPublicacionFav.text = publicacion.titulo

        itemView.setOnClickListener {
            Adaptador.abrirAjustesDesdeItem(itemView, publicacion.autor)
        }
    }
}