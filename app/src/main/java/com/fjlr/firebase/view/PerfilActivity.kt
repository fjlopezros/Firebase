package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fjlr.firebase.R
import com.fjlr.firebase.adapter.ajustes.PublicacionAdaptadorAjustes
import com.fjlr.firebase.databinding.ActivityPerfilBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import com.fjlr.firebase.viewModel.PerfilVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.fjlr.firebase.viewModel.SeguidoresVistaModelo
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var viewModel: UtilidadesPerfilVistaModelo
    private lateinit var viewModelFav: FavoritosVistaModelo
    private lateinit var viewModelSeguidores: SeguidoresVistaModelo
    private lateinit var viewModelMy: PublicacionesVistaModelo
    private lateinit var publicacionesAdapter: PublicacionAdaptadorAjustes
    private val email = FirebaseAuth.getInstance().currentUser?.email
    private lateinit var emailDelPerfil: String


    private lateinit var viewModelPerfil: PerfilVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuracion del binding
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuración de la barra de navegación
        configurarBarraNavegacion(this, binding.barraNavegacion)

        //Configuración del botón de cerrar sesión
        binding.ibAjustes.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java))
        }

        //Instancia del ViewModel
        viewModel = ViewModelProvider(this)[UtilidadesPerfilVistaModelo::class.java]
        viewModelMy = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]
        viewModelSeguidores = ViewModelProvider(this)[SeguidoresVistaModelo::class.java]
        viewModelFav = ViewModelProvider(this)[FavoritosVistaModelo::class.java]


        viewModelPerfil = ViewModelProvider(this)[PerfilVistaModelo::class.java]

        //Configuración del RecyclerView
        inicializarRecyclerView()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Recogo el email del perfil que se pasa por intent
        emailDelPerfil = intent.getStringExtra("emailDelPerfil") ?: email ?: ""

        fotoDePerfilAleatoria()

        //Obetengo el nomrbre de usuario
        viewModel.obtenerNombreDeEmail(emailDelPerfil) { nombre ->
            binding.tvCorreoUsuario.text = nombre ?: "Nombre no disponible"
        }

        //Cargar las publicaciones de mi perfil
        viewModelPerfil.cargarTusPublicaciones(emailDelPerfil.toString())

        //Observar cambios en la lista de publicaciones
        viewModelPerfil.publicaciones.observe(this) { lista ->
            publicacionesAdapter.submitList(lista)
        }

        //Seguir o dejar de seguir al usuario
        binding.btSeguir.setOnClickListener {
            viewModelSeguidores.seguirUsuario(email.toString(), emailDelPerfil)
            binding.btSeguir.text = getString(R.string.dejar_de_seguir)
        }

        inicializarMenu()

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

    private fun inicializarMenu() {
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
    }

    private fun inicializarRecyclerView() {
        publicacionesAdapter = PublicacionAdaptadorAjustes(viewModelFav)
        binding.recyclerViewMy.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewMy.adapter = publicacionesAdapter
    }

    private fun fotoDePerfilAleatoria() {
        viewModel.cargarFotoDePerfilAleatoria(binding.ivPerfilUsuario)
    }
}