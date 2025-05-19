package com.fjlr.firebase.adapter.ajustes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fjlr.firebase.adapter.PublicacionesDiffCallback
import com.fjlr.firebase.databinding.PublicacionesItemAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ActualizarIcono
import com.fjlr.firebase.viewModel.FavoritosVistaModelo

/**
 * Clase de adaptador para el RecyclerView de publicaciones en la pantalla de ajustes.
 * @param viewModel El ViewModel asociado al adaptador.
 */
class PublicacionAdaptadorAjustes(
    private val viewModel: FavoritosVistaModelo
) : ListAdapter<PublicacionesModelo, PublicacionesViewHolderAjustes>(
    PublicacionesDiffCallback()
) {
    /**
     * Metodo sobrecargado para crear el ViewHolder.
     * @param parent El ViewGroup al que se adjuntará la nueva vista.
     * @return Un nuevo ViewHolder para el RecyclerView.
     */
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

    /**
     * Metodo sobrecargado para vincular los datos del modelo a la vista.
     * @param holder El ViewHolder que contiene la vista.
     * @param position La posición del elemento en la lista.
     */
    override fun onBindViewHolder(
        holder: PublicacionesViewHolderAjustes,
        position: Int
    ) {
        val publicacion = getItem(position)
        holder.bind(publicacion)
        ActualizarIcono.configurarIconoFavorito(publicacion, holder.icono, viewModel)
    }
}