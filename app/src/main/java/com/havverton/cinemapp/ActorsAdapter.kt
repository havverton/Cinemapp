package com.havverton.cinemapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter(actors: List<Actor>) : RecyclerView.Adapter<ActorViewHolder>() {
    val actors = actors
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.name.text = actors[position].name
        holder.pic.setImageDrawable(actors[position].pic)
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById<TextView>(R.id.actorName)
    val pic: ImageView = itemView.findViewById<ImageView>(R.id.actorPic)
}
