package com.fjlr.firebase.repository

import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repositorio para operaciones relacionadas con datos del usuario en Firestore.
 */
class UsuarioRepositorio(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val aut: FirebaseAuth = FirebaseAuth.getInstance()
) {
    /**
     * Guarda el nombre del usuario en Firestore con su email como ID de documento.
     *
     * @param email Correo del usuario.
     * @param nombreUsuario Nombre que se mostrará.
     * @param callback Retorna true si se guardó, false con mensaje de error si falla.
     */
    fun guardarNombreUsuario(
        email: String,
        nombreUsuario: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val usuario = mapOf(ConstantesUtilidades.USUARIO to nombreUsuario)
        firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .set(usuario)
            .addOnSuccessListener { callback(true, null) }
            .addOnFailureListener { callback(false, it.message) }
    }

    fun cambiarNombreUsuario(email: String, usuario: String, callback: (Boolean) -> Unit) {
        val usuario = mapOf(ConstantesUtilidades.USUARIO to usuario)
        firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .set(usuario)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun cambiarContrasena(email: String, callback: (Boolean) -> Unit) {
        aut.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener { callback(false) }
    }
}