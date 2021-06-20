package com.uaudith.memesterland

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uaudith.memesterland.entities.ViewPagerItemData


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MemesterLand)
        setContentView(R.layout.activity_main)

        val model: MainViewModel by viewModels()


        toolbar = findViewById(R.id.toolbar)
        viewPager2 = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        setSupportActionBar(toolbar)

        val linksFactory = model.lfMain
        val feedAdapter = RvImageAdapter(linksFactory)
        linksFactory.setCbOnFetch { startPos, size ->
            feedAdapter.notifyItemRangeInserted(startPos,size)
        }

        val pageData = listOf(
            ViewPagerItemData(feedAdapter,"dank"),
            ViewPagerItemData(RvImageAdapter(model.lfFav),"green"))

        viewPager2.adapter = ViewPagerAdapter(pageData)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = pageData[position].tabName
        }.attach()

    }

}