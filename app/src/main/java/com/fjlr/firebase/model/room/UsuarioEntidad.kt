package com.fjlr.firebase.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntidad (
    @PrimaryKey val email: String,
    val nombre: String
)