package com.fjlr.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.google.firebase.firestore.FirebaseFirestore

class AnadirPublicacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirPublicacionBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = FirebaseFirestore.getInstance()

        binding = ActivityAnadirPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibFlechaParaSalir.setOnClickListener { finish() }

        binding.btGuardarPublicacion.setOnClickListener {
            try {
                val titulo = binding.etTituloAnadir.text.toString()
                val descripcion = binding.etDescripcionAnadir.text.toString()
                val ingredientes = binding.etIngredientes.text.toString()

                if (titulo.isNotEmpty() && descripcion.isNotEmpty() && ingredientes.isNotEmpty()) {
                    val publicaciones = hashMapOf(
                        "titulo" to titulo,
                        "descripcion" to descripcion,
                        "ingredientes" to ingredientes
                    )

                    db.collection("publicaciones").add(publicaciones)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Receta guardada", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                        }
                    finish()
                } else {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            } catch (_: SecurityException) {
                Log.d("ERROR CAPTURADO", "error seguridad")
            }
        }
    }
}