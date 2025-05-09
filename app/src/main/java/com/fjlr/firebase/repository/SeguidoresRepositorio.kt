package com.fjlr.firebase.repository

import android.view.View
import android.widget.Button
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SeguidoresRepositorio {
    private val db = FirebaseFirestore.getInstance()

    fun seguirUsuario(emailSeguidor: String, emailSeguido: String) {
        db.collection("usuarios")
            .document(emailSeguido)
            .collection("seguidores")
            .document(emailSeguidor)
            .set(mapOf("email" to emailSeguidor))

        db.collection("usuarios")
            .document(emailSeguidor)
            .collection("seguidos")
            .document(emailSeguido)
            .set(mapOf("email" to emailSeguido))
    }


    fun verificarSiSigue(emailUsuarioActual: String, emailDelPerfil: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios")
            .document(emailUsuarioActual)
            .collection("seguidos")
            .document(emailDelPerfil)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    callback(false)
                    return@addSnapshotListener
                }
                callback(snapshot != null && snapshot.exists())
            }
    }

    fun dejarDeSeguir(emailSeguidor: String, emailSeguido: String) {
        db.collection("usuarios")
            .document(emailSeguido)
            .collection("seguidores")
            .document(emailSeguidor)
            .delete()

        db.collection("usuarios")
            .document(emailSeguidor)
            .collection("seguidos")
            .document(emailSeguido)
            .delete()
    }


    fun contarSeguidores(email: String, callback: (Int) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDORES)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    callback(0)
                    return@addSnapshotListener
                }
                val totalSeguidores = snapshots?.size() ?: 0
                callback(totalSeguidores)
            }
    }


    fun contarSeguidos(email: String, callback: (Int) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    callback(0)
                    return@addSnapshotListener
                }
                val totalSeguidores = snapshots?.size() ?: 0
                callback(totalSeguidores)
            }
    }

    fun ocultarSeguir(bt: Button, emailDelPerfil: String) {
        val emailUsuarioActual = FirebaseAuth.getInstance().currentUser?.email
        bt.visibility = if (emailUsuarioActual == emailDelPerfil) View.GONE else View.VISIBLE
    }
}