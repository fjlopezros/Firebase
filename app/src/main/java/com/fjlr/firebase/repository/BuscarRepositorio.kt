package com.fjlr.firebase.repository

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repositorio para manejar la búsqueda de recetas.
 */
class BuscarRepositorio {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Realiza una búsqueda de recetas por título.
     * @param tituloReceta Título de la receta a buscar.
     * @param callback Lista de publicaciones o vacía si hay error.
     */
    fun buscarRecetas(tituloReceta: String, callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .orderBy(ConstantesUtilidades.TITULO)
            .startAt(tituloReceta)
            .endAt(tituloReceta + "\uf8ff")
            .addSnapshotListener { snapshots, error ->
                val publicaciones = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                } ?: emptyList()

                callback(publicaciones)
            }
    }
}