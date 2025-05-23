package com.fjlr.firebase.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.fjlr.firebase.databinding.ActivityRegistroBinding
import com.fjlr.firebase.viewModel.RegistroVistaModelo

/**
 * Actividad de registro de usuarios.
 */
class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var viewModel: RegistroVistaModelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Configuración del binding
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Instancia del viewModel
        viewModel = ViewModelProvider(this)[RegistroVistaModelo::class.java]

        /**
         * Botón para salir de la actividad.
         */
        binding.ibFlechaParaSalir.setOnClickListener { finish() }

        /**
         * Recoge los datos introducidos por el usuario y los registra.
         */
        binding.btRegistro.setOnClickListener {
            viewModel.registrarse(
                binding.etEmailRegistro.text.toString(),
                binding.etContrasenaRegistro.text.toString(),
                binding.etUsuarioRegistro.text.toString()
            ) { success, error ->
                if (success) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}