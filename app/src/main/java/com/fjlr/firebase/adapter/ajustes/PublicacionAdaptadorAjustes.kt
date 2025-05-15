package com.fjlr.firebase.adapter.ajustes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fjlr.firebase.adapter.PublicacionesDiffCallback
import com.fjlr.firebase.databinding.PublicacionesItemAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class PublicacionAdaptadorAjustes(
    private val viewModel: PublicacionesVistaModelo
) : ListAdapter<PublicacionesModelo, PublicacionesViewHolderAjustes>(
    PublicacionesDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesViewHolderAjustes =
        PublicacionesViewHolderAjustes(
            PublicacionesItemAjustesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: PublicacionesViewHolderAjustes,
        position: Int
    ) {
        val publicacion = getItem(position)
        holder.bind(publicacion)
        Adaptador.configurarIconoFavorito(publicacion, holder.icono, viewModel)
    }
}