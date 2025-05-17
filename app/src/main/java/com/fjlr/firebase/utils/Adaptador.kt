package com.fjlr.firebase.utils

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.view.AjustesActivity
import com.fjlr.firebase.view.DetalleActivity
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

object Adaptador {
    fun configurarIconoFavorito(
        publicacion: PublicacionesModelo,
        holderIcono: ImageButton,
        viewModel: PublicacionesVistaModelo
    ) {
        viewModel.esFavorito(publicacion) { favorito ->
            publicacion.esFavorito = favorito
            ActualizarIcono.actualizarIcono(favorito, holderIcono)
        }

        holderIcono.setOnClickListener {
            publicacion.esFavorito = !publicacion.esFavorito
            ActualizarIcono.actualizarIcono(publicacion.esFavorito, holderIcono)
            if (publicacion.esFavorito) {
                viewModel.guardarFavorito(publicacion)
            } else {
                viewModel.eliminarFavorito(publicacion)
            }
        }
    }

    fun abrirAjustesDesdeItem(view: View, email: String) {
        val intent = Intent(view.context, AjustesActivity::class.java)
        intent.putExtra("emailDelPerfil", email)
        view.context.startActivity(intent)
    }
    fun abrirDetalleDesdeItemAjustes(view: View, publicacion: PublicacionesModelo) {
        val intent = Intent(view.context, DetalleActivity::class.java)
        intent.putExtra("publicacionDelPerfil", publicacion)
        view.context.startActivity(intent)
    }
}