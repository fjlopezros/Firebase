package com.fjlr.firebase.repository

import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth

/**
 * Repositorio encargado de gestionar la autenticación de usuarios con FirebaseAuth.
 */
class AutenticacionRepositorio(
    private val autenticacion: FirebaseAuth = FirebaseAuth.getInstance()
) {

    /**
     * Crea una cuenta con email y contraseña. También envía un correo de verificación.
     *
     * @param email Correo del usuario.
     * @param contrasena Contraseña.
     * @param callback Retorna true si se creó exitosamente, false con mensaje de error si falla.
     */
    fun crearCuenta(
        email: String,
        contrasena: String,
        callback: (Boolean, String?) -> Unit
    ) {
        if (email.isBlank() || contrasena.isBlank()) {
            callback(false, ConstantesUtilidades.ERROR_INICIAR_SESION)
            return
        }

        autenticacion.createUserWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //autenticacion.currentUser?.sendEmailVerification() //Verificacion
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    /**
     * Inicia sesión con email y contraseña.
     */
    fun iniciarSesion(email: String, contrasena: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank() || contrasena.isBlank()) {
            callback(false, ConstantesUtilidades.ERROR_INICIAR_SESION)
            return
        }

        autenticacion.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful, task.exception?.message)
            }
    }

    /**
     * Cierra la sesión actual del usuario.
     */
    fun cerrarSesion() {
        autenticacion.signOut()
    }

    /**
     * Verifica si hay una sesión de usuario activa.
     */
    fun sesionActiva(): Boolean = autenticacion.currentUser != null
}