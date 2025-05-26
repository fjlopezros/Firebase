package com.fjlr.firebase.repository.publicaciones

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para operaciones relacionadas con favoritos.
 */
class FavoritosRepositorio {

    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario =
        FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

    /**
     * Guarda una publicación como favorita en Firestore.
     * @param publicacion Publicación a guardar.
     */
    suspend fun guardarFavorito(publicacion: PublicacionesModelo) {
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
            .await()
    }

    /**
     * Elimina una publicación de la lista de favoritos en Firestore.
     * @param publicacion Publicación a eliminar.
     */
    suspend fun eliminarFavorito(publicacion: PublicacionesModelo) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .delete()
            .await()
    }

    /**
     * Verifica si una publicación está en la lista de favoritos.
     * @param publicacion Publicación a verificar.
     */
    suspend fun esFavorito(publicacion: PublicacionesModelo): Boolean {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        return try {
            val documento = db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
                .document(emailUsuario)
                .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
                .document(docId)
                .get()
                .await()

            documento.exists()
        }catch(_:Exception){
            false
        }
    }

    /**
     * Carga las publicaciones marcadas como favoritas.
     * @param callback Lista de publicaciones o vacía si hay error.
     */
    fun cargarFavoritos(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario)
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .addSnapshotListener { snapshots, error ->

                val favoritos = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                } ?: emptyList()

                callback(favoritos)
            }
    }
}