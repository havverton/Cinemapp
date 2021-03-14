
package com.havverton.cinemapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.havverton.cinemapp.R
import com.havverton.cinemapp.model.Actor

class ActorsAdapter() : RecyclerView.Adapter<ActorViewHolder>() {
    var actors : List<Actor> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    fun setActorsList(list : List<Actor>){
        actors = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.name.text = actors[position].name
        Glide
            .with(holder.itemView.context)
            .load(actors[position].imageUrl)
            .apply(RequestOptions().override(80, 80))
            .apply(RequestOptions().centerCrop())
            .into(holder.pic)
    }
}

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.actorName)
    val pic: ImageView = itemView.findViewById(R.id.actorPic)
}

