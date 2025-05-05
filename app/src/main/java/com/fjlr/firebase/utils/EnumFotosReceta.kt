package com.fjlr.firebase.utils

enum class EnumFotosReceta(
    val nombre: String,
    val fotoUrl: String
) {
    ANSELMO("123456","https://robohash.org/anselmo");

    companion object {
        fun getRecetaByNombre(nombre: String): EnumFotosReceta? =
            EnumFotosReceta.entries.find { it.nombre == nombre }
    }
}
