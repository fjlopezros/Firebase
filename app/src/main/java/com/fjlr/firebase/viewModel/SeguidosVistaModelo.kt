package com.fjlr.firebase.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.repository.publicaciones.SeguidosRepositorio

class SeguidosVistaModelo: ViewModel() {
    private val repositorio = SeguidosRepositorio()

    private var _publicaciones = MutableLiveData<List<PublicacionesModelo>>()
    val publicaciones: LiveData<List<PublicacionesModelo>> get() = _publicaciones

    fun cargarPublicacionesSeguidos(){
        repositorio.cargarPublicacionesSeguidos { lista ->
            _publicaciones.postValue(lista)
        }
    }

}