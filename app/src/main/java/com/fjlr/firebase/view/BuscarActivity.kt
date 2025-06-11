package com.fjlr.firebase.view

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.app.PublicacionAdaptador
import com.fjlr.firebase.databinding.ActivityBuscarBinding
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.BuscarVistaModelo
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import kotlinx.coroutines.launch

/**
 * Actividad de búsqueda de recetas.
 */
class BuscarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuscarBinding
    private lateinit var viewModel: PublicacionesVistaModelo
    private lateinit var viewModelFav: FavoritosVistaModelo
    private lateinit var viewModelBuscar: BuscarVistaModelo
    private lateinit var publicacionAdapter: PublicacionAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuración de la barra de navegación.
        configurarBarraNavegacion(this, binding.barraNavegacion)

        //Instancia del ViewModel
        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]
        viewModelFav = ViewModelProvider(this)[FavoritosVistaModelo::class.java]
        viewModelBuscar = ViewModelProvider(this)[BuscarVistaModelo::class.java]

        //Configuración del RecyclerView
        inicializarRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Carga todas las publicaciones.
         */
        viewModel.cargarPublicaciones()

        /**
         * Observa las publicaciones y actualiza el adaptador.
         */
        viewModel.publicaciones.observe(this) { lista ->
            publicacionAdapter.submitList(lista)
        }

        /**
         * Observa las publicaciones BUSCADAS y actualiza el adaptador.
         */
        viewModelBuscar.publicaciones.observe(this@BuscarActivity) { lista ->
            publicacionAdapter.submitList(lista)
        }

        /**
         * Configura el listener de búsqueda.
         *
         */
        binding.svBuscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /**
             * Cuando se envía la consulta busca las recetas y actualiza el adaptador.
             */
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModelBuscar.buscarRecetas(query ?: ConstantesUtilidades.NULL)
                return true
            }

            /**
             * No hace nada al cambiar el texto.
             */
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    /**
     * Inicializa el RecyclerView y el adaptador.
     */
    private fun inicializarRecyclerView() {
        lifecycleScope.launch {
            publicacionAdapter = PublicacionAdaptador(viewModelFav, this@BuscarActivity)
            binding.recyclerViewBuscar.layoutManager = LinearLayoutManager(this@BuscarActivity)
            binding.recyclerViewBuscar.adapter = publicacionAdapter
        }
    }
}