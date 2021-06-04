package com.uaudith.memesterland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uaudith.memesterland.factory.LinksFactory
const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    lateinit var rvFeed: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFeed = findViewById(R.id.rvFeed)

        val lf = LinksFactory()
        lf.addSourceLink("https://www.reddit.com/r/dankmemes/.json?limit=100")
        val feedAdapter = RvImageAdapter(lf)
        rvFeed.adapter = feedAdapter
        rvFeed.layoutManager = LinearLayoutManager(this)
        lf.addCallbackAfterSourceAdded { feedAdapter.notifyDataSetChanged() }
        Log.i(TAG,"RecyclerView initiated")
    }
}