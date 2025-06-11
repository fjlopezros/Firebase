package com.fjlr.firebase.adapter.app

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.ImagenPubli
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo

/**
 * Clase de ViewHolder para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param binding El item de layout de la publicación.
 */
class PublicacionesViewHolder(private val binding: PublicacionesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavorito
    val utilidadesPerfilVistaModelo = UtilidadesPerfilVistaModelo()

    /**
     * Metodo para vincular los datos del modelo a la vista.
     * @param publicacion El modelo de datos de la publicación.
     */
    fun bind(publicacion: PublicacionesModelo) {
        utilidadesPerfilVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuario.text = nombre ?: ConstantesUtilidades.NO_ENCONTRADO
        }
        binding.tvTituloPublicacion.text = publicacion.titulo
        binding.tvDescripcionPublicacion.text = publicacion.descripcion
        binding.tvIngredientesPublicacion.text = publicacion.ingredientes
        binding.tvPreparacionPublicacion.text = publicacion.preparacion

        ImagenPubli.cargarImagenPublicacion(publicacion.fotoPublicacion, binding.ivFoto)

        itemView.setOnClickListener {
            Adaptador.abrirAjustesDesdeItem(itemView, publicacion.autor)
        }
    }
}