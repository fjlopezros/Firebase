package com.fjlr.firebase.utils

import android.widget.ImageButton
import com.fjlr.firebase.R

object ActualizarIcono {
    fun actualizarIcono(esFavorito: Boolean, icono: ImageButton) {
        if (esFavorito) {
            icono.setImageResource(R.drawable.menos)
        } else {
            icono.setImageResource(R.drawable.favorito)
        }
    }
}