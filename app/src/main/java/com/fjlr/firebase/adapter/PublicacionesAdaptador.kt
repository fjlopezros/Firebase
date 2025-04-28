package com.fjlr.firebase.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.R
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.utils.ConstantesUtilidades
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//LISTADAPTER
class PublicacionesAdaptador(
    private var publicaciones: MutableList<PublicacionesModelo>
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
            val nuevoEstado = animacionIcono(holder.icono, R.raw.favorito, publicacion.esFavorito)
            publicacion.esFavorito = nuevoEstado
            Log.d("PublicacionesAdaptador", "onBindViewHolder: $nuevoEstado")
            FirebaseFirestore.getInstance().collection(ConstantesUtilidades.COLECCION_FIREBASE)
                .document(publicacion.titulo+"_"+ FirebaseAuth.getInstance().currentUser?.email)
                .update("favorito", nuevoEstado)
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

    private fun animacionIcono(
        imageView: LottieAnimationView,
        animation: Int,
        like: Boolean
    ): Boolean {
        return if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
            true
        } else {
            imageView.setImageResource(R.drawable.favorito)
            false
        }
    }

}