package com.example.music_deezerapi

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class MyAdapter(val context : Context , val dataList : List<Data> , val db : Database) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }

    class MyViewHolder(itemVIew : View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemVIew){
            val image : ImageView
            val songName : TextView
            val artist : TextView
            val status : ToggleButton

            init{
                itemVIew.setOnClickListener(){
                    listener.onItemClick(adapterPosition)
                }
                status = itemVIew.findViewById(R.id.heart)
                image = itemVIew.findViewById(R.id.music_image)
                songName = itemVIew.findViewById(R.id.textView)
                artist = itemVIew.findViewById(R.id.textView2)
            }
    }

    private val likedSongs: MutableSet<Pair<String, String>> = mutableSetOf()

    init {
        preloadLikedSongs()
    }

    private fun preloadLikedSongs() {
        val likedSongsList = db.getSongs()
        likedSongs.clear()
        likedSongsList.forEach { song ->
            likedSongs.add(Pair(song.name, song.artist))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachrow,parent,false)
        return MyViewHolder(itemView,myListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currData = dataList[position]
        holder.songName.text = currData.title
        holder.artist.text = currData.artist.name
        Picasso.get().load(currData.album.cover).into(holder.image)

        val isLiked = likedSongs.contains(Pair(currData.title, currData.artist.name))
        holder.status.setOnCheckedChangeListener(null)
        holder.status.isChecked = isLiked

        holder.status.setOnCheckedChangeListener{ _, isChecked ->
            val song = LikedSong(currData.title, currData.album.cover , currData.artist.name, currData.preview)
            if(isChecked){
            db.insertSong(song , context)
            }else{
            db.deleteSong(song)
                Toast.makeText(context,"Removed from Liked Song",Toast.LENGTH_SHORT).show()
            }

        }
    }

}