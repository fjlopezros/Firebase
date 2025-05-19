package com.fjlr.firebase.utils

import android.widget.ImageButton
import com.fjlr.firebase.R
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.viewModel.FavoritosVistaModelo

/**
 * Objeto de utilidad para actualizar el icono de favorito.
 */
object ActualizarIcono {
    /**
     * Actualiza el icono de favorito en función de si es favorito o no.
     * @param esFavorito Indica si la publicación es favorita o no.
     * @param icono El icono a actualizar.
     */
    fun actualizarIcono(esFavorito: Boolean, icono: ImageButton) {
        if (esFavorito) {
            icono.setImageResource(R.drawable.menos)
        } else {
            icono.setImageResource(R.drawable.favorito)
        }
    }

    /**
     * Configura el icono de favorito para una publicación.
     * @param publicacion La publicación a configurar.
     * @param holderIcono El holder del icono.
     * @param viewModel El ViewModel de publicaciones.
     */
    fun configurarIconoFavorito(
        publicacion: PublicacionesModelo,
        holderIcono: ImageButton,
        viewModel: FavoritosVistaModelo
    ) {
        viewModel.esFavorito(publicacion) { favorito ->
            publicacion.esFavorito = favorito
            actualizarIcono(favorito, holderIcono)
        }

        holderIcono.setOnClickListener {
            publicacion.esFavorito = !publicacion.esFavorito
            actualizarIcono(publicacion.esFavorito, holderIcono)
            if (publicacion.esFavorito) {
                viewModel.agregarAFavoritos(publicacion)
            } else {
                viewModel.eliminarDeFavoritos(publicacion)
            }
        }
    }
}