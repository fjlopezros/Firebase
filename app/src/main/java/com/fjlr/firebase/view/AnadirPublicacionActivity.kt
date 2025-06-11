package com.fjlr.firebase.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Carga
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlin.toString

/**
 * Actividad para añadir una nueva publicación.
 */
class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private lateinit var viewModel: PublicacionesVistaModelo

    private val email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private var uriImagenSeleccionada: Uri = Uri.EMPTY

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
                    R.string.error_seleccionar_imagen,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        /**
         * Botón para guardar la publicación.
         * Recoge los datos introducidos por el usuario y los envía al ViewModel para su procesamiento.
         *
         * Valida que todos los campos estén rellenos antes de proceder.
         * Si algún campo está vacío, muestra un mensaje de error.
         * Inicia un proceso de carga mientras se guarda la publicación.
         * Si ocurre un error al guardar, muestra un mensaje de error.
         *
         */
        binding.btGuardarPublicacion.setOnClickListener {
            val titulo = binding.etTituloAnadir.text.toString().trim()
            val descripcion = binding.etDescripcionAnadir.text.toString().trim()
            val ingredientes = binding.etIngredientes.text.toString().trim()
            val preparacion = binding.etPreparacion.text.toString().trim()

            if (titulo.isEmpty() || descripcion.isEmpty() || ingredientes.isEmpty() || preparacion.isEmpty()) {
                Toast.makeText(this, R.string.rellenar_campos, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                Carga.cargando(binding.pbCargandoPubli, binding.btGuardarPublicacion, true)

                try {
                    val urlFinalFoto = uriImagenSeleccionada.let {
                        viewModel.agregarFotoPublicacion(it, email, titulo)
                    }

                    viewModel.subirPublicacion(
                        PublicacionesModelo(
                            titulo,
                            descripcion,
                            ingredientes,
                            preparacion,
                            fotoPublicacion = urlFinalFoto
                        )
                    )

                    finish()
                } catch (_: Exception) {
                    Toast.makeText(
                        this@AnadirPublicacionActivity,
                        R.string.error_guardar_publicacion,
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    Carga.cargando(binding.pbCargandoPubli, binding.btGuardarPublicacion, false)
                }
            }
        }


    }

    /**
     * Crea un launcher para seleccionar una imagen de la galería.
     * Utiliza Picasso para cargar la imagen seleccionada en un ImageView.
     * Muestra un mensaje si no se selecciona ninguna imagen.
     *
     * @return ActivityResultLauncher configurado para PickVisualMediaRequest.
     */
    private fun crearImagenLauncher(): ActivityResultLauncher<PickVisualMediaRequest> {
        return registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let {
                uriImagenSeleccionada = it

                Picasso.get()
                    .load(it)
                    .fit()
                    .centerCrop()
                    .into(binding.ivImagenSeleccionada)
            } ?: Toast.makeText(
                this@AnadirPublicacionActivity,
                R.string.imagen_no_seleccionada,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}