package com.uaudith.memesterland

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.uaudith.memesterland.factory.LinksFactory


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var rvFeed: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFeed = findViewById(R.id.rvFeed)
        swipeRefresh = findViewById(R.id.swiperefreshFeed)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        val lf = LinksFactory()
        lf.addSourceLink("https://www.reddit.com/r/dankmemes/.json?limit=100")
        val feedAdapter = RvImageAdapter(lf)
        rvFeed.adapter = feedAdapter
        rvFeed.layoutManager = LinearLayoutManager(this)
        lf.addCallbackAfterSourceAdded { feedAdapter.notifyDataSetChanged() }
        Log.i(TAG,"RecyclerView initiated")

        swipeRefresh.setOnRefreshListener {
            lf.shuffleItems()
            feedAdapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }

}