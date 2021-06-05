package com.uaudith.memesterland

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.uaudith.memesterland.factory.LinksFactory
import com.uaudith.memesterland.feedItemOptions.Share

class RvImageAdapter(private val dataSet: LinksFactory) :
    RecyclerView.Adapter<RvImageAdapter.FeedViewHolder>() {
    inner class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById(R.id.feedItem_imageView)
        val progBar: ProgressBar = view.findViewById(R.id.progressBar)
        val likeBtn: ImageButton = view.findViewById(R.id.likeBtn)
        val shareBtn: ImageButton = view.findViewById(R.id.shareBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)

    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.progBar.visibility = View.VISIBLE
        holder.likeBtn.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(holder.view.context, R.anim.enlarge))
        }
        holder.shareBtn.setOnClickListener{
            Share(dataSet.getUrlAt(position), holder.view.context)
        }
        Glide.with(holder.view)
            .load(dataSet.getUrlAt(position))
            .placeholder(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .listener(glideListener(holder,position))
            .into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return dataSet.getTotalCount()
    }
    private fun glideListener(holder: FeedViewHolder, position: Int): RequestListener<Drawable> {
        return object :RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progBar.visibility = View.GONE
                dataSet.removeItem(position)
                notifyItemRemoved(position)
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progBar.visibility = View.GONE
                return false
            }

        }
    }
}