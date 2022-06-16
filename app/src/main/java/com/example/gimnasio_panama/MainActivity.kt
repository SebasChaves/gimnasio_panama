/*package com.example.gimnasio_panama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}*/

package com.example.gimnasio_panama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gimnasio_panama.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import java.security.Principal

//import java.security.Principal


class MainActivity : AppCompatActivity() {

    //Variable para la autenticacion
    private lateinit var auth: FirebaseAuth
    //Variable para la vista
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Se establece el enlace con la vista xml mediante el objeto binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Se inicializa Firebase y se asigna el objeto para autenticación
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener { haceRegistro() };
        binding.btLogin.setOnClickListener { haceLogin() }

        //setContentView(R.layout.activity_main)

    }
    private fun haceLogin() {
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()
        println("HOla$email$clave")

        //Se usa la función para crear un usuario por medio de correo y contraseña
        auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    actualiza(user)
                } else {
                    Toast.makeText(baseContext,
                        getString(R.string.msg_fallo_login),
                        Toast.LENGTH_SHORT).show()
                    actualiza(null)
                }
            }
    }
    private fun haceRegistro() {
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()
        //println("HOla$email$clave")
        //Se usa la función para crear un usuario por medio de correo y contraseña
        auth.createUserWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    actualiza(user)
                } else {
                    Toast.makeText(baseContext,
                        getString(R.string.msg_fallo_registro),
                        Toast.LENGTH_SHORT).show()
                    actualiza(null)
                }
            }
    }
    private fun actualiza(user: FirebaseUser?) {
        if (user!=null) {
            // paso a la pantalla principal
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }
}