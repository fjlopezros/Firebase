package com.fjlr.firebase.repository

import android.view.View
import android.widget.Button
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Repositorio para operaciones relacionadas con el perfil del usuario.
 */
class SeguidoresRepositorio {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Seguir a un usuario.
     * Agrego los documentos correspondientes a las colecciones de seguidores y seguidos.
     * @param emailSeguidor Email del seguidor.
     * @param emailSeguido Email del seguido.
     */
    fun seguirUsuario(emailSeguidor: String, emailSeguido: String) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailSeguido)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDORES)
            .document(emailSeguidor)
            .set(mapOf(ConstantesUtilidades.EMAIL to emailSeguidor))

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailSeguidor)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .document(emailSeguido)
            .set(mapOf(ConstantesUtilidades.EMAIL to emailSeguido))
    }

    /**
     * Verificar si el usuario actual sigue a otro usuario.
     * @param emailUsuarioActual Email del usuario actual.
     * @param emailDelPerfil Email del perfil.
     * @param callback Función de devolución de llamada con el resultado de la verificación.
     */
    fun verificarSiSigue(
        emailUsuarioActual: String,
        emailDelPerfil: String,
        callback: (Boolean) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuarioActual)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .document(emailDelPerfil)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    callback(false)
                    return@addSnapshotListener
                }
                callback(snapshot.exists())
            }
    }

    /**
     * Dejar de seguir a un usuario.
     * Borra los documentos correspondientes en las colecciones de seguidores y seguidos.
     * @param emailSeguidor Email del seguidor.
     * @param emailSeguido Email del seguido.
     */
    fun dejarDeSeguir(emailSeguidor: String, emailSeguido: String) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailSeguido)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDORES)
            .document(emailSeguidor)
            .delete()

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailSeguidor)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .document(emailSeguido)
            .delete()
    }


    /**
     * Cuenta la cantidad de seguidores de un usuario.
     * @param email Email del usuario.
     * @param callback Función de devolución de llamada con el total de seguidores.
     */
    fun contarSeguidores(email: String, callback: (Int) -> Unit) {
        if(email.isBlank()){
            return
        }
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDORES)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    callback(ConstantesUtilidades.VACIO)
                    return@addSnapshotListener
                }
                callback(snapshots.size())
            }
    }


    /**
     * Cuenta la cantidad de usuarios seguidos por un usuario.
     * @param email Email del usuario.
     * @param callback Función de devolución de llamada con el total de usuarios seguidos.
     */
    fun contarSeguidos(email: String, callback: (Int) -> Unit) {
        if(email.isBlank()){
            return
        }
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) {
                    callback(ConstantesUtilidades.VACIO)
                    return@addSnapshotListener
                }
                callback(snapshots.size())
            }
    }

    /**
     * Oculta el botón de seguir si el usuario actual es el mismo que el del perfil.
     * @param bt Botón a ocultar.
     * @param emailDelPerfil Email del perfil.
     */
    fun ocultarSeguir(bt: Button, emailDelPerfil: String) {
        val emailUsuarioActual = FirebaseAuth.getInstance().currentUser?.email
        bt.visibility = if (emailUsuarioActual == emailDelPerfil) View.GONE else View.VISIBLE
    }
}