package com.fjlr.firebase.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.ImagenRepositorio
import com.fjlr.firebase.repository.publicaciones.PublicacionesRepositorio
import com.fjlr.firebase.utils.ConstantesUtilidades
import kotlinx.coroutines.launch

/**
 * Vista modelo para la pantalla de publicaciones.
 */
class PublicacionesVistaModelo : ViewModel() {
    private val repositorio = PublicacionesRepositorio()
    private val imagenRepositorio = ImagenRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    /**
     * Carga todas las publicaciones.
     */
    fun cargarPublicaciones() {
        repositorio.cargarPublicaciones { lista ->
            _publicaciones.postValue(lista)
        }
    }

    /**
     * Subir una nueva publicación.
     * @param publicacion Publicación a subir.
     */
    fun subirPublicacion(publicacion: PublicacionesModelo) {
        viewModelScope.launch {
            repositorio.subirPublicacion(publicacion)
        }
    }

    /**
     * Agrega la foto (PUBLICACION) en Storage y Firestore.
     *
     * @param imagen Uri de la imagen a subir.
     * @param email Email del usuario.
     */
    suspend fun agregarFotoPublicacion(imagen: Uri, email: String, titulo: String): String {
        return imagenRepositorio.agregarFoto(
            imagen,
            email,
            titulo,
            ConstantesUtilidades.IMAGEN_PUBLICACION
        )
    }

    fun eliminarPublicacion(publicacion: PublicacionesModelo){
        viewModelScope.launch {
            repositorio.eliminarPublicacion(publicacion)
        }
    }
}