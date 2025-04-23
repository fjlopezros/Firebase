package com.fjlr.firebase

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        verificarSesionAbierta()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.btRegistrarse.setOnClickListener { irARegistro() }
        binding.btIniciarSesion.setOnClickListener { iniciarSesion() }
        binding.ibFlechaParaSalir.setOnClickListener { finishAffinity() }

    }

    fun irARegistro() {
        startActivity(Intent(this, RegistroActivity::class.java))
    }

    fun iniciarSesion() {
        iniciarSesion(
            binding.etUsuarioSesion.text.toString(),
            binding.etContrasenaSesion.text.toString()
        )
    }

    fun iniciarSesion(email: String, contrasena: String) {
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, AppActivity::class.java))
                } else {
                    Toast.makeText(
                        baseContext,
                        "El inicio de sesion fallo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun verificarSesionAbierta() {
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            startActivity(Intent(this, AppActivity::class.java))
            finish()
        }
    }
}