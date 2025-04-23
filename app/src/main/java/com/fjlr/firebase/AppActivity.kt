package com.fjlr.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.google.firebase.firestore.FirebaseFirestore

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarBarraNavegacion(this, binding.barraNavegacion)

        binding.btGuardarPublicacion.setOnClickListener { startActivity(Intent(this,
            AnadirPublicacionActivity::class.java)) }
    }
}