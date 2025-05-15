package com.fjlr.firebase.viewModel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.PublicacionesRepositorio

class AjustesVistaModelo : ViewModel() {
    private val repositorioAutenticacion = AutenticacionRepositorio()
    private val repositorioPublicaciones = PublicacionesRepositorio()

    fun cerrarSesion() {
        repositorioAutenticacion.cerrarSesion()
    }

    fun obtenerNombreDeEmail(email: String, callback: (String?) -> Unit) {
        repositorioPublicaciones.obtenerNombreDeEmail(email, callback)
    }

    fun contarPublicaciones(emailDelPerfil: String, callback: (Int) -> Unit) {
        repositorioPublicaciones.contarPublicaciones(emailDelPerfil, callback)
    }

    fun fotoDePerfilAleatoria(fotoDePerfil: ImageView) {
        repositorioAutenticacion.fotoDePerfilAleatoria(fotoDePerfil)
    }
}