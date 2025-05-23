package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio

/**
 * Vista modelo para la pantalla de perfil.
 */
class PerfilVistaModelo: ViewModel() {
    private val repositorio = PerfilRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    /**
     * Carga tus publicaciones.
     * @param emailDelPerfil Email del perfil del usuario.
     */
    fun cargarTusPublicaciones(emailDelPerfil: String) {
        repositorio.cargarTusPublicaciones(emailDelPerfil) { lista ->
            _publicaciones.postValue(lista)
        }
    }
}