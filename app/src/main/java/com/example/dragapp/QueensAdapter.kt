package com.example.dragapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        Picasso.get().load(item.imgUrl).into(holder.imagen)

    }

    override fun getItemCount() = queens.size

    class QueenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : Button
        val imagen : ImageView

        init {
            name = itemView.findViewById(R.id.btnName)
            imagen = itemView.findViewById(R.id.imgProfile)
        }
    }
}
