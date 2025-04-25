package com.fjlr.firebase

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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private lateinit var db: FirebaseFirestore

    private lateinit var viewModel: PublicacionesVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = FirebaseFirestore.getInstance()

        binding = ActivityAnadirPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        binding.ibFlechaParaSalir.setOnClickListener { finish() }

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