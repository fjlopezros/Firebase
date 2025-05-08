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
import com.fjlr.firebase.R
import com.fjlr.firebase.adapter.PublicacionesAdaptador
import com.fjlr.firebase.databinding.ActivityAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.AjustesVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.fjlr.firebase.viewModel.SeguidoresVistaModelo
import com.google.firebase.auth.FirebaseAuth

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding
    private lateinit var viewModel: AjustesVistaModelo
    private lateinit var viewModelSeguidores: SeguidoresVistaModelo
    private lateinit var adapter: PublicacionesAdaptador
    private var listaTusPublicaciones = mutableListOf<PublicacionesModelo>()
    private lateinit var viewModelMy: PublicacionesVistaModelo
    private val email = FirebaseAuth.getInstance().currentUser?.email

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
        viewModelSeguidores = ViewModelProvider(this)[SeguidoresVistaModelo::class.java]

        configurarBarraNavegacion(this, binding.barraNavegacion)
        inicializarRecyclerView()

        binding.btCerrarSesion.setOnClickListener {
            viewModel.cerrarSesion()
            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.obtenerNombreUsuario { nombre ->
            binding.tvCorreoUsuario.text = nombre ?: "Nombre no disponible"
        }

        viewModel.contarPublicaciones { publicaciones ->
            binding.tvNumPublicaciones.text = publicaciones.toString()
        }

        viewModelSeguidores.contarSeguidores(email.toString()) { seguidores ->
            binding.tvNumSeguidores.text = seguidores.toString()
        }

        viewModelSeguidores.contarSeguidos(email.toString()) { seguidos ->
            binding.tvNumSeguidos.text = seguidos.toString()
        }
        viewModelMy.cargarTusPublicaciones()
        viewModelMy.publicaciones.observe(this) { lista ->
            listaTusPublicaciones.clear()
            listaTusPublicaciones.addAll(lista)
            adapter.notifyDataSetChanged()
        }

        viewModelSeguidores.ocultarSeguir(binding.btSeguir, email.toString())

        binding.btSeguir.setOnClickListener {
            viewModelSeguidores.seguirUsuario(email.toString())
            binding.btSeguir.text = getString(R.string.dejar_de_seguir)
        }

        if(binding.btSeguir.text.equals(getString(R.string.dejar_de_seguir))){
            binding.btSeguir.setOnClickListener {
                viewModelSeguidores.dejarDeSeguir(email.toString())
                binding.btSeguir.text = getString(R.string.seguir)
            }
        }
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesAdaptador(listaTusPublicaciones, viewModelMy) { publicacion ->
            val intent = Intent(this, AjustesActivity::class.java)
            intent.putExtra("emailDelPerfil", publicacion.autor)
            startActivity(intent)
        }
        binding.recyclerViewMy.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMy.adapter = adapter
    }
}