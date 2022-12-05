package com.example.dragapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dragapp.databinding.ActivityLoginBinding
import com.example.dragapp.utils.Generics
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
            emptyControl(binding.inputMail.text.toString(),binding.inputPass.text.toString())
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvRecover.setOnClickListener {
            val intent = Intent(this,RecoverActivity::class.java)
            startActivity(intent)
        }

    }

    private fun emptyControl(email: String, password: String) {

        if(email.isEmpty() && password.isEmpty())
            Generics.showSnackBar(binding.layoutLogin,"Ingrese sus datos por favor")
        if(email.isEmpty() && password.isNotEmpty())
            Generics.showSnackBar(binding.layoutLogin,"Ingrese su correo por favor")
        if(password.isEmpty() && email.isNotEmpty())
            Generics.showSnackBar(binding.layoutLogin,"Ingrese su contraseña por favor")
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
                    Generics.showSnackBar(binding.layoutLogin,"Error algun dato erroneo")
                }
            }
    }
}