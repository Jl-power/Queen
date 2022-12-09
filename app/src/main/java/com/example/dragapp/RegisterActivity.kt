package com.example.dragapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dragapp.databinding.ActivityRegisterBinding
import com.example.dragapp.pojo.User
import com.example.dragapp.utils.Generics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnAccept.setOnClickListener {
            controlInputs(binding.inputMailR.text.toString(), binding.inputPassR.text.toString(),
                binding.inputNameR.text.toString(), binding.inputNumberR.text.toString())
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun controlInputs(email: String, password: String, name: String, number: String) {

        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || number.isEmpty())
            Generics.showSnackBar(binding.layoutRegister,"No puede dejar campos vacios")
        if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && number.isNotEmpty())
            newUser(email, password, name, number)

    }

    private fun newUser(email : String, password : String, name : String, number : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserFirebase(User(email,name,number))
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Generics.showSnackBar(binding.layoutRegister,
                        "Ya existe un usuario con ese correo")
                }
            }
    }

    private fun saveUserFirebase(user: User) {
        val ref : CollectionReference = db.collection("User")
        ref.add(user)
    }
}