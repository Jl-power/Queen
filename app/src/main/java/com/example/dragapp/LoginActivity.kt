package com.example.dragapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dragapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            emptyControl(binding.inputUser.text.toString(),binding.inputPass.text.toString())
        }

    }

    private fun emptyControl(email: String, password: String) {

        if(email.isEmpty() && password.isEmpty())
            Snackbar.make(binding.layoutLogin,"Ingrese sus datos por favor",
                Snackbar.LENGTH_SHORT).apply {
                view.background = resources.getDrawable(R.drawable.round_corner, null)
            }.show()
        if(email.isEmpty() && password.isNotEmpty())
            Snackbar.make(binding.layoutLogin,"Ingrese su correo por favor",
                Snackbar.LENGTH_SHORT).apply {
                view.background = resources.getDrawable(R.drawable.round_corner, null)
            }.show()
        if(password.isEmpty() && email.isNotEmpty())
            Snackbar.make(binding.layoutLogin,"Ingrese su contraseña por favor",
                Snackbar.LENGTH_SHORT).apply {
                view.background = resources.getDrawable(R.drawable.round_corner, null)
            }.show()
        if(email.isNotEmpty() && password.isNotEmpty())
            loginControl(email,password)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            next()
        }
    }

    private fun next(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loginControl(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    next()
                } else {
                    Snackbar.make(binding.layoutLogin,"Error algun dato erroneo",
                            Snackbar.LENGTH_SHORT).apply {
                        view.background = resources.getDrawable(R.drawable.round_corner, null)
                    }.show()
                }
            }
    }
}