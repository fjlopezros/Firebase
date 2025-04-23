package com.fjlr.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adaptador.Publicaciones
import com.fjlr.firebase.databinding.ActivityAppBinding
import com.fjlr.firebase.entity.PublicacionesEntity
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = FirebaseFirestore.getInstance()

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarBarraNavegacion(this, binding.barraNavegacion)

        var listaPublicaciones = mutableListOf<PublicacionesEntity>()
        val adapter = Publicaciones(listaPublicaciones)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.btAnadirPublicacion.setOnClickListener {
            startActivity(Intent(this, AnadirPublicacionActivity::class.java))
        }
        db.collection("publicaciones")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Firestore", "Escucha fallida", e)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    listaPublicaciones.clear()
                    for (doc in snapshots.documents) {
                        val publicacion = doc.toObject(PublicacionesEntity::class.java)
                        if (publicacion != null) {
                            listaPublicaciones.add(publicacion)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }
}