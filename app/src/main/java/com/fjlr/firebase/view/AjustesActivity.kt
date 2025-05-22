package com.fjlr.firebase.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.fjlr.firebase.databinding.ActivityAjustesBinding
import com.fjlr.firebase.viewModel.RegistroVistaModelo
import com.fjlr.firebase.viewModel.UtilidadesPerfilVistaModelo
import com.google.firebase.auth.FirebaseAuth

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding
    private lateinit var viewModel: RegistroVistaModelo
    private lateinit var viewModelUtilidades: UtilidadesPerfilVistaModelo
    private val email = FirebaseAuth.getInstance().currentUser?.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegistroVistaModelo::class.java]
        viewModelUtilidades = ViewModelProvider(this)[UtilidadesPerfilVistaModelo::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibFlechaParaSalir.setOnClickListener {
            finish()
        }

        binding.btCambiarUsuario.setOnClickListener {
            val nuevoUsuario = binding.etCambiarUsuario.text.toString()
            if (nuevoUsuario.isNotBlank()) {
                viewModel.cambiarNombreUsuario(
                    email, binding.etCambiarUsuario.text.toString(),
                    callback = { guardado ->
                        if (guardado) {
                            Toast.makeText(this, "Usuario cambiado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Algo fallo", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        binding.btContrasena.setOnClickListener {
            viewModel.cambiarContrasena(email, callback = { enviado ->
                if (enviado) {
                    Toast.makeText(
                        this,
                        "Se ha enviado un correo para cambiar la contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModelUtilidades.cerrarSesion()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "No se ha podido enviar el correo", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }

        binding.ibSalir.setOnClickListener {
            viewModelUtilidades.cerrarSesion()
        }
    }
}