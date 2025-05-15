package com.fjlr.firebase.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fjlr.firebase.model.PublicacionesModelo

// DiffUtil callback para comparar los items
class PublicacionesDiffCallback : DiffUtil.ItemCallback<PublicacionesModelo>() {
    override fun areItemsTheSame(
        oldItem: PublicacionesModelo,
        newItem: PublicacionesModelo
    ): Boolean {
        // Compara los identificadores únicos de los elementos
        return oldItem.titulo == newItem.titulo && oldItem.autor == newItem.autor
    }

    override fun areContentsTheSame(
        oldItem: PublicacionesModelo,
        newItem: PublicacionesModelo
    ): Boolean {
        // Compara los contenidos (puedes añadir más propiedades según lo necesites)
        return oldItem == newItem
    }
}