package com.fjlr.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.ivFlechaParaSalir.setOnClickListener { irAMenu() }
        binding.btRegistro.setOnClickListener { creacionUsuario() }

    }

    fun irAMenu(){
        finish()
    }

    fun creacionUsuario() {
        creacionUsuario(
            binding.etUsuarioRegistro.text.toString(),
            binding.etContrasenaRegistro.text.toString()
        )
        binding.etUsuarioRegistro.text.clear()
        binding.etContrasenaRegistro.text.clear()
    }

    fun creacionUsuario(email: String, contrasena: String) {
        auth.createUserWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(
                        baseContext,
                        "El registro fallo",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}