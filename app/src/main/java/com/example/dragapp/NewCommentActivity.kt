package com.example.dragapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.dragapp.databinding.ActivityNewCommentBinding
import com.example.dragapp.pojo.Comment
import com.example.dragapp.pojo.User
import com.example.dragapp.utils.Generics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewCommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewCommentBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this,CommentActivity::class.java)
            startActivity(intent)
        }

        initSpinner()
        initSpinnerRating()

        binding.btnAdd.setOnClickListener {
            if (binding.inputDescription.text.toString().isEmpty()) {
                Generics.showSnackBar(
                    binding.layoutNewComment,
                    "Debe ingresar un comentario")
            }else{
                sendComment(
                    Comment(binding.spinner.selectedItem.toString(),
                    binding.inputDescription.text.toString(),
                        Firebase.auth.currentUser?.email,
                    binding.spinnerRating.selectedItem.toString())
                )
                Generics.showSnackBar(binding.layoutNewComment
                    ,"¡Nuevo comentario agregado con exito!")
            }
        }

    }

    private fun initSpinnerRating() {
        val rating = ArrayList<String?>()
        rating.add("1")
        rating.add("2")
        rating.add("3")
        rating.add("4")
        rating.add("5")
        val adp = ArrayAdapter<Any?>(this, R.layout.style_spinner, rating as List<Any?>)
        adp.setDropDownViewResource(R.layout.style_dropdown_spinner)
        binding.spinnerRating.setAdapter(adp)
    }

    private fun sendComment(comment: Comment) {
            val ref : CollectionReference = db.collection("Comment")
            ref.add(comment)
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