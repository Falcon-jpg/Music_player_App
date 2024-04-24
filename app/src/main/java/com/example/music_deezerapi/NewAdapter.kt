package com.example.music_deezerapi

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
class NewAdapter(private val context: Context, private var likedSongs: List<LikedSong>,val db:Database) : RecyclerView.Adapter<NewAdapter.LikedSongViewHolder>() {
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
        val lottie : LottieAnimationView

        init{
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)
            }
            lottie = itemView.findViewById(R.id.lottie)
            img1 = itemView.findViewById(R.id.music_image)
            name1 = itemView.findViewById(R.id.textView)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedSongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.llikedsongrow, parent, false)
        return LikedSongViewHolder(itemView,myListener)
    }

    private val animationStates = MutableList(likedSongs.size) { false }

    override fun onBindViewHolder(holder: LikedSongViewHolder, position: Int) {
        val song = likedSongs[position]
        val img = song.image
        val name= song.name
        Picasso.get().load(img).into(holder.img1)
        holder.name1.text = name


        holder.lottie.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (!animationStates[adapterPosition]) {
                holder.lottie.speed = 2.0f
                holder.lottie.playAnimation()
                animationStates[adapterPosition] = true

                // Perform action after animation completes (if needed)
                holder.lottie.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {}

                    override fun onAnimationEnd(animation: Animator) {
                        val removedSong = likedSongs[adapterPosition]
                        db.deleteSong(removedSong)
                        likedSongs = likedSongs.filterNot { it == removedSong }
                        notifyItemRemoved(adapterPosition)
                        animationStates.removeAt(adapterPosition)
                        Toast.makeText(context, "Song removed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAnimationCancel(animation: Animator) {}

                    override fun onAnimationRepeat(animation: Animator) {}
                })

            }
        }

    }
    override fun getItemCount(): Int {
        return likedSongs.size
    }

    fun refreshData(newSongs : List<LikedSong>){
        likedSongs = newSongs
    }



}
