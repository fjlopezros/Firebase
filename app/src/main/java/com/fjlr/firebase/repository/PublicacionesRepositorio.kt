package com.fjlr.firebase.repository

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PublicacionesRepositorio {
    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario = FirebaseAuth.getInstance().currentUser?.email

    fun cargarPublicaciones(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val lista = snapshots.documents.mapNotNull { doc ->
                    doc.toObject(PublicacionesModelo::class.java)
                }

                callback(lista)
            }
    }

    fun cargarPublicacionesFavoritas(callback: (List<PublicacionesModelo>) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario.toString())
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val listaFavoritos = mutableListOf<PublicacionesModelo>()
                if (snapshots != null) {
                    for (doc in snapshots.documents) {
                        val publicacion = doc.toObject(PublicacionesModelo::class.java)
                        if (publicacion != null) {
                            listaFavoritos.add(publicacion)
                        }
                    }
                }
                callback(listaFavoritos)
            }
    }


    fun cargarTusPublicaciones(callback: (List<PublicacionesModelo>) -> Unit) {
        if (emailUsuario != null) {
            db
                .collection(ConstantesUtilidades.COLECCION_FIREBASE)
                .whereEqualTo(ConstantesUtilidades.AUTOR, emailUsuario)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        callback(emptyList())
                        return@addSnapshotListener
                    }

                    val listaPerfil = mutableListOf<PublicacionesModelo>()
                    snapshots?.documents?.forEach { doc ->
                        doc.toObject(PublicacionesModelo::class.java)?.let { listaPerfil.add(it) }
                    }
                    callback(listaPerfil)
                }
        }
    }

    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        validarCampos(publicacion)
        val publicaciones = hashMapOf(
            ConstantesUtilidades.TITULO to publicacion.titulo,
            ConstantesUtilidades.DESCRIPCION to publicacion.descripcion,
            ConstantesUtilidades.INGREDIENTES to publicacion.ingredientes,
            ConstantesUtilidades.PREPARACION to publicacion.preparacion,
            ConstantesUtilidades.FAVORITO to false,
            ConstantesUtilidades.AUTOR to emailUsuario,
            ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
        )

        val docId = "${publicacion.titulo}_" + "$emailUsuario"

        db.collection(ConstantesUtilidades.COLECCION_FIREBASE).document(docId)
            .set(publicaciones)
            .await()
    }

    fun guardarFavorito(publicacion: PublicacionesModelo) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        val publicaciones = hashMapOf(
            ConstantesUtilidades.TITULO to publicacion.titulo,
            ConstantesUtilidades.DESCRIPCION to publicacion.descripcion,
            ConstantesUtilidades.INGREDIENTES to publicacion.ingredientes,
            ConstantesUtilidades.PREPARACION to publicacion.preparacion,
            ConstantesUtilidades.FAVORITO to true,
            ConstantesUtilidades.AUTOR to publicacion.autor,
            ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
        )

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario.toString())
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .set(publicaciones)
    }

    fun eliminarFavorito(publicacion: PublicacionesModelo) {
        val docId = "${publicacion.titulo}_${publicacion.autor}"

        db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario.toString())
            .collection(ConstantesUtilidades.COLECCION_FAVORITOS)
            .document(docId)
            .delete()
    }

    fun contarPublicaciones(callback: (Int) -> Unit) {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .whereEqualTo(ConstantesUtilidades.AUTOR, emailUsuario)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    callback(0)
                    return@addSnapshotListener
                }
                val totalPublicaciones = snapshots?.size() ?: 0
                callback(totalPublicaciones)
            }
    }

    private fun validarCampos(publicacion: PublicacionesModelo) {
        require(publicacion.titulo.isNotEmpty()) { "El título no puede estar vacío" }
        require(publicacion.descripcion.isNotEmpty()) { "La descripción no puede estar vacía" }
        require(publicacion.preparacion.isNotEmpty()) { "La preparación no puede estar vacía" }
        require(publicacion.ingredientes.isNotEmpty()) { "Los ingredientes no pueden estar vacíos" }
    }
}