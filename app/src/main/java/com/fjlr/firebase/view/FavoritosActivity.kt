package com.fjlr.firebase.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fjlr.firebase.adapter.favoritos.PublicacionAdaptadorFav
import com.fjlr.firebase.databinding.ActivityFavoritosBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import kotlinx.coroutines.launch

/**
 * Actividad para mostrar las publicaciones favoritas del usuario.
 */
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

        /**
         * Carga las publicaciones favoritas del usuario.
         */
        viewModel.cargarFavoritos()

        /**
         * Observador de cambios en la lista de publicaciones.
         */
        viewModel.publicaciones.observe(this) { lista ->
            publicacionAdaptadorFav.submitList(lista)
            binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    /**
     * Inicializa el RecyclerView.
     */
    private fun inicializarRecyclerView() {
        lifecycleScope.launch {
            publicacionAdaptadorFav = PublicacionAdaptadorFav(viewModel, this@FavoritosActivity)
            binding.recyclerViewFav.layoutManager = GridLayoutManager(this@FavoritosActivity, 2)
            binding.recyclerViewFav.adapter = publicacionAdaptadorFav
        }
    }
}