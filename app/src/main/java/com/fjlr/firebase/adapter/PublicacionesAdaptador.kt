package com.fjlr.firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

//LISTADAPTER
class PublicacionesAdaptador(
    private var publicaciones: MutableList<PublicacionesModelo>,
    private val viewModel: PublicacionesVistaModelo
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

        holder.icono.setOnClickListener {
            publicacion.esFavorito = !publicacion.esFavorito
            actualizarIcono(holder.icono, publicacion.esFavorito)
            viewModel.alternarFavorito(publicacion)
        }
    }

    override fun getItemCount(): Int = publicaciones.size

    inner class PublicacionesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PublicacionesItemBinding.bind(view)
        val icono: LottieAnimationView = binding.ibBotonFavorito

        fun bind(publicaciones: PublicacionesModelo) {
            binding.tvTituloPublicacion.text = publicaciones.titulo
            binding.tvDescripcionPublicacion.text = publicaciones.descripcion
            binding.tvIngredientesPublicacion.text = publicaciones.ingredientes
            binding.tvPreparacionPublicacion.text = publicaciones.preparacion
        }
    }

    private fun actualizarIcono(icono: LottieAnimationView, esFavorito: Boolean) {
        if (esFavorito) {
            icono.setAnimation(R.raw.favorito)
            icono.playAnimation()
        } else {
            icono.setImageResource(R.drawable.favorito)
        }
    }

}