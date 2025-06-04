package com.fjlr.firebase.repository

import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para operaciones relacionadas con datos del usuario en Firestore.
 */
class UsuarioRepositorio(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val aut: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    /**
     * Guarda el nombre del usuario en Firestore con su email como ID de documento.
     *
     * @param email Correo del usuario.
     * @param nombreUsuario Nombre que se mostrará.
     */
    suspend fun guardarNombreUsuario(
        email: String,
        nombreUsuario: String,
    ) {
        val usuario = mapOf(ConstantesUtilidades.USUARIO to nombreUsuario)
        firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .set(usuario, SetOptions.merge())
            .await()
    }

    /**
     * Actualiza el nombre del usuario en Firestore.
     * @param email Correo del usuario.
     * @param usuario Nuevo nombre de usuario.
     */
    suspend fun cambiarNombreUsuario(email: String, usuario: String) {
        val usuario = mapOf(ConstantesUtilidades.USUARIO to usuario)

        firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .set(usuario)
            .await()
    }

    /**
     * Cambia la contraseña del usuario.
     * @param email Correo del usuario.
     */
    suspend fun cambiarContrasena(email: String) {
        aut.sendPasswordResetEmail(email)
            .await()
    }

    /**
     * Obtiene la url de la foto de perfil del usuario.
     * @param email Correo del usuario.
     */
    suspend fun obtenerUrlFotoPerfil(email: String): String? {
        val doc = firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .get()
            .await()
        return doc.getString(ConstantesUtilidades.IMAGEN)
    }

}