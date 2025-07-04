package com.fjlr.firebase.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.ActivityAjustesBinding
import com.fjlr.firebase.utils.Carga
import com.fjlr.firebase.viewModel.PerfilVistaModelo
import com.fjlr.firebase.viewModel.RegistroVistaModelo
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlin.toString

/**
 * Actividad para la pantalla de ajustes del usuario.
 */
class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding
    private lateinit var viewModel: RegistroVistaModelo
    private lateinit var viewModelUtilidades: UtilidadesPerfilVistaModelo
    private lateinit var viewModelPerfil: PerfilVistaModelo
    private val email = FirebaseAuth.getInstance().currentUser?.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuracion del binding
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia del ViewModel
        viewModel = ViewModelProvider(this)[RegistroVistaModelo::class.java]
        viewModelUtilidades = ViewModelProvider(this)[UtilidadesPerfilVistaModelo::class.java]
        viewModelPerfil = ViewModelProvider(this)[PerfilVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Botón para salir de la pantalla.
         */
        binding.ibFlechaParaSalir.setOnClickListener {
            finish()
        }

        /**
         * Botón para cambiar el nombre de usuario.
         */
        binding.btCambiarUsuario.setOnClickListener {
            val nuevoUsuario = binding.etCambiarUsuario.text.toString()

            if (nuevoUsuario.isNotBlank()) {
                viewModel.cambiarNombreUsuario(
                    email, nuevoUsuario
                )

                Toast.makeText(
                    this@AjustesActivity,
                    R.string.nombre_cambiado,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        /**
         * Botón que te envía un email para cambiar la contraseña.
         * Cierra sesion y te lleva a la pantalla de inicio de sesión.
         */
        binding.btContrasena.setOnClickListener {
            viewModel.cambiarContrasena(email)

            cerrarSesion()

            Toast.makeText(
                this@AjustesActivity,
                R.string.email_enviado,
                Toast.LENGTH_SHORT
            ).show()
        }

        /**
         * Botón para cerrar sesión.
         * Muestra un diálogo de confirmación antes de cerrar sesión.
         */
        binding.ibSalir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.cerrar_sesion)
                .setMessage(R.string.pregunta_salir)
                .setPositiveButton(R.string.si) { _, _ ->
                    cerrarSesion()
                }
                .setNegativeButton(R.string.no, null)
                .show()
        }

        // launcher para seleccionar una imagen de la galería
        val launcher = crearImagenLauncher()
        /**
         * Botón para cambiar la foto de perfil.
         * Abre un selector de imágenes para elegir una nueva foto de perfil.
         */
        binding.btFotoperfil.setOnClickListener {
            try {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } catch (_: Exception) {
                Toast.makeText(
                    this@AjustesActivity,
                    R.string.error_seleccionar_imagen,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Cierra la sesión del usuario actual y redirige a la pantalla de inicio de sesión.
     */
    private fun cerrarSesion() {
        viewModelUtilidades.cerrarSesion()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    /**
     * Crea un launcher para seleccionar una imagen de la galería.
     * Al seleccionar una imagen, se llama al metodo cambiarFotoDePerfil del ViewModel.
     * Muestra una carga mientras se sube la imagen y muestra un mensaje de éxito o error.
     *
     * @return ActivityResultLauncher para manejar la selección de imágenes.
     */
    private fun crearImagenLauncher(): ActivityResultLauncher<PickVisualMediaRequest> {
        return registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let {
                lifecycleScope.launch {
                    Carga.cargando(binding.pbCargando, binding.ibFlechaParaSalir, true)

                    try {
                        viewModelUtilidades.agregarFotoPerfil(it, email)

                        Toast.makeText(
                            this@AjustesActivity,
                            R.string.foto_actualizada,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (_: Exception) {
                        Toast.makeText(
                            this@AjustesActivity,
                            R.string.foto_error,
                            Toast.LENGTH_LONG
                        ).show()
                    } finally {
                        Carga.cargando(binding.pbCargando, binding.ibFlechaParaSalir, false)
                    }
                }
            } ?: Toast.makeText(
                this@AjustesActivity,
                R.string.imagen_no_seleccionada,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}