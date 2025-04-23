package com.fjlr.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAnadirPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibFlechaParaSalir.setOnClickListener { finish() }

        binding.btGuardarPublicacion.setOnClickListener {

            db.collection("publicaciones")
                .add(PublicacionesEntity(titulo = "pizza", descripcion = "casera", ingredientes = "harina"))
                .addOnSuccessListener {
                    Toast.makeText(this, "Receta guardada!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar receta", Toast.LENGTH_SHORT).show()
                }
        }

    }

}