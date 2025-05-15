package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.PublicacionesRepositorio

class PublicacionesVistaModelo : ViewModel() {
    private val repositorio = PublicacionesRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    fun cargarPublicaciones() {
        repositorio.cargarPublicaciones { lista ->
            _publicaciones.postValue(lista)
        }
    }

    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        return repositorio.subirPublicacion(publicacion)
    }

    fun guardarFavorito(publicacion: PublicacionesModelo) {
        repositorio.guardarFavorito(publicacion)
    }

    fun eliminarFavorito(publicacion: PublicacionesModelo) {
        repositorio.eliminarFavorito(publicacion)
    }

    fun cargarFavoritos() {
        repositorio.cargarPublicacionesFavoritas { lista ->
            _publicaciones.postValue(lista)
        }
    }

    fun esFavorito(publicacion: PublicacionesModelo, callback: (Boolean) -> Unit) {
        repositorio.esFavorito(publicacion, callback)
    }

    fun cargarTusPublicaciones(emailDelPerfil: String) {
        repositorio.cargarTusPublicaciones(emailDelPerfil) { lista ->
            _publicaciones.postValue(lista)
        }
    }
}