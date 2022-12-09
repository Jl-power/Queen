package com.example.dragapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragapp.pojo.Comment

class CommentAdapter(private val comments : ArrayList<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.comment_item, parent, false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val item: Comment = comments[position]

        holder.name.text = item.name
        holder.comment.text = item.detail
        holder.user.text = item.user
        holder.rating.text = item.rating
    }

    override fun getItemCount() = comments.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView
        val comment : TextView
        val user : TextView
        val rating : TextView

        init {
            name = itemView.findViewById(R.id.tvComment_name)
            comment = itemView.findViewById(R.id.tvComment_detail)
            user = itemView.findViewById(R.id.tvComment_user)
            rating = itemView.findViewById(R.id.tvComment_rating)
        }
    }
}
