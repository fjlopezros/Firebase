package com.fjlr.firebase.repository.publicaciones

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Repositorio para manejar la carga general de publicaciones públicas sin filtros.
 */
class PublicacionesRepositorio {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Carga todas las publicaciones ordenadas por fecha de creación descendente.
     * @param callback Lista de publicaciones o vacía si hay error.
     */
    fun cargarPublicaciones(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, error ->

                val publicaciones = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                } ?: emptyList()

                callback(publicaciones)
            }
    }
}