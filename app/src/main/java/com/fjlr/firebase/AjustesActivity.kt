package com.fjlr.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityAjustesBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.google.firebase.auth.FirebaseAuth

class AjustesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjustesBinding

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

        configurarBarraNavegacion(this, binding.barraNavegacion)

        mostrarCorreo()
        binding.btCerrarSesion.setOnClickListener { cerrarSesion() }
    }
    fun mostrarCorreo(){
        val usuario = FirebaseAuth.getInstance().currentUser
        binding.tvCorreoUsuario.text = usuario?.email ?: "Correo no disponible"
    }

    fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}