package com.fjlr.firebase.adapter.favoritos

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.AjustesVistaModelo

/**
 * Clase de ViewHolder para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param binding El item de layout de la publicación.
 */
class PublicacionesViewHolderFav(private val binding: PublicacionesItemFavoritosBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavoritoFav
    val ajustesVistaModelo = AjustesVistaModelo()

    /**
     * Metodo para vincular los datos del modelo a la vista.
     * @param publicacion El modelo de datos de la publicación.
     */
    fun bind(publicacion: PublicacionesModelo) {
        ajustesVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuarioFav.text = nombre ?: "Null"
        }
        binding.tvTituloPublicacionFav.text = publicacion.titulo

        itemView.setOnClickListener {
            Adaptador.abrirAjustesDesdeItem(itemView, publicacion.autor)
        }
    }
}