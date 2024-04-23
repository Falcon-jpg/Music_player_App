package com.example.music_deezerapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewAdapter
    private lateinit var db : Database


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Database(requireContext())



        recyclerView = view.findViewById(R.id.recView1)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NewAdapter(requireContext(),db.getSongs())
        recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData(db.getSongs())
    }
}