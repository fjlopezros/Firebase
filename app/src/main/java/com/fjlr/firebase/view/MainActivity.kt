package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.fjlr.firebase.databinding.ActivityMainBinding
import com.fjlr.firebase.viewModel.SesionVistaModelo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SesionVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[SesionVistaModelo::class.java]

        if (viewModel.sesionActiva()) {
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        }

        binding.btRegistrarse.setOnClickListener {
            startActivity(Intent(this,RegistroActivity::class.java))
        }

        binding.ibFlechaParaSalir.setOnClickListener { finishAffinity() }

        binding.btIniciarSesion.setOnClickListener {
            viewModel.iniciarSesion(
                binding.etUsuarioSesion.text.toString(),
                binding.etContrasenaSesion.text.toString()
            ) { success, error ->
                if (success) {
                    startActivity(Intent(this, AppActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al Iniciar Sesion", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}