package com.fjlr.firebase.adapter.favoritos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fjlr.firebase.adapter.PublicacionesDiffCallback
import com.fjlr.firebase.databinding.PublicacionesItemFavoritosBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.PublicacionesVistaModelo

class PublicacionAdaptadorFav(
    private val viewModel: PublicacionesVistaModelo
) : ListAdapter<PublicacionesModelo, PublicacionesViewHolderFav>(
    PublicacionesDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PublicacionesViewHolderFav =
        PublicacionesViewHolderFav(
            PublicacionesItemFavoritosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: PublicacionesViewHolderFav,
        position: Int
    ) {
        val publicacion = getItem(position)
        holder.bind(publicacion)
        Adaptador.configurarIconoFavorito(publicacion, holder.icono, viewModel)
    }
}