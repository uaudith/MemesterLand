package com.uaudith.memesterland

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var rvFeed: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MemesterLand)
        setContentView(R.layout.activity_main)

        val model: MainViewModel by viewModels()

        rvFeed = findViewById(R.id.rvFeed)
        swipeRefresh = findViewById(R.id.swiperefreshFeed)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val lf = model.lf

        val feedAdapter = RvImageAdapter(lf)
        rvFeed.adapter = feedAdapter
        rvFeed.layoutManager = LinearLayoutManager(this)
        lf.setCbOnFetch { startPos, size ->
            feedAdapter.notifyItemRangeInserted(startPos,size)
        }
        Log.i(TAG,"RecyclerView initiated")


        swipeRefresh.setOnRefreshListener {
            lf.shuffleItems()
            feedAdapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }

}