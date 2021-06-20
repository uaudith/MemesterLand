package com.uaudith.memesterland.helpers


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.uaudith.memesterland.R


fun popupImage(context: Context, urlAt: String) {
    val myPopup = Dialog(context,R.style.ThemeOverlay_MaterialComponents_Dialog_Alert )
    myPopup.setContentView(R.layout.popup_layout)
    val popupImgView = myPopup.findViewById<ImageView>(R.id.popupImgV)
    myPopup.show()
    Glide.with(context)
        .load(urlAt)
        .into(popupImgView)
    myPopup.apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}
