package com.fjlr.firebase.view

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fjlr.firebase.databinding.ActivityDetalleBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ActualizarIcono
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

/**
 * Actividad para mostrar los detalles de una publicación.
 */
class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private lateinit var viewModelAjustes: UtilidadesPerfilVistaModelo
    private lateinit var viewModelFav: FavoritosVistaModelo

    //Requiere la versión Tiramisu (33 Android)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia del viewModel
        viewModelAjustes = ViewModelProvider(this)[UtilidadesPerfilVistaModelo::class.java]
        viewModelFav = ViewModelProvider(this)[FavoritosVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Botón para salir de la actividad.
         */
        binding.ibFlechaParaSalir.setOnClickListener {
            finish()
        }

        //Funcionalidad de la actividad
        pasarADetalle()
    }

    /**
     * Recoge la publicación pulsada.
     * Actualiza la información de la publicación en la vista.
     */
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

        publicacion?.let {
            lifecycleScope.launch {
                ActualizarIcono.configurarIconoFavorito(
                    publicacion,
                    binding.ibBotonFavoritoDetalla,
                    viewModelFav,
                    lifecycleOwner = this@DetalleActivity
                )
            }
        }
    }
}