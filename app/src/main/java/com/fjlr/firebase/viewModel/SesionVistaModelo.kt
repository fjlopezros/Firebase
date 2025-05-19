package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio

/**
 * ViewModel encargado de gestionar el inicio de sesión del usuario.
 */
class SesionVistaModelo : ViewModel() {

    private val autenticacionRepositorio = AutenticacionRepositorio()

    /**
     * Inicia sesión con email y contraseña.
     *
     * @param email Correo del usuario.
     * @param contrasena Contraseña del usuario.
     * @param callback Retorna true si inicia sesión, false con mensaje de error si falla.
     */
    fun iniciarSesion(email: String, contrasena: String, callback: (Boolean, String?) -> Unit) {
        autenticacionRepositorio.iniciarSesion(email, contrasena) { exito, error ->
            callback(exito, error)
        }
    }

    /**
     * Verifica si hay una sesión activa.
     *
     * @return true si hay sesión iniciada.
     */
    fun sesionActiva(): Boolean = autenticacionRepositorio.sesionActiva()
}