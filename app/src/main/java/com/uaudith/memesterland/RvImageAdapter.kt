package com.uaudith.memesterland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uaudith.memesterland.factory.LinksFactory

class RvImageAdapter(private val dataSet: LinksFactory) :
    RecyclerView.Adapter<RvImageAdapter.FeedViewHolder>() {
    inner class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgView = view.findViewById<ImageView>(R.id.feedItem_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)

    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        Glide.with(holder.view)
            .load(dataSet.getUrlAt(position))
            .placeholder(R.drawable.ic_launcher_foreground)
            .dontAnimate()
            .into(holder.imgView);
    }

    override fun getItemCount(): Int {
        return dataSet.getTotalCount()
    }
}