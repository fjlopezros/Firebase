package com.fjlr.firebase.repository.publicaciones

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Repositorio para operaciones relacionadas con publicaciones seguidas.
 */
class SeguidosRepositorio {
    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario =
        FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

    /**
     * Carga las publicaciones de los usuarios seguidos.
     * @param callback Lista de publicaciones o vacía si hay error.
     */
    fun cargarPublicacionesSeguidos(
        callback: (List<PublicacionesModelo>) -> Unit
    ) {
        comprobarSeguidos { emailsSeguidos ->
            if (emailsSeguidos.isEmpty()) {
                callback(emptyList())
                return@comprobarSeguidos
            } else {
                escucharPublicacionesSeguidos(emailsSeguidos, callback)
            }
        }
    }

    /**
     * Comprueba si el usuario sigue a otros usuarios.
     * @param callback Lista de emails de usuarios seguidos o vacía si hay error.
     */
    private fun comprobarSeguidos(callback: (List<String>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario.toString())
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .get()
            .addOnSuccessListener { snapshot ->
                val listaEmails = snapshot?.documents?.mapNotNull {
                    it.getString(ConstantesUtilidades.EMAIL)
                } ?: emptyList()
                callback(listaEmails)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    /**
     * Escucha las publicaciones de los usuarios seguidos.
     * @param emailsSeguidos Lista de emails de usuarios seguidos.
     * @param callback Lista de publicaciones o vacía si hay error.
     */
    private fun escucharPublicacionesSeguidos(
        emailsSeguidos: List<String>,
        callback: (List<PublicacionesModelo>) -> Unit
    ) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val publicacionesFiltradas = snapshots.documents.mapNotNull { doc ->
                    val publicacion = doc.toObject(PublicacionesModelo::class.java)
                    val autor = publicacion?.autor
                    if (autor != null && autor in emailsSeguidos) publicacion else null
                }
                callback(publicacionesFiltradas)
            }
    }
}