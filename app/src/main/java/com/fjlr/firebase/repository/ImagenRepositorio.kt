package com.fjlr.firebase.repository

import android.widget.ImageView
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.squareup.picasso.Picasso

/**
 * Repositorio para gestionar carga de imágenes, como foto de perfil.
 */
class ImagenRepositorio {

    /**
     * Carga una imagen aleatoria desde un servicio externo en el ImageView proporcionado.
     */
    fun fotoDePerfilAleatoria(fotoDePerfil: ImageView) {
        Picasso.get()
            .load(ConstantesUtilidades.URL_FOTO_ALEATORIA)
            .into(fotoDePerfil)
    }
}