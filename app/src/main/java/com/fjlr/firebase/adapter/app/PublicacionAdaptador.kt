package com.fjlr.firebase.adapter.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import com.fjlr.firebase.adapter.PublicacionesDiffCallback
import com.fjlr.firebase.databinding.PublicacionesItemBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.ActualizarIcono
import com.fjlr.firebase.viewModel.FavoritosVistaModelo
import kotlinx.coroutines.launch

/**
 * Clase de adaptador para el RecyclerView de publicaciones en la pantalla principal.
 * @param viewModel El ViewModel asociado al adaptador.
 */
class PublicacionAdaptador(
    private val viewModel: FavoritosVistaModelo,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<PublicacionesModelo, PublicacionesViewHolder>(
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
    ): PublicacionesViewHolder =
        PublicacionesViewHolder(
            PublicacionesItemBinding.inflate(
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
        holder: PublicacionesViewHolder,
        position: Int
    ) {
        val publicacion = getItem(position)
        holder.bind(publicacion)

        lifecycleOwner.lifecycleScope.launch {
            ActualizarIcono.configurarIconoFavorito(
                publicacion,
                holder.icono,
                viewModel,
                lifecycleOwner
            )
        }
    }
}