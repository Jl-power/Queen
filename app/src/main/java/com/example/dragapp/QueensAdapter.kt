package com.example.dragapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dragapp.pojo.Queen
import com.squareup.picasso.Picasso

class QueensAdapter(private val queens : ArrayList<Queen>) :
    RecyclerView.Adapter<QueensAdapter.QueenViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueenViewHolder {
        return QueenViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.queen_item, parent, false))
    }

    override fun onBindViewHolder(holder: QueenViewHolder, position: Int) {
        val item: Queen = queens[position]

        holder.name.text = item.name
        Picasso.get().load(item.imgUrl).into(holder.image)

        holder.card.setOnClickListener {
            val intent = Intent(it.context,DetailActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("realName",item.realName)
            intent.putExtra("imgUrl",item.imgUrl)
            intent.putExtra("age",item.age)
            intent.putExtra("description",item.description)
            intent.putExtra("location",item.location)
            intent.putExtra("gallery",item.gallery)
            intent.putExtra("socialMedia",item.socialMedia)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount() = queens.size

    class QueenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : Button
        val image : ImageView
        val card : CardView

        init {
            name = itemView.findViewById(R.id.btnName)
            image = itemView.findViewById(R.id.imgProfile)
            card = itemView.findViewById(R.id.cardView)
        }
    }
}
