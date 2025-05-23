package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.BuscarRepositorio

/**
 * Vista modelo para la pantalla de búsqueda de recetas.
 */
class BuscarVistaModelo : ViewModel() {
    private val repositorio = BuscarRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    /**
     * Realiza una búsqueda de recetas.
     * @param tituloReceta Título de la receta a buscar.
     */
    fun buscarRecetas(tituloReceta: String) {
        repositorio.buscarRecetas(tituloReceta) { lista ->
            _publicaciones.postValue(lista)
        }
    }

}