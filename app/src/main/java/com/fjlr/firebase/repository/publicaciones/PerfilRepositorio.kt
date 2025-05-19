package com.fjlr.firebase.repository.publicaciones

import android.util.Log
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.ValidarCampos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para operaciones relacionadas con el perfil del usuario.
 */
class PerfilRepositorio {

    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario =
        FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

    /**
     * Carga las publicaciones de un usuario específico.
     */
    fun cargarTusPublicaciones(
        emailDelPerfil: String,
        callback: (List<PublicacionesModelo>) -> Unit
    ) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .whereEqualTo(ConstantesUtilidades.AUTOR, emailDelPerfil)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val listaPerfil =
                    snapshots.mapNotNull { it.toObject(PublicacionesModelo::class.java) }
                callback(listaPerfil)
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

    /**
     * Cuenta la cantidad de publicaciones de un usuario.
     */
    fun contarPublicaciones(emailDelPerfil: String, callback: (Int) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .whereEqualTo(ConstantesUtilidades.AUTOR, emailDelPerfil)
            .addSnapshotListener { snapshots, error ->
                callback(if (error != null || snapshots == null) 0 else snapshots.size())
            }
    }

    /**
     * Obtiene el nombre de usuario a partir del email.
     */
    fun obtenerNombreDeEmail(email: String, callback: (String?) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .get()
            .addOnSuccessListener { doc ->
                callback(doc.getString(ConstantesUtilidades.USUARIO))
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}