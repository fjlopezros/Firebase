package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.AutenticacionRepositorio

class AjustesVistaModelo : ViewModel() {
    private val repositorio = AutenticacionRepositorio()

    fun cerrarSesion() {
        repositorio.cerrarSesion()
    }

    fun obtenerUsuarioActual(): String =
        repositorio.obtenerUsuarioActual()?.email ?: "Correo no encontrado"

}