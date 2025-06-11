package com.fjlr.firebase.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjlr.firebase.repository.AutenticacionRepositorio
import com.fjlr.firebase.repository.UsuarioRepositorio
import kotlinx.coroutines.launch

/**
 * ViewModel encargado del proceso de registro de nuevos usuarios.
 */
class RegistroVistaModelo : ViewModel() {

    private val autenticacionRepositorio = AutenticacionRepositorio()
    private val usuarioRepositorio = UsuarioRepositorio()

    /**
     * Registra un nuevo usuario creando su cuenta y guardando su nombre en Firestore.
     *
     * @param email Correo electrónico del usuario.
     * @param contrasena Contraseña para la cuenta.
     * @param nombreUsuario Nombre de usuario visible.
     */
    suspend fun registrarse(
        email: String,
        contrasena: String,
        nombreUsuario: String,
    ): Result<Unit> {
        return autenticacionRepositorio.crearCuenta(email, contrasena).onSuccess {
            usuarioRepositorio.guardarNombreUsuario(
                email,
                nombreUsuario
            )
        }
    }

    /**
     * Cambia el nombre de usuario de un usuario.
     * @param email Email del usuario.
     * @param usuario Nuevo nombre de usuario.
     */
    fun cambiarNombreUsuario(email: String, usuario: String) {
        viewModelScope.launch {
            usuarioRepositorio.cambiarNombreUsuario(email, usuario)
        }
    }

    /**
     * Cambia la contraseña de un usuario.
     * @param email Email del usuario.
     */
    fun cambiarContrasena(email: String) {
        viewModelScope.launch {
            usuarioRepositorio.cambiarContrasena(email)
        }
    }
}