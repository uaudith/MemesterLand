package com.uaudith.memesterland
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(sharedPreferences.getBoolean("darkMode", true))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val linksFactory = model.lfMain
        val feedAdapter = RvImageAdapter(linksFactory)
        linksFactory.setCbOnFetch { startPos, size ->
            feedAdapter.notifyItemRangeInserted(startPos, size)
        }

        val pageData = listOf(
            ViewPagerItemData(feedAdapter, "dank"),
            ViewPagerItemData(RvImageAdapter(model.lfFav), "green")
        )

        viewPager2.adapter = ViewPagerAdapter(pageData)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = pageData[position].tabName
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.settingsFrame -> {
                Log.i(TAG,"clicked settings")
                startActivity(Intent(this,SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}