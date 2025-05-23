package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.UsuarioRepositorio

/**
 * ViewModel encargado del proceso de registro de nuevos usuarios.
 */
class RegistroVistaModelo : ViewModel() {

    private val autenticacionRepositorio = AutenticacionRepositorio()
    private val usuarioRepositorio = UsuarioRepositorio()

    /**
     * Registra un nuevo usuario creando su cuenta y guardando su nombre en Firestore.
     *
     * @param email Correo electrónico del usuario.
     * @param contrasena Contraseña para la cuenta.
     * @param nombreUsuario Nombre de usuario visible.
     * @param callback Retorna true si fue exitoso, false con mensaje de error si falla.
     */
    fun registrarse(
        email: String,
        contrasena: String,
        nombreUsuario: String,
        callback: (Boolean, String?) -> Unit
    ) {
        autenticacionRepositorio.crearCuenta(email, contrasena) { exito, error ->
            if (exito) {
                usuarioRepositorio.guardarNombreUsuario(
                    email,
                    nombreUsuario
                ) { guardado, errorGuardar ->
                    callback(guardado, errorGuardar)
                }
            } else {
                callback(false, error)
            }
        }
    }

    /**
     * Cambia el nombre de usuario de un usuario.
     * @param email Email del usuario.
     * @param usuario Nuevo nombre de usuario.
     * @param callback Retorna true si fue exitoso, false con mensaje de error si falla.
     */
    fun cambiarNombreUsuario(email: String, usuario: String, callback: (Boolean) -> Unit) {
        usuarioRepositorio.cambiarNombreUsuario(email, usuario, callback)
    }

    /**
     * Cambia la contraseña de un usuario.
     * @param email Email del usuario.
     * @param callback Retorna true si fue exitoso, false con mensaje de error si falla.
     */
    fun cambiarContrasena(email: String,callback: (Boolean) -> Unit) {
        usuarioRepositorio.cambiarContrasena(email, callback)
    }
}