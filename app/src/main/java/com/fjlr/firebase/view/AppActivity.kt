package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.app.PublicacionAdaptador
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import com.fjlr.firebase.viewModel.SeguidosVistaModelo
import kotlinx.coroutines.launch

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

        /**
         * Botón para ir a la actividad AnadirPublicacion.
         */
        binding.btAnadirPublicacion.setOnClickListener {
            startActivity(Intent(this, AnadirPublicacionActivity::class.java))
        }

        /**
         * Carga las publicaciones de los seguidos del usuario.
         */
        viewModelSeguidos.cargarPublicacionesSeguidos()

        /**
         * Observador de cambios en la lista de publicaciones.
         */
        viewModelSeguidos.publicaciones.observe(this) { lista ->
            publicacionAdapter.submitList(lista)

            binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }

    }

    /**
     * Inicializa el RecyclerView.
     */
    private fun inicializarRecyclerView() {
        lifecycleScope.launch {
            publicacionAdapter = PublicacionAdaptador(viewModelFav, this@AppActivity)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@AppActivity)
            binding.recyclerView.adapter = publicacionAdapter
        }
    }
}