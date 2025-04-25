package com.fjlr.firebase

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.firebase.adapter.PublicacionesFavAdaptador
import com.fjlr.firebase.databinding.ActivityFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.fjlr.firebase.utils.configurarBarraNavegacion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding

    private lateinit var db: FirebaseFirestore

    private var listaPublicaciones = mutableListOf<PublicacionesModelo>()
    private lateinit var adapter: PublicacionesFavAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = FirebaseFirestore.getInstance()

        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarBarraNavegacion(this, binding.barraNavegacion)
        inicializarRecyclerView()
        cargarPublicaciones()
    }

    private fun inicializarRecyclerView() {
        adapter = PublicacionesFavAdaptador(listaPublicaciones)
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFav.adapter = adapter
    }

    private fun cargarPublicaciones() {
        db.collection(ConstantesUtilidades.COLECCION_FIREBASE)
            .whereEqualTo("esFavorito", true)
            .orderBy(ConstantesUtilidades.TIEMPO_ORDENAR_PUBLI, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Firestore", "Escucha fallida", e)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    listaPublicaciones.clear()
                    for (doc in snapshots.documents) {
                        val publicacion = doc.toObject(PublicacionesModelo::class.java)
                        if (publicacion != null) {
                            listaPublicaciones.add(publicacion)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
    }
}