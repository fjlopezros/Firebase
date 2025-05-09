package com.fjlr.firebase.utils

import android.app.Activity
import android.content.Intent
import com.fjlr.firebase.view.AjustesActivity
import com.fjlr.firebase.view.AppActivity
import com.fjlr.firebase.view.BuscarActivity
import com.fjlr.firebase.view.FavoritosActivity
import com.fjlr.firebase.databinding.LayoutBarraBinding
import com.google.firebase.auth.FirebaseAuth

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

    bindingBarra.ibAjustes.setOnClickListener {
        if (activity is AjustesActivity &&
            (activity.intent.getStringExtra("emailDelPerfil") == null ||
                    activity.intent.getStringExtra("emailDelPerfil") ==
                    FirebaseAuth.getInstance().currentUser?.email)
        ) {
            return@setOnClickListener
        }
        activity.startActivity(Intent(activity, AjustesActivity::class.java))
    }
}
