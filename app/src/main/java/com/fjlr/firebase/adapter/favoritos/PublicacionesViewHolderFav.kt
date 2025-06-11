package com.fjlr.firebase.adapter.favoritos

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.ImagenPubli
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo

/**
 * Clase de ViewHolder para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param binding El item de layout de la publicación.
 */
class PublicacionesViewHolderFav(private val binding: PublicacionesItemFavoritosBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavoritoFav
    val utilidadesPerfilVistaModelo = UtilidadesPerfilVistaModelo()

    /**
     * Metodo para vincular los datos del modelo a la vista.
     * @param publicacion El modelo de datos de la publicación.
     */
    fun bind(publicacion: PublicacionesModelo) {
        utilidadesPerfilVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuarioFav.text = nombre ?: ConstantesUtilidades.NO_ENCONTRADO
        }
        binding.tvTituloPublicacionFav.text = publicacion.titulo

        ImagenPubli.cargarImagenPublicacion(publicacion.fotoPublicacion, binding.ivImagenPublicacionFavorito)

        itemView.setOnClickListener {
            Adaptador.abrirAjustesDesdeItem(itemView, publicacion.autor)
        }
    }
}