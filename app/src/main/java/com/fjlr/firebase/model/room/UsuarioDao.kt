package com.fjlr.firebase.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE email = :email")
    suspend fun obtenerUsuario(email: String): UsuarioEntidad?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntidad)
}
