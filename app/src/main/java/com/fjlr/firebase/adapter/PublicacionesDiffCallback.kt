package com.fjlr.firebase.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fjlr.firebase.model.PublicacionesModelo

/**
 * Clase de utilidad para calcular las diferencias entre listas de publicaciones.
 */
class PublicacionesDiffCallback : DiffUtil.ItemCallback<PublicacionesModelo>() {
    /**
     * Compara si dos elementos son el mismo objeto.
     */
    override fun areItemsTheSame(
        oldItem: PublicacionesModelo,
        newItem: PublicacionesModelo
    ): Boolean {
        return oldItem.titulo == newItem.titulo && oldItem.autor == newItem.autor
    }

    /**
     * Compara si dos elementos tienen los mismos contenidos.
     */
    override fun areContentsTheSame(
        oldItem: PublicacionesModelo,
        newItem: PublicacionesModelo
    ): Boolean {
        return oldItem == newItem
    }
}