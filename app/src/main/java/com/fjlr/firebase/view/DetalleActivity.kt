package com.fjlr.firebase.view

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.fjlr.firebase.databinding.ActivityDetalleBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.AjustesVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.squareup.picasso.Picasso

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private lateinit var viewModel: PublicacionesVistaModelo
    private lateinit var viewModelAjustes: AjustesVistaModelo

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PublicacionesVistaModelo::class.java]
        viewModelAjustes = ViewModelProvider(this)[AjustesVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibFlechaParaSalir.setOnClickListener {
            finish()
        }

        pasarADetalle()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun pasarADetalle() {
        val publicacion =
            intent.getParcelableExtra("publicacionDelPerfil", PublicacionesModelo::class.java)

        viewModelAjustes.obtenerNombreDeEmail(publicacion?.autor.toString()) { nombre ->
            binding.tvUsuarioDetalla.text = nombre ?: "Null"
        }
        binding.tvTituloPublicacionDetalla.text = publicacion?.titulo
        binding.tvDescripcionPublicacionDetalla.text = publicacion?.descripcion
        binding.tvIngredientesPublicacionDetalla.text = publicacion?.ingredientes
        binding.tvPreparacionPublicacionDetalla.text = publicacion?.preparacion
        Picasso.get().load("https://robohash.org/fran").into(binding.ivFotoDetalla)
        Adaptador.configurarIconoFavorito(PublicacionesModelo(), binding.ibBotonFavoritoDetalla, viewModel)
    }
}