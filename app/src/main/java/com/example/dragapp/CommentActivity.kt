package com.example.dragapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dragapp.databinding.ActivityCommentBinding
import com.example.dragapp.pojo.Comment
import com.example.dragapp.pojo.Queen
import com.example.dragapp.utils.Generics
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding
    private val db = Firebase.firestore
    private lateinit var adapter : CommentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnComment.setOnClickListener {
            val intent = Intent(this,NewCommentActivity::class.java)
            startActivity(intent)
        }

        initComment()

    }

    private fun initComment() {
        val ref : CollectionReference = db.collection("Comment")

        ref.get().addOnSuccessListener {

            var auxComment : String
            var comment : Comment
            val commentList = ArrayList<Comment>()

            for (document in it){
                auxComment = Gson().toJson(document.data)
                comment = Gson().fromJson(auxComment, Comment::class.java)
                commentList.add(comment)
            }

            initRecyclerView(commentList)
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun initRecyclerView(commentList: ArrayList<Comment>) {
        adapter = CommentAdapter(commentList)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.rvComments.layoutManager = llm
        binding.rvComments.adapter = adapter

        if (commentList.isEmpty())
            Generics.showSnackBar(binding.layoutComment,
                "No hay comentarios realizados todavia")
    }
}