package com.fjlr.firebase.repository

import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

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
     */
    suspend fun crearCuenta(
        email: String,
        contrasena: String
    ): Result<Unit> {
        if (email.isBlank() || contrasena.isBlank()) {
            return Result.failure(Exception(ConstantesUtilidades.ERROR_INICIAR_SESION))
        }

        return try {
            autenticacion.createUserWithEmailAndPassword(email, contrasena).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Inicia sesión con email y contraseña.
     * @param email Correo del usuario.
     * @param contrasena Contraseña.
     */
    suspend fun iniciarSesion(email: String, contrasena: String): Result<Unit> {
        if (email.isBlank() || contrasena.isBlank()) {
            return Result.failure(Exception(ConstantesUtilidades.ERROR_INICIAR_SESION))
        }

        return try {
            autenticacion.signInWithEmailAndPassword(email, contrasena).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
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