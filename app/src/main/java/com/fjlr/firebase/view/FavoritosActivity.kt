package com.fjlr.firebase.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fjlr.firebase.adapter.favoritos.PublicacionAdaptadorFav
import com.fjlr.firebase.databinding.ActivityFavoritosBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.FavoritosVistaModelo

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var viewModel: FavoritosVistaModelo
    private lateinit var publicacionAdaptadorFav: PublicacionAdaptadorFav


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuración de la barra de navegación
        configurarBarraNavegacion(this, binding.barraNavegacion)

        //Instancia del ViewModel
        viewModel = ViewModelProvider(this)[FavoritosVistaModelo::class.java]

        //Configuración del RecyclerView
        inicializarRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Cargar publicaciones
        viewModel.cargarFavoritos()

        //Observar cambios en la lista de publicaciones
        viewModel.publicaciones.observe(this) { lista ->
            publicacionAdaptadorFav.submitList(lista)
            binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun inicializarRecyclerView() {
        publicacionAdaptadorFav = PublicacionAdaptadorFav(viewModel)
        binding.recyclerViewFav.layoutManager = GridLayoutManager(this,2)
        binding.recyclerViewFav.adapter = publicacionAdaptadorFav
    }
}