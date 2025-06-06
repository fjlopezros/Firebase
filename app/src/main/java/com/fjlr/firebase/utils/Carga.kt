package com.fjlr.firebase.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar

object Carga {
    /**
     * Muestra u oculta una barra de progreso.
     *
     * @param progressBar Barra de progreso a mostrar/ocultar
     * @param show true para mostrar, false para ocultar
     */
    fun cargando(progressBar: ProgressBar, componente: View, show: Boolean) {
        if (show) {
            progressBar.visibility = VISIBLE
            componente.isEnabled = false
        }else{
            progressBar.visibility = GONE
            componente.isEnabled = true
        }
    }
}