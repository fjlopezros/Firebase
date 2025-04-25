package com.fjlr.firebase.repository

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PublicacionesRepositorio {
    private val db = FirebaseFirestore.getInstance()

    fun cargarPublicaciones(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val lista = snapshots.documents.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                }

                callback(lista)
            }
    }

    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        validarCampos(publicacion)

        val publicaciones = hashMapOf(
            ConstantesUtilidades.TITULO to publicacion.titulo,
            ConstantesUtilidades.DESCRIPCION to publicacion.descripcion,
            ConstantesUtilidades.INGREDIENTES to publicacion.ingredientes,
            ConstantesUtilidades.PREPARACION to publicacion.preparacion,
            ConstantesUtilidades.FAVORITO to false,
            ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
        )

        val docId = "${publicacion.titulo}_" +
                "${FirebaseAuth.getInstance().currentUser?.email}"

        db.collection(ConstantesUtilidades.COLECCION_FIREBASE).document(docId)
            .set(publicaciones)
            .await()
    }

    private fun validarCampos(publicacion: PublicacionesModelo) {
        require(publicacion.titulo.isNotEmpty()) { "El título no puede estar vacío" }
        require(publicacion.descripcion.isNotEmpty()) { "La descripción no puede estar vacía" }
        require(publicacion.preparacion.isNotEmpty()) { "La preparación no puede estar vacía" }
        require(publicacion.ingredientes.isNotEmpty()) { "Los ingredientes no pueden estar vacíos" }
    }

}