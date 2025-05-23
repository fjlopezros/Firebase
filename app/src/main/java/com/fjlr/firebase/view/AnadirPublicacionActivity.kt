package com.fjlr.firebase.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import kotlinx.coroutines.launch

/**
 * Actividad para añadir una nueva publicación.
 */
class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private lateinit var viewModel: PublicacionesVistaModelo

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

        /**
         * Botón para guardar la publicación.
         * Recoge los datos introducidos por el usuario y los sube a la base de datos.
         */
        binding.btGuardarPublicacion.setOnClickListener {
            lifecycleScope.launch {
                viewModel.subirPublicacion(
                    PublicacionesModelo(
                        binding.etTituloAnadir.text.toString(),
                        binding.etDescripcionAnadir.text.toString(),
                        binding.etIngredientes.text.toString(),
                        binding.etPreparacion.text.toString()
                    )
                )
                finish()
            }
        }
    }
}