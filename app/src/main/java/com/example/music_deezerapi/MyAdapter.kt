package com.example.music_deezerapi

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class MyAdapter(val context : Activity , val dataList : List<Data>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

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

            init{
                itemVIew.setOnClickListener(){
                    listener.onItemClick(adapterPosition)
                }
                image = itemVIew.findViewById(R.id.music_image)
                songName = itemVIew.findViewById(R.id.textView)
                artist = itemVIew.findViewById(R.id.textView2)
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
    }




}