package com.fjlr.firebase.adapter.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fjlr.firebase.adapter.PublicacionesDiffCallback
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class PublicacionAdaptador(
    private val viewModel: PublicacionesVistaModelo
) : ListAdapter<PublicacionesModelo, PublicacionesViewHolder>(
    PublicacionesDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesViewHolder =
        PublicacionesViewHolder(
            PublicacionesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: PublicacionesViewHolder,
        position: Int
    ) {
        val publicacion = getItem(position)
        holder.bind(publicacion)
        Adaptador.configurarIconoFavorito(publicacion, holder.icono, viewModel)
    }
}