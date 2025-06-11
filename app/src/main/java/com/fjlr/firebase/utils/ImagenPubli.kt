package com.fjlr.firebase.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImagenPubli {
    /**
     * Carga la imagen de la publicación en el ImageView.
     *
     * @param urlImagen La URL de la imagen a cargar.
     */
    fun cargarImagenPublicacion(urlImagen: String, ivFoto: ImageView) {
        if (urlImagen.isNotEmpty() && urlImagen.startsWith("http")) {
            Picasso.get()
                .load(urlImagen)
                .fit()
                .centerCrop()
                .into(ivFoto)
        }
    }
}