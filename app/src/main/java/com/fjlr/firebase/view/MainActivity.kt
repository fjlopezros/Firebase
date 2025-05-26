package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.fjlr.firebase.databinding.ActivityMainBinding
import com.fjlr.firebase.viewModel.SesionVistaModelo
import kotlinx.coroutines.launch

/**
 * Actividad principal de la aplicación.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SesionVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicialización del viewModel
        viewModel = ViewModelProvider(this)[SesionVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * Comprobación de la sesión activa.
         * Si está activa, se redirige a la pantalla principal de la App.
         */
        if (viewModel.sesionActiva()) {
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        }

        /**
         * Botón para ir a la actividad de registro.
         */
        binding.btRegistrarse.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        /**
         * Botón para salir de la aplicación.
         */
        binding.ibFlechaParaSalir.setOnClickListener { finishAffinity() }

        /**
         * Botón para iniciar sesión.
         * Si el inicio de sesión es exitoso, se redirige a la pantalla principal de la App.
         */
        binding.btIniciarSesion.setOnClickListener {
        lifecycleScope.launch {
                viewModel.iniciarSesion(
                    binding.etUsuarioSesion.text.toString(),
                    binding.etContrasenaSesion.text.toString()
                ).onSuccess {
                    startActivity(Intent(this@MainActivity, AppActivity::class.java))
                    finish()
                }.onFailure {
                    Toast.makeText(this@MainActivity, "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}