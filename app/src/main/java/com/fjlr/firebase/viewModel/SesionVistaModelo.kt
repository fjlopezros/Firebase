package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio

class SesionVistaModelo() : ViewModel() {
    private val repositorio = AutenticacionRepositorio()

    fun iniciarSesion(correo: String, contrasena: String, callback: (Boolean, String?) -> Unit) {
        repositorio.iniciarSesion(correo, contrasena) { success, error ->
            callback(success, error)
        }
    }

    fun sesionActiva(): Boolean = repositorio.sesionActiva()
}