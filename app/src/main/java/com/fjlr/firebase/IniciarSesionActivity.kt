package com.fjlr.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityIniciarSesionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        binding.btEnviarSesion.setOnClickListener {
            iniciarSesion(
                binding.etUsuarioSesion.text.toString(),
                binding.etContrasenaSesion.text.toString()
            )
            binding.etUsuarioSesion.text.clear()
            binding.etContrasenaSesion.text.clear()
        }
    }
    fun iniciarSesion(email:String, contrasena:String) {
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Todo salio como esperaba",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "El inicio de sesion fallo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
