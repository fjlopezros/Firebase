package com.fjlr.firebase.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.ImagenRepositorio
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio
import com.fjlr.firebase.utils.ConstantesUtilidades

/**
 * ViewModel para gestionar la vista de ajustes del usuario.
 */
class UtilidadesPerfilVistaModelo : ViewModel() {

    private val autenticacionRepositorio = AutenticacionRepositorio()
    private val perfilRepositorio = PerfilRepositorio()
    private val imagenRepositorio = ImagenRepositorio()

    /**
     * Cierra la sesión del usuario actual.
     */
    fun cerrarSesion() {
        autenticacionRepositorio.cerrarSesion()
    }

    /**
     * Obtiene el nombre asociado a un email desde Firestore.
     *
     * @param email Email del usuario.
     * @param callback Retorna el nombre o null si no se encuentra.
     */
    fun obtenerNombreDeEmail(email: String, callback: (String?) -> Unit) {
        perfilRepositorio.obtenerNombreDeEmail(email, callback)
    }

    /**
     * Cuenta las publicaciones asociadas a un usuario por su email.
     *
     * @param emailDelPerfil Email del usuario.
     * @param callback Retorna el número de publicaciones.
     */
    fun contarPublicaciones(emailDelPerfil: String, callback: (Int) -> Unit) {
        perfilRepositorio.contarPublicaciones(emailDelPerfil, callback)
    }

    /**
     * Agrega la foto (PERFIL) en Storage y Firestore.
     *
     * @param imagen Uri de la imagen a subir.
     * @param email Email del usuario.
     */
    suspend fun agregarFotoPerfil(imagen: Uri, email: String) {
        imagenRepositorio.agregarFoto(imagen, email, ConstantesUtilidades.IMAGEN_PERFIL)
    }
}
