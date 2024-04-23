package com.example.music_deezerapi

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class NewAdapter(private val context: Context, private var likedSongs: List<LikedSong>) : RecyclerView.Adapter<NewAdapter.LikedSongViewHolder>() {
    private lateinit var myListener: onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    class LikedSongViewHolder(itemView : View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val img1 : ImageView
        val name1 : TextView

        init{
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)
            }
            img1 = itemView.findViewById(R.id.music_image)
            name1 = itemView.findViewById(R.id.textView)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedSongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.llikedsongrow, parent, false)
        return LikedSongViewHolder(itemView,myListener)
    }

    override fun onBindViewHolder(holder: LikedSongViewHolder, position: Int) {
        val song = likedSongs[position]
        val img = song.image
        val name= song.name
        Picasso.get().load(img).into(holder.img1)
        holder.name1.text = name

    }
    override fun getItemCount(): Int {
        return likedSongs.size
    }

    fun refreshData(newSongs : List<LikedSong>){
        likedSongs = newSongs
        notifyDataSetChanged()
    }



}
