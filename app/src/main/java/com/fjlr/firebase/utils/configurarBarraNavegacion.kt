package com.fjlr.firebase.utils

import android.app.Activity
import android.content.Intent
import com.fjlr.firebase.view.AjustesActivity
import com.fjlr.firebase.view.AppActivity
import com.fjlr.firebase.view.BuscarActivity
import com.fjlr.firebase.view.FavoritosActivity
import com.fjlr.firebase.databinding.LayoutBarraBinding

fun configurarBarraNavegacion(activity: Activity, bindingBarra: LayoutBarraBinding) {

    if (activity !is AppActivity) {
        bindingBarra.ibPrincipal.setOnClickListener {
            activity.startActivity(Intent(activity, AppActivity::class.java))
        }
    }

    if (activity !is BuscarActivity) {
        bindingBarra.ibBuscar.setOnClickListener {
            activity.startActivity(Intent(activity, BuscarActivity::class.java))
        }
    }

    if (activity !is FavoritosActivity) {
        bindingBarra.ibFavoritos.setOnClickListener {
            activity.startActivity(Intent(activity, FavoritosActivity::class.java))
        }
    }

    if (activity !is AjustesActivity) {
        bindingBarra.ibAjustes.setOnClickListener {
            activity.startActivity(Intent(activity, AjustesActivity::class.java))
        }
    }
}
