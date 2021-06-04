package com.uaudith.memesterland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uaudith.memesterland.factory.LinksFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lf = LinksFactory()
        lf.addSourceLink("https://www.reddit.com/r/dankmemes/.json?limit=10")
    }
}