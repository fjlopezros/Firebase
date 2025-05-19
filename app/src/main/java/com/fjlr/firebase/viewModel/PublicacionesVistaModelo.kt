package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio
import com.fjlr.firebase.repository.publicaciones.PublicacionesRepositorio
import com.fjlr.firebase.repository.publicaciones.SeguidosRepositorio

class PublicacionesVistaModelo : ViewModel() {
    private val repositorio = PublicacionesRepositorio()
    private val repositorioSeguidos = SeguidosRepositorio()
    private val perfilRepositorio = PerfilRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    fun cargarPublicaciones() {
        repositorio.cargarPublicaciones { lista ->
            _publicaciones.postValue(lista)
        }
    }

    fun cargarPublicacionesSeguidos(){
        repositorioSeguidos.cargarPublicacionesSeguidos { lista ->
            _publicaciones.postValue(lista)
        }
    }

    suspend fun subirPublicacion(publicacion: PublicacionesModelo) {
        return perfilRepositorio.subirPublicacion(publicacion)
    }

    fun cargarTusPublicaciones(emailDelPerfil: String) {
        perfilRepositorio.cargarTusPublicaciones(emailDelPerfil) { lista ->
            _publicaciones.postValue(lista)
        }
    }
}