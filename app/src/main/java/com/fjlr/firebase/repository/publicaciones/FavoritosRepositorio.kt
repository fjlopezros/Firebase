package com.fjlr.firebase.repository.publicaciones

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FavoritosRepositorio {

    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario = FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

    fun guardarFavorito(publicacion: PublicacionesModelo) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        val data = mapOf(
            ConstantesUtilidades.TITULO to publicacion.titulo,
            ConstantesUtilidades.DESCRIPCION to publicacion.descripcion,
            ConstantesUtilidades.INGREDIENTES to publicacion.ingredientes,
            ConstantesUtilidades.PREPARACION to publicacion.preparacion,
            ConstantesUtilidades.FAVORITO to true,
            ConstantesUtilidades.AUTOR to publicacion.autor,
            ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
        )

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .set(data)
    }

    fun eliminarFavorito(publicacion: PublicacionesModelo) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .delete()
    }

    fun esFavorito(publicacion: PublicacionesModelo, callback: (Boolean) -> Unit) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .get()
            .addOnSuccessListener { document ->
                callback(document.exists())
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun cargarFavoritos(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val favoritos = snapshots.documents.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                }

                callback(favoritos)
            }
    }
}