package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio

class PerfilVistaModelo: ViewModel() {
    private val repositorio = PerfilRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    fun cargarTusPublicaciones(emailDelPerfil: String) {
        repositorio.cargarTusPublicaciones(emailDelPerfil) { lista ->
            _publicaciones.postValue(lista)
        }
    }
}