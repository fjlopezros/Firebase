package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.PublicacionesRepositorio

/**
 * Vista modelo para la pantalla de publicaciones.
 */
class PublicacionesVistaModelo : ViewModel() {
    private val repositorio = PublicacionesRepositorio()

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
    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        return repositorio.subirPublicacion(publicacion)
    }
}