package com.fjlr.firebase.repository.publicaciones

import android.util.Log
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repositorio para operaciones relacionadas con el perfil del usuario.
 */
class PerfilRepositorio {

    private val db = FirebaseFirestore.getInstance()

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

                val listaPerfil = snapshots?.documents?.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                } ?: emptyList()

                callback(listaPerfil)
            }
    }

    /**
     * Cuenta la cantidad de publicaciones de un usuario.
     */
    fun contarPublicaciones(emailDelPerfil: String, callback: (Int) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .whereEqualTo(ConstantesUtilidades.AUTOR, emailDelPerfil)
            .addSnapshotListener { snapshots, error ->

                callback(snapshots?.size() ?: ConstantesUtilidades.VACIO)

            }
    }

    /**
     * Obtiene el nombre de usuario a partir del email.
     */
    fun obtenerNombreDeEmail(email: String?, callback: (String?) -> Unit) {
        if (email.isNullOrBlank()) {
            callback(null)
            return
        }
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