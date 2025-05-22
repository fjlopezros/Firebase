package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.app.PublicacionAdaptador
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import com.fjlr.firebase.viewModel.SeguidosVistaModelo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var viewModelFav: FavoritosVistaModelo
    private lateinit var publicacionAdapter: PublicacionAdaptador
    private lateinit var viewModelSeguidos: SeguidosVistaModelo


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
        viewModelFav = ViewModelProvider(this)[FavoritosVistaModelo::class.java]
        viewModelSeguidos = ViewModelProvider(this)[SeguidosVistaModelo::class.java]

        //Configuración del RecyclerView
        inicializarRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Cargar publicaciones
        viewModelSeguidos.cargarPublicacionesSeguidos()

        //Observar cambios en la lista de publicaciones
        viewModelSeguidos.publicaciones.observe(this) { lista ->
            publicacionAdapter.submitList(lista)
        }

    }

    private fun inicializarRecyclerView() {
        publicacionAdapter = PublicacionAdaptador(viewModelFav)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = publicacionAdapter
    }
}