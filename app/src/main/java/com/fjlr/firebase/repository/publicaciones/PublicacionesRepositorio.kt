package com.fjlr.firebase.repository.publicaciones

import android.util.Log
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.ValidarCampos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para manejar la carga general de publicaciones públicas sin filtros.
 */
class PublicacionesRepositorio {
    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario = FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

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
    /**
     * Sube una publicación nueva al Firestore.
     */
    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        try {
            ValidarCampos.validarCampos(publicacion)

            val docId = "${publicacion.titulo}_${emailUsuario}"
            val data = mapOf(
                ConstantesUtilidades.TITULO to publicacion.titulo,
                ConstantesUtilidades.DESCRIPCION to publicacion.descripcion,
                ConstantesUtilidades.INGREDIENTES to publicacion.ingredientes,
                ConstantesUtilidades.PREPARACION to publicacion.preparacion,
                ConstantesUtilidades.FAVORITO to false,
                ConstantesUtilidades.AUTOR to emailUsuario,
                ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
            )

            db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
                .document(docId)
                .set(data)
                .await()

        } catch (e: IllegalArgumentException) {
            Log.d("Error", "Error al subir la publicación ${e.message}")
        }
    }
}