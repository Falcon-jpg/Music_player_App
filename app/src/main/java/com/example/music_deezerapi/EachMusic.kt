package com.example.music_deezerapi

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.music_deezerapi.databinding.ActivityEachMusicBinding
import com.squareup.picasso.Picasso

class EachMusic : AppCompatActivity() {
    private lateinit var binding : ActivityEachMusicBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEachMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val artist = intent.getStringExtra("artist")!!
        val name = intent.getStringExtra("title")!!
        val image = intent.getStringExtra("image")!!
        val link = intent.getStringExtra("link")!!

        binding.name1.text = artist
        binding.name.text = name
        Picasso.get().load(image).into(binding.imageView)

        mediaPlayer = MediaPlayer.create(this, link.toUri())


        mediaPlayer.setVolume(1f,1f)
        val totalTime = mediaPlayer.duration

        binding.play.setOnClickListener{
            play(link)
        }

        binding.stop.setOnClickListener{
            stop()
        }

        binding.seek.max = totalTime
        //to reflect the change in lyrics when user moves the seekbar
        binding.seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })



        handler = Handler()
        runnable = Runnable {
            try {
                binding.seek.progress = mediaPlayer.currentPosition
                handler.postDelayed(runnable, 1000)
            } catch (exception: Exception) {
                binding.seek.progress = 0
            }
        }
        handler.postDelayed(runnable, 0)


        }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }

    private fun play(link : String) {
        if (!mediaPlayer.isPlaying) {
            if (mediaPlayer.currentPosition == mediaPlayer.duration) {
                mediaPlayer.reset()
                mediaPlayer = MediaPlayer.create(this, link.toUri())

                mediaPlayer.setOnCompletionListener {
                    // Handle completion if needed
                }
                binding.seek.progress = 0
            }
            mediaPlayer.start()
            binding.play.setImageResource(R.drawable.baseline_pause_circle_outline_24)

            // Restart the handler to update the seekbar
            handler.postDelayed(runnable, 0)
        }else{
            mediaPlayer.pause()
            binding.play.setImageResource(R.drawable.baseline_play_circle_outline_24)
        }
    }

    private fun stop() {
        binding.play.setImageResource(R.drawable.baseline_play_circle_outline_24)
        mediaPlayer.stop()
        mediaPlayer.reset()
        handler.removeCallbacks(runnable)
        binding.seek.progress = 0

    }
}