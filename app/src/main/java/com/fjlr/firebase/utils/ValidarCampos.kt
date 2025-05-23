package com.fjlr.firebase.utils

import com.fjlr.firebase.model.PublicacionesModelo

/**
 * Valida si los campos de la publicación están llenos.
 */
object ValidarCampos {
    fun validarCampos(publicacion: PublicacionesModelo) {
        if (publicacion.titulo.isEmpty() ||
            publicacion.descripcion.isEmpty() ||
            publicacion.ingredientes.isEmpty() ||
            publicacion.preparacion.isEmpty()
        ) {
            throw IllegalArgumentException("Todos los campos de la publicación deben estar llenos.")
        }
    }
}