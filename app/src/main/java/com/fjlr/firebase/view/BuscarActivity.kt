package com.fjlr.firebase.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.app.PublicacionAdaptador
import com.fjlr.firebase.databinding.ActivityBuscarBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class BuscarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarBinding
    private lateinit var viewModel: PublicacionesVistaModelo
    private lateinit var publicacionAdapter: PublicacionAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBarraNavegacion(this, binding.barraNavegacion)

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
        binding.recyclerViewBuscar.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBuscar.adapter = publicacionAdapter
    }
}