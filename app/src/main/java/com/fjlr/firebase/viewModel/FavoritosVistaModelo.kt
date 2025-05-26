package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.FavoritosRepositorio

/**
 * Vista modelo para la pantalla de favoritos.
 */
class FavoritosVistaModelo : ViewModel() {

    private val repositorio = FavoritosRepositorio()
    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    /**
     * Carga las publicaciones favoritas del usuario.
     */
    fun cargarFavoritos() {
        repositorio.cargarFavoritos { lista ->
            _publicaciones.postValue(lista)
        }
    }

    /**
     * Verifica si una publicación es favorita.
     */
    suspend fun esFavorito(publicacion: PublicacionesModelo): Boolean {
        return repositorio.esFavorito(publicacion)
    }

    /**
     * Agrega una publicación a favoritos.
     */
    suspend fun agregarAFavoritos(publicacion: PublicacionesModelo) {
        repositorio.guardarFavorito(publicacion)
    }

    /**
     * Elimina una publicación de favoritos.
     */
    suspend fun eliminarDeFavoritos(publicacion: PublicacionesModelo) {
        repositorio.eliminarFavorito(publicacion)
    }
}