package com.fjlr.firebase.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.PublicacionesAdaptador
import com.fjlr.firebase.databinding.ActivityFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var adapter: PublicacionesAdaptador
    private var listaFavoritos = mutableListOf<PublicacionesModelo>()
    private lateinit var viewModel: PublicacionesVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        configurarBarraNavegacion(this, binding.barraNavegacion)
        inicializarRecyclerView()

        viewModel.cargarFavoritos()
        viewModel.publicaciones.observe(this) { lista ->
            listaFavoritos.clear()
            listaFavoritos.addAll(lista)
            adapter.notifyDataSetChanged()
        }
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesAdaptador(listaFavoritos)
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFav.adapter = adapter
    }
}