package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.UsuarioRepositorio
import com.fjlr.firebase.repository.publicaciones.PerfilRepositorio

/**
 * Vista modelo para la pantalla de perfil.
 */
class PerfilVistaModelo : ViewModel() {
    private val repositorio = PerfilRepositorio()
    private val usuarioRepositorio = UsuarioRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    private val _fotoDePerfil = MutableLiveData<String>()
    val fotoDePerfil: LiveData<String> = _fotoDePerfil

    /**
     * Carga tus publicaciones.
     * @param emailDelPerfil Email del perfil del usuario.
     */
    fun cargarTusPublicaciones(emailDelPerfil: String) {
        repositorio.cargarTusPublicaciones(emailDelPerfil) { lista ->
            _publicaciones.postValue(lista)
        }
    }

    /**
     * Obtiene la url de la foto de perfil del usuario.
     * @param email Email del usuario.
     */
    fun obtenerUrlFotoPerfil(email: String) {
            usuarioRepositorio.obtenerUrlFotoPerfil(email) { url ->
                _fotoDePerfil.postValue(url)
            }
    }
}