package com.fjlr.firebase

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    object Global {
        var preferenciasCompartidas = "sharedpreferences"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //verificarSesionAbierta()
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            // El usuario ya inició sesión, puedes ir a la pantalla principal
            val intent = Intent(this, AppActivity::class.java)
            intent.putExtra("Correo", usuario.email) // si necesitas el correo
            startActivity(intent)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth


        binding.btRegistrarse.setOnClickListener { irARegistro() }
        binding.btIniciarSesion.setOnClickListener { iniciarSesion() }
        binding.ibFlechaParaSalir.setOnClickListener { salirDeLaAplicacion() }

    }

    fun salirDeLaAplicacion() {
        finishAffinity()
    }

    fun irARegistro() {
        startActivity(Intent(this, RegistroActivity::class.java))
    }

    fun iniciarSesion() {
        iniciarSesion(
            binding.etUsuarioSesion.text.toString(),
            binding.etContrasenaSesion.text.toString()
        )
        binding.etUsuarioSesion.text.clear()
        binding.etContrasenaSesion.text.clear()
    }

    fun iniciarSesion(email: String, contrasena: String) {
        auth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, AppActivity::class.java))

                    //guardarSesion(email)

                } else {
                    Toast.makeText(
                        baseContext,
                        "El inicio de sesion fallo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun verificarSesionAbierta() {
        var sesionAbierta: SharedPreferences =
            this.getSharedPreferences(Global.preferenciasCompartidas, MODE_PRIVATE)

        var correo = sesionAbierta.getString("Correo", null)
        if (correo != null) {
            var intent = Intent(this, AppActivity::class.java)
            intent.putExtra("Correo", correo)
            startActivity(intent)
        }
    }

    fun guardarSesion(correo: String) {
        var guardarSesion: SharedPreferences.Editor =
            this.getSharedPreferences(Global.preferenciasCompartidas, MODE_PRIVATE).edit()
        guardarSesion.putString("Correo", correo)
        guardarSesion.apply()
    }
}