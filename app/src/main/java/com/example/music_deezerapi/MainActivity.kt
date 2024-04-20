package com.example.music_deezerapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_deezerapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var rView : RecyclerView
    private lateinit var binding : ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        fetchData(retrofit,"taylor swift")

        binding.imageButton.setOnClickListener{
            val text = binding.etID.text.toString()
            fetchData(retrofit,text)
        }

    }

    private fun fetchData(retrofit:ApiInterface,s: String) {

        val retrofitData = retrofit.getData(s)

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                val dataList = p1.body()?.data!!

                myAdapter = MyAdapter(this@MainActivity,dataList)
                rView = binding.recView
                rView.layoutManager = LinearLayoutManager(this@MainActivity)
                rView.adapter = myAdapter

                myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        //on clicking each item, what action do you want to perform
                        val intent = Intent(this@MainActivity , EachMusic::class.java)
                        intent.putExtra("title",dataList[position].title)
                        intent.putExtra("image",dataList[position].album.cover_big)
                        intent.putExtra("link",dataList[position].preview)
                        startActivity(intent)
                    }

                })

            }

            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                Log.d("Error","onFailure"+p1.message)
            }
        })

    }
}