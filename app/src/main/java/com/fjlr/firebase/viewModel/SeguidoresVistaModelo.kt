package com.fjlr.firebase.viewModel

import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.repository.SeguidoresRepositorio
import com.google.firebase.auth.FirebaseAuth

class SeguidoresVistaModelo : ViewModel() {

    private val repositorio = SeguidoresRepositorio()

    fun seguirUsuario(usuarioASeguir: String) {
        val emailActual = FirebaseAuth.getInstance().currentUser?.email ?: return

        repositorio.seguirUsuario(emailActual, usuarioASeguir) { exito, error ->
            if (exito) {
                Log.d("Seguimiento", "Ahora sigues a $usuarioASeguir")
            } else {
                Log.e("Seguimiento", "Error: $error")
            }
        }
    }

    fun dejarDeSeguir(usuarioADeselegir: String) {
        val emailActual = FirebaseAuth.getInstance().currentUser?.email ?: return

        repositorio.dejarDeSeguir(emailActual, usuarioADeselegir) { exito, error ->
            if (exito) {
                Log.d("Seguimiento", "Dejaste de seguir a $usuarioADeselegir")
            } else {
                Log.e("Seguimiento", "Error: $error")
            }
        }
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