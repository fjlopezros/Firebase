package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
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
    private lateinit var emailDelPerfil: String


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

        emailDelPerfil = intent.getStringExtra("emailDelPerfil") ?: email ?: ""


        binding.btCerrarSesion.setOnClickListener {
            viewModel.cerrarSesion()
            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.obtenerNombreUsuario { nombre ->
            binding.tvCorreoUsuario.text = nombre ?: "Nombre no disponible"
        }

        viewModelMy.cargarTusPublicaciones(emailDelPerfil.toString())
        viewModelMy.publicaciones.observe(this) { lista ->
            listaTusPublicaciones.clear()
            listaTusPublicaciones.addAll(lista)
            adapter.notifyDataSetChanged()
        }

        viewModelMy.obtenerNombreDePerfil(emailDelPerfil) { nombre ->
            binding.tvCorreoUsuario.text = nombre ?: "Error al cargar perfil"
        }

        viewModel.contarPublicaciones(emailDelPerfil) { publicaciones ->
            binding.tvNumPublicaciones.text = publicaciones.toString()
        }

        viewModelSeguidores.contarSeguidores(emailDelPerfil) { seguidores ->
            binding.tvNumSeguidores.text = seguidores.toString()
        }

        viewModelSeguidores.contarSeguidos(emailDelPerfil) { seguidos ->
            binding.tvNumSeguidos.text = seguidos.toString()
        }

        viewModelSeguidores.ocultarSeguir(binding.btSeguir, emailDelPerfil.toString())

        binding.btSeguir.setOnClickListener {
            viewModelSeguidores.seguirUsuario(email.toString(), emailDelPerfil)
            binding.btSeguir.text = getString(R.string.dejar_de_seguir)
        }

        actualizarEstadoBotonSeguir()

    }

    fun actualizarEstadoBotonSeguir() {
        viewModelSeguidores.verificarSiSigue(email.toString(), emailDelPerfil) { loSigue ->
            binding.btSeguir.text =
                if (loSigue) getString(R.string.dejar_de_seguir) else getString(R.string.seguir)

            binding.btSeguir.setOnClickListener {
                if (loSigue) {
                    viewModelSeguidores.dejarDeSeguir(email.toString(), emailDelPerfil)
                } else {
                    viewModelSeguidores.seguirUsuario(email.toString(), emailDelPerfil)
                }

                actualizarEstadoBotonSeguir()
            }
        }
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesAdaptador(listaTusPublicaciones, viewModelMy)
        binding.recyclerViewMy.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMy.adapter = adapter
    }
}