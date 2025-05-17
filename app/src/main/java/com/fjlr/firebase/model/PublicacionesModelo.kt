package com.fjlr.firebase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PublicacionesModelo(
    val titulo: String = "",
    val descripcion: String = "",
    val ingredientes: String = "",
    val preparacion: String = "",
    val autor: String = "",
    var esFavorito: Boolean = false,
    var imagen: String =""
) : Parcelable