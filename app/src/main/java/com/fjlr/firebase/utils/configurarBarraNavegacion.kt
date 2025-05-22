package com.fjlr.firebase.utils

import android.app.Activity
import android.content.Intent
import com.fjlr.firebase.view.PerfilActivity
import com.fjlr.firebase.view.AppActivity
import com.fjlr.firebase.view.BuscarActivity
import com.fjlr.firebase.view.FavoritosActivity
import com.fjlr.firebase.databinding.LayoutBarraBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Configura la barra de navegación de la actividad.
 * @param activity La actividad actual.
 * @param bindingBarra El binding de la barra de navegación.
 */
fun configurarBarraNavegacion(activity: Activity, bindingBarra: LayoutBarraBinding) {

    /**
     * Abre la actividad principal.
     */
    if (activity !is AppActivity) {
        bindingBarra.ibPrincipal.setOnClickListener {
            activity.startActivity(Intent(activity, AppActivity::class.java))
        }
    }

    /**
     * Abre la actividad de buscar.
     */
    if (activity !is BuscarActivity) {
        bindingBarra.ibBuscar.setOnClickListener {
            activity.startActivity(Intent(activity, BuscarActivity::class.java))
        }
    }

    /**
     * Abre la actividad de favoritos.
     */
    if (activity !is FavoritosActivity) {
        bindingBarra.ibFavoritos.setOnClickListener {
            activity.startActivity(Intent(activity, FavoritosActivity::class.java))
        }
    }

    /**
     * Abre la actividad de ajustes.
     * Comparo el usuario y si es el mismo, no hace nada.
     */
    bindingBarra.ibAjustes.setOnClickListener {
        if (activity is PerfilActivity &&
            (activity.intent.getStringExtra(ConstantesUtilidades.GET_STRING) == null ||
                    activity.intent.getStringExtra(ConstantesUtilidades.GET_STRING) ==
                    FirebaseAuth.getInstance().currentUser?.email)
        ) {
            return@setOnClickListener
        }
        activity.startActivity(Intent(activity, PerfilActivity::class.java))
    }
}
