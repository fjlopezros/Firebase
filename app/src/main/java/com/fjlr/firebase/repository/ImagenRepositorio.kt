package com.fjlr.firebase.repository

import android.net.Uri
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await


/**
 * Todo: AL SUBIR PUBLICACIONES PONER: TITULO+EMAIL
 */
/**
 * Repositorio para gestionar carga de imágenes, como foto de perfil.
 */
class ImagenRepositorio(
    private val storage: StorageReference = FirebaseStorage.getInstance().reference,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    /**
     * Carga una imagen de perfil a Storage y actualiza la URL en Firestore.
     *
     * @param imagen URI de la imagen a cargar.
     * @param email Email del usuario para identificar la imagen.
     * @return URL de la imagen cargada.
     */
    suspend fun agregarFoto(imagen: Uri, email: String, url: String): String {
        var espacio = storage.child("${ConstantesUtilidades.IMAGENES}/$url/$email${ConstantesUtilidades.JPG}")

        espacio.putFile(imagen).await()

        val urlFoto = espacio.downloadUrl.await().toString()

        if(url == ConstantesUtilidades.IMAGEN_PERFIL) {
            subirFotoDePerfil(urlFoto, email)
        }

        return urlFoto
    }

    /**
     * Actualiza la URL de la imagen de perfil en Firestore.
     *
     * @param urlImagen URL de la imagen a guardar.
     * @param email Email del usuario para identificar el documento.
     */
    private suspend fun subirFotoDePerfil(urlImagen: String, email: String) {
        val img = mapOf(
            ConstantesUtilidades.IMAGEN_PERFIL to urlImagen
        )

        firestore.collection(ConstantesUtilidades.COLECCION_USUARIOS)
            .document(email)
            .set(img, SetOptions.merge())
            .await()
    }
}