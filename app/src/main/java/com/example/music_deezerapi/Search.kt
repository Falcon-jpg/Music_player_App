import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_deezerapi.ApiInterface
import com.example.music_deezerapi.Database
import com.example.music_deezerapi.EachMusic
import com.example.music_deezerapi.Home
import com.example.music_deezerapi.LikedSong
import com.example.music_deezerapi.MyAdapter
import com.example.music_deezerapi.MyData
import com.example.music_deezerapi.databinding.SearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Search : Fragment(){

    private lateinit var binding: SearchBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var rView: RecyclerView
    private lateinit var database: Database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = SearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        fetchData(retrofit, "taylor swift")

        binding.imageButton.setOnClickListener {
            val text = binding.etID.text.toString()
            fetchData(retrofit, text)
        }


    }

    private fun fetchData(retrofit: ApiInterface, s: String) {
        val retrofitData = retrofit.getData(s)

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList = response.body()?.data!!

                database = Database(requireContext())
                myAdapter = MyAdapter(requireContext(), dataList,database)
                rView = binding.recView
                rView.layoutManager = LinearLayoutManager(this@Search.requireContext())
                rView.adapter = myAdapter

                myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        //on clicking each item, what action do you want to perform
                        val intent = Intent(this@Search.requireContext(), EachMusic::class.java)
                        intent.putExtra("title", dataList[position].title)
                        intent.putExtra("image", dataList[position].album.cover_big)
                        intent.putExtra("link", dataList[position].preview)
                        startActivity(intent)
                    }

                })
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Error", "onFailure" + t.message)
            }
        })
    }
}
