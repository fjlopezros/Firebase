package com.fjlr.firebase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.viewModel.AjustesVistaModelo
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo
import com.squareup.picasso.Picasso

//LISTADAPTER
class PublicacionesAdaptador(
    private var publicaciones: MutableList<PublicacionesModelo>,
    private val viewModel: PublicacionesVistaModelo,
    private val fn: (PublicacionesModelo) -> Unit
) : RecyclerView.Adapter<PublicacionesAdaptador.PublicacionesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesViewHolder = PublicacionesViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.publicaciones_item, parent, false)
    )

    override fun onBindViewHolder(
        holder: PublicacionesViewHolder,
        position: Int
    ) {
        val publicacion = publicaciones[position]
        holder.bind(publicacion)

        actualizarIcono(holder.icono, publicacion.esFavorito)
        Log.d("PublicacionesAdaptador", "Publicacion: ${publicacion.esFavorito}")

        holder.icono.setOnClickListener {
            publicacion.esFavorito = !publicacion.esFavorito

            actualizarIcono(holder.icono, publicacion.esFavorito)
            viewModel.alternarFavorito(publicacion)
            Log.d("PublicacionesAdaptador", "Publicacion: ${publicacion.esFavorito}")
        }
    }

    override fun getItemCount(): Int = publicaciones.size

    inner class PublicacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PublicacionesItemBinding.bind(view)
        val icono: ImageButton = binding.ibBotonFavorito
        val ajustesVistaModelo = AjustesVistaModelo()

        fun bind(publicaciones: PublicacionesModelo) {
            ajustesVistaModelo.obtenerNombreUsuario{ nombre ->
                binding.tvUsuario.text = nombre ?: "Nombre no disponible"
            }
            binding.tvTituloPublicacion.text = publicaciones.titulo
            binding.tvDescripcionPublicacion.text = publicaciones.descripcion
            binding.tvIngredientesPublicacion.text = publicaciones.ingredientes
            binding.tvPreparacionPublicacion.text = publicaciones.preparacion
            //CAMBIAR IMAGENES
            Picasso.get().load(publicaciones.imagen).into(binding.ivFoto)

            itemView.setOnClickListener {
                fn(publicaciones)
            }
        }
    }
    private fun actualizarIcono(icono: ImageButton, esFavorito: Boolean) {
        if (esFavorito) {
            icono.setImageResource(R.drawable.favorito)
        } else {
            icono.setImageResource(R.drawable.menos)
        }
    }
}