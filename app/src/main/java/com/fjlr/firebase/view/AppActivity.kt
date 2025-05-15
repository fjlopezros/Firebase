package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.app.PublicacionAdaptador
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var viewModel: PublicacionesVistaModelo
    private lateinit var publicacionAdapter: PublicacionAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuración de la barra de navegación
        configurarBarraNavegacion(this, binding.barraNavegacion)

        //Configuración del botón de añadir publicación
        binding.btAnadirPublicacion.setOnClickListener {
            startActivity(Intent(this, AnadirPublicacionActivity::class.java))
        }

        //Instancia del ViewModel
        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        //Configuración del RecyclerView
        inicializarRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Cargar publicaciones
        viewModel.cargarPublicaciones()

        //Observar cambios en la lista de publicaciones
        viewModel.publicaciones.observe(this) { lista ->
            publicacionAdapter.submitList(lista)
        }

    }

    private fun inicializarRecyclerView() {
        publicacionAdapter = PublicacionAdaptador(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = publicacionAdapter
    }
}