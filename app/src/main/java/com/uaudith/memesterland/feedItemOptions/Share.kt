package com.uaudith.memesterland.feedItemOptions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer


class Share(urlAt: String, private val context: Context) {

    init {
        Glide.with(context)
            .load(urlAt)
            .into(object :CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    lateinit var imageFile: File
                    if(resource is BitmapDrawable){
                        imageFile = getTempFile(".png")
                        val fos = FileOutputStream(imageFile)
                        resource.bitmap.compress(
                            Bitmap.CompressFormat.PNG, 100, fos)
                        fos.close()
                    }else if (resource is GifDrawable){
                        imageFile = getTempFile(".gif")
                        val byteBuffer = resource.buffer

                        val output = FileOutputStream(imageFile)
                        val bytes = ByteArray(byteBuffer.capacity())
                        (byteBuffer.duplicate().clear() as ByteBuffer).get(bytes)
                        output.write(bytes, 0 ,bytes.size)
                        output.close()

                    }
                    val uriToShare = FileProvider.getUriForFile(context,"com.uaudith.memesterland.fileprovider",
                        imageFile)
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(uriToShare, context.contentResolver.getType(uriToShare))
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uriToShare as Parcelable?)
                    startActivity(context,Intent.createChooser(shareIntent, "Choose an app"),null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

            })

    }

    private fun getTempFile(type: String): File {
        val outputDir: File = context.cacheDir // context being the Activity pointer
        return File.createTempFile("tmpImage", type, outputDir)
    }
}