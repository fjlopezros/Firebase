package com.fjlr.firebase.view

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
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Carga
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

/**
 * Actividad para añadir una nueva publicación.
 */
class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private lateinit var viewModel: PublicacionesVistaModelo

    private val email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private var urlFotoPublicacion: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuracion del binding
        binding = ActivityAnadirPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia del viewModel
        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Botón para salir de la actividad.
         */
        binding.ibFlechaParaSalir.setOnClickListener { finish() }


        // launcher para seleccionar una imagen de la galería
        val launcher = crearImagenLauncher()
        /**
         * Botón para añadir una imagen a la publicación.
         * Abre un selector de imágenes para elegir una nueva imagen.
         */
        binding.btSubirImagen.setOnClickListener {
            try {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            } catch (_: Exception) {
                Toast.makeText(
                    this@AnadirPublicacionActivity,
                    "Error al abrir selector",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        /**
         * Botón para guardar la publicación.
         * Recoge los datos introducidos por el usuario y los sube a la base de datos.
         */
        binding.btGuardarPublicacion.setOnClickListener {
            lifecycleScope.launch {
                try {
                    viewModel.subirPublicacion(
                        PublicacionesModelo(
                            binding.etTituloAnadir.text.toString(),
                            binding.etDescripcionAnadir.text.toString(),
                            binding.etIngredientes.text.toString(),
                            binding.etPreparacion.text.toString(),
                            fotoPublicacion = urlFotoPublicacion
                        )
                    )
                    finish()
                } catch (_: IllegalArgumentException) {
                    Toast.makeText(
                        this@AnadirPublicacionActivity,
                        "Rellena los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    /**
     * Crea un launcher para seleccionar una imagen de la galería.
     * Al seleccionar una imagen, se llama al metodo agregarFotoPublicacion del ViewModel.
     *
     * @return ActivityResultLauncher para manejar la selección de imágenes.
     */
    private fun crearImagenLauncher(): ActivityResultLauncher<PickVisualMediaRequest> {
        return registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let {
                Picasso.get()
                    .load(it)
                    .fit()
                    .centerCrop()
                    .into(binding.ivImagenSeleccionada)

                lifecycleScope.launch {
                    Carga.cargando(binding.pbCargandoPubli, binding.btGuardarPublicacion, true)

                    try {
                        urlFotoPublicacion = viewModel.agregarFotoPublicacion(it, email)

                        Toast.makeText(
                            this@AnadirPublicacionActivity,
                            "Foto subida correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (_: Exception) {
                        Toast.makeText(
                            this@AnadirPublicacionActivity,
                            "Error al subir la imagen",
                            Toast.LENGTH_LONG
                        ).show()
                    } finally {
                        Carga.cargando(binding.pbCargandoPubli, binding.btGuardarPublicacion, false)
                    }
                }
            } ?: Toast.makeText(
                this@AnadirPublicacionActivity,
                "No se ha seleccionado ninguna imagen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}