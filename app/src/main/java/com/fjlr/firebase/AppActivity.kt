package com.fjlr.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.PublicacionesAdaptador
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.google.firebase.firestore.FirebaseFirestore

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var db: FirebaseFirestore

    private val listaPublicaciones = mutableListOf<PublicacionesModelo>()
    private lateinit var adapter: PublicacionesAdaptador

    private lateinit var viewModel: PublicacionesVistaModelo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = FirebaseFirestore.getInstance()

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]

        configurarBarraNavegacion(this, binding.barraNavegacion)
        inicializarRecyclerView()

        binding.btAnadirPublicacion.setOnClickListener {
            startActivity(Intent(this, AnadirPublicacionActivity::class.java))
        }

        viewModel.cargarPublicaciones()
        viewModel.publicaciones.observe(this) { lista ->
            listaPublicaciones.clear()
            listaPublicaciones.addAll(lista)
            adapter.notifyDataSetChanged()
        }
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesAdaptador(listaPublicaciones)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}