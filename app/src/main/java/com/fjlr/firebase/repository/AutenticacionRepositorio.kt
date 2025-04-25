package com.fjlr.firebase.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AutenticacionRepositorio {

    private val autenticacion = FirebaseAuth.getInstance()

    fun crearCuenta(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        autenticacion.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun iniciarSesion(email: String, contrasena: String, callback: (Boolean, String?) -> Unit) {
        autenticacion.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    fun cerrarSesion() {
        autenticacion.signOut()
    }

    fun sesionActiva(): Boolean {
        return autenticacion.currentUser != null
    }

    fun obtenerUsuarioActual(): FirebaseUser? {
        return autenticacion.currentUser
    }
}