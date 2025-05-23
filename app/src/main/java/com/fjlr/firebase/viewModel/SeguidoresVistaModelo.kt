package com.fjlr.firebase.viewModel

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.SeguidoresRepositorio

/**
 * Vista modelo para la pantalla de seguidores.
 */
class SeguidoresVistaModelo : ViewModel() {

    private val repositorio = SeguidoresRepositorio()

    /**
     * Seguir a un usuario.
     * @param emailSeguidor Email del seguidor.
     * @param emailSeguido Email del seguido.
     */
    fun seguirUsuario(emailSeguidor: String, emailSeguido: String) {
        repositorio.seguirUsuario(emailSeguidor, emailSeguido)
    }

    /**
     * Dejar de seguir a un usuario.
     * @param emailSeguidor Email del seguidor.
     * @param usuarioADeselegir Email del seguido.
     */
    fun dejarDeSeguir(emailSeguidor:String, usuarioADeselegir: String) {
        repositorio.dejarDeSeguir(emailSeguidor, usuarioADeselegir)
    }

    /**
     * Verificar si un usuario sigue a otro.
     * @param emailUsuarioActual Email del usuario actual.
     * @param emailDelPerfil Email del perfil del usuario.
     * @param callback Retorna true si sigue, false si no.
     */
    fun verificarSiSigue(
        emailUsuarioActual: String,
        emailDelPerfil: String,
        callback: (Boolean) -> Unit
    ) {
        repositorio.verificarSiSigue(emailUsuarioActual, emailDelPerfil, callback)
    }

    /**
     * Cuenta la cantidad de seguidores de un usuario.
     * @param email Email del usuario.
     * @param callback Retorna la cantidad de seguidores.
     */
    fun contarSeguidores(email: String, callback: (Int) -> Unit) {
        repositorio.contarSeguidores(email, callback)
    }

    /**
     * Cuenta la cantidad de seguidos de un usuario.
     * @param email Email del usuario.
     * @param callback Retorna la cantidad de seguidos.
     */
    fun contarSeguidos(email: String, callback: (Int) -> Unit) {
        repositorio.contarSeguidos(email, callback)
    }

    /**
     * Ocultar el botón de seguir cuando es tu propia cuenta.
     * @param bt Botón a mostrar.
     * @param email Email del usuario.
     */
    fun ocultarSeguir(bt: Button, email: String) {
        repositorio.ocultarSeguir(bt, email)
    }

}