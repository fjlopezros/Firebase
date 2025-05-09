package com.fjlr.firebase.viewModel

import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.SeguidoresRepositorio
import com.google.firebase.auth.FirebaseAuth

class SeguidoresVistaModelo : ViewModel() {

    private val repositorio = SeguidoresRepositorio()

    fun seguirUsuario(emailSeguidor: String, emailSeguido: String) {
        repositorio.seguirUsuario(emailSeguidor, emailSeguido)
    }

    fun dejarDeSeguir(emailSeguidor:String, usuarioADeselegir: String) {
        repositorio.dejarDeSeguir(emailSeguidor, usuarioADeselegir)
    }

    fun verificarSiSigue(
        emailUsuarioActual: String,
        emailDelPerfil: String,
        callback: (Boolean) -> Unit
    ) {
        repositorio.verificarSiSigue(emailUsuarioActual, emailDelPerfil, callback)
    }

    fun contarSeguidores(email: String, callback: (Int) -> Unit) {
        repositorio.contarSeguidores(email, callback)
    }

    fun contarSeguidos(email: String, callback: (Int) -> Unit) {
        repositorio.contarSeguidos(email, callback)
    }

    fun ocultarSeguir(bt: Button, email: String) {
        repositorio.ocultarSeguir(bt, email)
    }

}