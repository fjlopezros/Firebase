package com.fjlr.firebase.utils

import android.content.Intent
import android.view.View
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.view.AjustesActivity
import com.fjlr.firebase.view.DetalleActivity

/**
 * Objeto de utilidad para abrir actividades desde el adaptador.
 */
object Adaptador {
    /**
     * Abre la actividad de ajustes desde el adaptador.
     * Le pasa el email del autor de la publicación para luego mostrar sus publicaciones.
     * @param view La vista actual.
     * @param email El email del autor de la publicación.
     */
    fun abrirAjustesDesdeItem(view: View, email: String) {
        val intent = Intent(view.context, AjustesActivity::class.java)
        intent.putExtra("emailDelPerfil", email)
        view.context.startActivity(intent)
    }

    /**
     * Abre la actividad de detalle desde el adaptador.
     * Le pasa la publicacion seleccionada para luego mostrar sus detalles.
     * @param view La vista actual.
     * @param publicacion La publicacion seleccionada.
     */
    fun abrirDetalleDesdeItemAjustes(view: View, publicacion: PublicacionesModelo) {
        val intent = Intent(view.context, DetalleActivity::class.java)
        intent.putExtra("publicacionDelPerfil", publicacion)
        view.context.startActivity(intent)
    }
}