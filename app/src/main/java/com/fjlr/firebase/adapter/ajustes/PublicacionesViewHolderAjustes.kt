package com.fjlr.firebase.adapter.ajustes

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.viewModel.AjustesVistaModelo

/**
 * Clase de ViewHolder para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param binding El item de layout de la publicación.
 */
class PublicacionesViewHolderAjustes(private val binding: PublicacionesItemAjustesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavoritoAjustes
    val ajustesVistaModelo = AjustesVistaModelo()

    /**
     * Metodo para vincular los datos del modelo a la vista.
     * @param publicacion El modelo de datos de la publicación.
     */
    fun bind(publicacion: PublicacionesModelo) {
        ajustesVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuarioAjustes.text = nombre ?: ConstantesUtilidades.NULL
        }
        binding.tvTituloPublicacionAjustes.text = publicacion.titulo

        itemView.setOnClickListener {
            Adaptador.abrirDetalleDesdeItemAjustes(itemView, publicacion)
        }
    }
}