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

    fun obtenerNombreUsuario(email:String, callback: (String?) -> Unit) {
        repositorioAutenticacion.obtenerNombreUsuario(email, callback)
    }

    fun obtenerNombreDeEmail(email:String, callback: (String?) -> Unit){
        repositorioPublicaciones.obtenerNombreDeEmail(email, callback)
    }

    fun contarPublicaciones(emailDelPerfil:String, callback: (Int) -> Unit) {
        repositorioPublicaciones.contarPublicaciones(emailDelPerfil, callback)
    }
}