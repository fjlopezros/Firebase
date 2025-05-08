package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.PublicacionesRepositorio

class AjustesVistaModelo : ViewModel() {
    private val repositorioAutenticacion = AutenticacionRepositorio()
    private val repositorioPublicaciones = PublicacionesRepositorio()

    fun cerrarSesion() {
        repositorioAutenticacion.cerrarSesion()
    }

    fun obtenerNombreUsuario(callback: (String?) -> Unit) {
        repositorioAutenticacion.obtenerNombreUsuario(callback)
    }

    fun contarPublicaciones(callback: (Int) -> Unit) {
        repositorioPublicaciones.contarPublicaciones(callback)
    }
}