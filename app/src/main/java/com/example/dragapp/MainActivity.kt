package com.example.dragapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.dragapp.database.QueenDB
import com.example.dragapp.databinding.ActivityMainBinding
import com.example.dragapp.pojo.Queen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonObject

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : QueensAdapter
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore
    private val room by lazy {
        Room.databaseBuilder(this, QueenDB::class.java, "Queen")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQueens()

        binding.imgLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initQueens() {

        val ref : CollectionReference = db.collection("Queen")

        ref.get().addOnSuccessListener { result ->

            var auxQueen : String
            var queen : Queen
            val queenList = ArrayList<Queen>()

                for (document in result){
                auxQueen = Gson().toJson(document.data)
                queen = Gson().fromJson(auxQueen, Queen::class.java)
                queenList.add(queen)
                }

            initRecyclerView(queenList)
            saveDB(queenList)
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

    private fun saveDB(queenList: ArrayList<Queen>){
        for (data in queenList) {
            room.getQueenDao().insertData(data)
        }

        val prueba = room.getQueenDao().getAll()
        Log.d("Prueba", "Data: $prueba")
    }
}