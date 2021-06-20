package com.uaudith.memesterland

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.uaudith.memesterland.entities.ViewPagerItemData

class ViewPagerAdapter(private val dataList: List<ViewPagerItemData>)
    : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rv: RecyclerView = itemView.findViewById(R.id.rvFeed)
        val swipeRefresh = itemView.findViewById<SwipeRefreshLayout>(R.id.refresh)
        val context: Context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val adapter = dataList[position].adapter
        holder.rv.adapter = adapter
        holder.rv.layoutManager = LinearLayoutManager(holder.context)
        val doRefresh = dataList[position].adapter::shuffleData

        holder.swipeRefresh.setOnRefreshListener {
            doRefresh()
            adapter.notifyDataSetChanged()
            holder.swipeRefresh.isRefreshing = false
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}