package com.fjlr.firebase.model

data class PublicacionesModelo(
    val titulo: String = "",
    val descripcion: String = "",
    val ingredientes: String = "",
    val preparacion: String = "",
    var esFavorito: Boolean = false
)