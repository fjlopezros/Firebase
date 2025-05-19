package com.fjlr.firebase.viewModel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.ImagenRepositorio
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio

/**
 * ViewModel para gestionar la vista de ajustes del usuario.
 */
class AjustesVistaModelo : ViewModel() {

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
     * Carga una imagen aleatoria en el ImageView pasado.
     */
    fun cargarFotoDePerfilAleatoria(fotoDePerfil: ImageView) {
        imagenRepositorio.fotoDePerfilAleatoria(fotoDePerfil)
    }
}
