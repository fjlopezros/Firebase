package com.fjlr.firebase.repository.publicaciones

import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SeguidosRepositorio {
    private val db = FirebaseFirestore.getInstance()
    private val emailUsuario = FirebaseAuth.getInstance().currentUser?.email ?: ConstantesUtilidades.NULL

    fun cargarPublicacionesSeguidos(
        callback: (List<PublicacionesModelo>) -> Unit
    ) {
        //me guardo la coleccion "seguidos"
        val seguidoresRef = db.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(emailUsuario.toString())
            .collection(ConstantesUtilidades.COLECCION_SEGUIDOS)

        //me guardo el documento "email" no nulo
        seguidoresRef.get()
            .addOnSuccessListener { documentos ->
                val emailsSeguidos = documentos.mapNotNull { it.getString("email") }

                if (emailsSeguidos.isEmpty()) {
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                //me guardo la coleccion "publicaciones"
                db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
                    .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
                    .addSnapshotListener { snapshots, e ->
                        if (e != null || snapshots == null) {
                            callback(emptyList())
                            return@addSnapshotListener
                        }


                        val publicacionesFiltradas = snapshots.documents.mapNotNull { doc ->
                            val publicacion = doc.toObject(PublicacionesModelo::class.java)
                            val autor = publicacion?.autor
                            if (autor != null && autor in emailsSeguidos) {
                                publicacion
                            } else {
                                null
                            }
                        }
                        callback(publicacionesFiltradas)
                    }
            }
            .addOnFailureListener { e ->
                callback(emptyList())
            }
    }
}