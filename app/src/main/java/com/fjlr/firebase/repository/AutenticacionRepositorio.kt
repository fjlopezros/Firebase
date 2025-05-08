package com.fjlr.firebase.repository

import android.util.Log
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AutenticacionRepositorio {

    private val autenticacion = FirebaseAuth.getInstance()

    fun crearCuenta(
        email: String,
        password: String,
        nombreUsuario: String,
        callback: (Boolean, String?) -> Unit
    ) {
        autenticacion.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = autenticacion.currentUser
                    if (user != null) {
                        user.sendEmailVerification()
                        val usuario = mapOf("usuario" to nombreUsuario)

                        FirebaseFirestore.getInstance()
                            .collection(ConstantesUtilidades.COLECCION_USUARIOS)
                            .document(email)
                            .set(usuario)
                            .addOnSuccessListener {
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                callback(false, e.message)
                            }
                    } else {
                        callback(false, "No se pudo obtener el Usuario del usuario")
                    }
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
    fun obtenerNombreUsuario(callback: (String?) -> Unit) {
        val email = FirebaseAuth.getInstance().currentUser?.email ?: return callback(null)
        FirebaseFirestore.getInstance()
            .collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .get()
            .addOnSuccessListener { document ->
                val nombreUsuario = document.getString("usuario")
                callback(nombreUsuario)
            }
            .addOnFailureListener {
                callback(null)
            }
    }
}