package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio

class RegistroVistaModelo : ViewModel() {
    private val repositorio = AutenticacionRepositorio()

    fun registrarse(email: String, contrasena: String, callabck: (Boolean, String?) -> Unit) {
        repositorio.crearCuenta(email, contrasena) { success, error ->
            callabck(success, error)
        }
    }
}