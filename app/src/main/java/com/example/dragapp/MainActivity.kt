package com.example.dragapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragapp.databinding.ActivityMainBinding
import com.example.dragapp.pojo.Queen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : QueensAdapter
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQueens()
        setTextWelcome()

        binding.imgLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cardHire.setOnClickListener {
            val intent = Intent(this,HireActivity::class.java)
            startActivity(intent)
        }

        binding.cardReview.setOnClickListener {
            val intent = Intent(this,CommentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTextWelcome() {
        val ref : CollectionReference = db.collection("User")
        val query = ref.whereEqualTo("email",Firebase.auth.currentUser?.email)
        query.get().addOnSuccessListener {
            for (document in it){
                val name = "¡Hola\n" + document.data.get("name") + "!"
                binding.title.text = name
            }
        }
        }

    private fun initQueens() {

        val ref : CollectionReference = db.collection("Queen")

        ref.get().addOnSuccessListener {

            var auxQueen : String
            var queen : Queen
            val queenList = ArrayList<Queen>()

                for (document in it){
                auxQueen = Gson().toJson(document.data)
                queen = Gson().fromJson(auxQueen, Queen::class.java)
                queenList.add(queen)
                }

            initRecyclerView(queenList)
        }
            .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
    }

    private fun initRecyclerView(queenList : ArrayList<Queen>) {
        adapter = QueensAdapter(queenList)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvQueens.layoutManager = llm
        binding.rvQueens.adapter = adapter
    }
}