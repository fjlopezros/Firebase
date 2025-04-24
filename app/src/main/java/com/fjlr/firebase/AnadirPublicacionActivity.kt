package com.fjlr.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityAnadirPublicacionBinding
import com.fjlr.firebase.utils.Constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
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
            subirPublicacion()
        }
    }

    fun subirPublicacion() {
        val titulo = binding.etTituloAnadir.text.toString()
        val descripcion = binding.etDescripcionAnadir.text.toString()
        val ingredientes = binding.etIngredientes.text.toString()
        val preparacion = binding.etPreparacion.text.toString()

        if (titulo.isNotEmpty() && descripcion.isNotEmpty() &&
            preparacion.isNotEmpty() && ingredientes.isNotEmpty()) {
            val publicaciones = hashMapOf(
                Constantes.TITULO to titulo,
                Constantes.DESCRIPCION to descripcion,
                Constantes.INGREDIENTES to ingredientes,
                Constantes.PREPARACION to preparacion,
                Constantes.FAVORITO to false,
                Constantes.TIEMPO_ORDENAR_PUBLI to FieldValue.serverTimestamp()
            )

            val timestamp = System.currentTimeMillis()
            val docId = "${titulo}_" +
                    "${FirebaseAuth.getInstance().currentUser?.email}"

            Log.d("EEEEEEEEEE", docId)


            db.collection(Constantes.COLECCION_FIREBASE).document(docId)
                .set(publicaciones)
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
    }
}