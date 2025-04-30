package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.PublicacionesAdaptador
import com.fjlr.firebase.databinding.ActivityAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.AjustesVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding
    private lateinit var viewModel: AjustesVistaModelo
    private lateinit var adapter: PublicacionesAdaptador
    private var listaTusPublicaciones = mutableListOf<PublicacionesModelo>()
    private lateinit var viewModelMy: PublicacionesVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[AjustesVistaModelo::class.java]
        viewModelMy = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        configurarBarraNavegacion(this, binding.barraNavegacion)
        inicializarRecyclerView()

        binding.btCerrarSesion.setOnClickListener {
            viewModel.cerrarSesion()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.tvCorreoUsuario.text = viewModel.obtenerUsuarioActual()

        viewModelMy.cargarTusPublicaciones()
        viewModelMy.publicaciones.observe(this) { lista ->
            listaTusPublicaciones.clear()
            listaTusPublicaciones.addAll(lista)
            adapter.notifyDataSetChanged()
        }
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesAdaptador(listaTusPublicaciones, viewModelMy)
        binding.recyclerViewMy.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMy.adapter = adapter
    }
}