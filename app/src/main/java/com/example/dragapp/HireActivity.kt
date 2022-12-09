package com.example.dragapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.dragapp.databinding.ActivityHireBinding
import com.example.dragapp.utils.Generics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HireActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHireBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        initSpinner()

        binding.btnSend.setOnClickListener {
                controlEmpty()
            }
    }

    private fun controlEmpty() {
        if (binding.inputPrice.text.toString().isNotEmpty() &&
            binding.inputTime.text.toString().isNotEmpty() &&
            binding.inputDescription.text.toString().isNotEmpty())
                sendEmail()
        if (binding.inputPrice.text.toString().isEmpty() ||
            binding.inputTime.text.toString().isEmpty() ||
            binding.inputDescription.text.toString().isEmpty())
            Generics.showSnackBar(binding.layoutHire,"Todos los campos son obligatorios")
    }

    private fun sendEmail() {

        val ref : CollectionReference = db.collection("Queen")
        val query = ref.whereEqualTo("name",binding.spinner.selectedItem.toString())
        query.get().addOnSuccessListener {
            for (document in it){
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_EMAIL,
                    arrayOf<String>(document.data.get("email").toString()))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Queen Application")
                val message = "Hola! te informamos que el " +
                        "usuario ${Firebase.auth.currentUser?.email} quiere ofrecerte un trabajo!" +
                        "\n Precio : ${binding.inputPrice.text.toString()}" +
                        "\n Horas : ${binding.inputTime.text.toString()}" +
                        "\n Descripcion : ${binding.inputDescription.text.toString()}" +
                        "\n\n Si te interesa la propuesta comunicate con el usuario a la brevedad" +
                        "\n Gracias por usar los servicios de Queen"
                intent.putExtra(Intent.EXTRA_TEXT,message)
                intent.type = "message/rfc822"
                startActivity(intent)
                Generics.showSnackBar(binding.layoutHire
                    ,"Correo enviado! espera la respuesta de la Queen")
            }
        }
    }

    private fun initSpinner() {

        val ref : CollectionReference = db.collection("Queen")

        ref.get().addOnSuccessListener {

            var queenName : String
            val queenList = ArrayList<String?>()

            for (document in it){
                queenName = document.data.get("name").toString()
                queenList.add(queenName)
            }

            val adp = ArrayAdapter<Any?>(
                this,
                R.layout.style_spinner, queenList as List<Any?>
            )
            adp.setDropDownViewResource(R.layout.style_dropdown_spinner)
            binding.spinner.setAdapter(adp)
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }
}