package com.fjlr.firebase.adapter.ajustes

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.utils.ImagenPubli

/**
 * Clase de ViewHolder para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param binding El item de layout de la publicación.
 */
class PublicacionesViewHolderAjustes(private val binding: PublicacionesItemAjustesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Metodo para vincular los datos del modelo a la vista.
     * @param publicacion El modelo de datos de la publicación.
     */
    fun bind(publicacion: PublicacionesModelo) {
        binding.tvTituloPublicacionAjustes.text = publicacion.titulo

        ImagenPubli.cargarImagenPublicacion(publicacion.fotoPublicacion, binding.ivImagenPublicacionAjustes)

        itemView.setOnClickListener {
            Adaptador.abrirDetalleDesdeItemAjustes(itemView, publicacion)
        }
    }
}