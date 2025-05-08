package com.fjlr.firebase.repository

import android.util.Log
import android.view.View
import android.widget.Button
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SeguidoresRepositorio {
    private val db = FirebaseFirestore.getInstance()

    fun seguirUsuario(
        usuarioActual: String,
        usuarioASeguir: String,
        callback: (Boolean, String?) -> Unit
    ) {
        // Añadir a "seguidos" del usuario actual
        val seguidoRef =
            db.collection(ConstantesUtilidades.COLECCION_USUARIOS).document(usuarioActual)
                .collection(ConstantesUtilidades.COLECCION_SEGUIDOS).document(usuarioASeguir)

        // Añadir a "seguidores" del usuario al que se sigue
        val seguidorRef =
            db.collection(ConstantesUtilidades.COLECCION_USUARIOS).document(usuarioASeguir)
                .collection(ConstantesUtilidades.COLECCION_SEGUIDORES).document(usuarioActual)

        val data = ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()

        // Ejecutar ambas operaciones
        db.runBatch { batch ->
            batch.set(seguidoRef, data)
            batch.set(seguidorRef, data)
        }.addOnSuccessListener {
            callback(true, null)
        }.addOnFailureListener {
            callback(false, it.message)
        }
    }

    fun dejarDeSeguir(
        usuarioActual: String,
        usuarioADeselegir: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val seguidoRef =
            db.collection(ConstantesUtilidades.COLECCION_USUARIOS).document(usuarioActual)
                .collection(ConstantesUtilidades.COLECCION_SEGUIDOS).document(usuarioADeselegir)

        val seguidorRef =
            db.collection(ConstantesUtilidades.COLECCION_USUARIOS).document(usuarioADeselegir)
                .collection(ConstantesUtilidades.COLECCION_SEGUIDORES).document(usuarioActual)

        db.runBatch { batch ->
            batch.delete(seguidoRef)
            batch.delete(seguidorRef)
        }.addOnSuccessListener {
            callback(true, null)
        }.addOnFailureListener {
            callback(false, it.message)
        }
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

    fun ocultarSeguir(bt: Button, uidDelPerfil: String) {
        val uidUsuarioActual = FirebaseAuth.getInstance().currentUser?.email
        bt.visibility = if (uidUsuarioActual == uidDelPerfil) View.GONE else View.VISIBLE
    }
}