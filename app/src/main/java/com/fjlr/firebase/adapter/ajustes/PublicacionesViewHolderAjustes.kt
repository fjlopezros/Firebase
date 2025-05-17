package com.fjlr.firebase.adapter.ajustes

import androidx.recyclerview.widget.RecyclerView
import com.fjlr.firebase.databinding.PublicacionesItemAjustesBinding
import com.fjlr.firebase.model.PublicacionesModelo
import com.fjlr.firebase.utils.Adaptador
import com.fjlr.firebase.viewModel.AjustesVistaModelo

class PublicacionesViewHolderAjustes(private val binding: PublicacionesItemAjustesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val icono = binding.ibBotonFavoritoAjustes
    val ajustesVistaModelo = AjustesVistaModelo()

    fun bind(publicacion: PublicacionesModelo) {
        ajustesVistaModelo.obtenerNombreDeEmail(publicacion.autor) { nombre ->
            binding.tvUsuarioAjustes.text = nombre ?: "Null"
        }
        binding.tvTituloPublicacionAjustes.text = publicacion.titulo

        itemView.setOnClickListener {
            Adaptador.abrirDetalleDesdeItemAjustes(itemView, publicacion)
        }
    }
}