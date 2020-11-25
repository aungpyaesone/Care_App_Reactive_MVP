
package com.aungpyaesone.patient.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.aungpyaesone.patient.R
import com.bumptech.glide.Glide
import java.io.ByteArrayInputStream


fun String.convertToBitMap():Bitmap?{
    return try{
        val byte = Base64.decode(this,Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(byte))
        bitmap
    }catch (e: Exception){
        e.message
        null
    }
}

fun ImageView.load(uri: Uri){
    Glide.with(context)
        .asBitmap()
        .load(uri)
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_insert_photo_24)
        .into(this)
}

//fun MutableMap<String,Any>?.convertRestaurantVO(documentId:String): RestaurantVO{
//    val restaurantVO = RestaurantVO()
//    restaurantVO.id = this?.get("id") as String
//    restaurantVO.name = this["name"] as String
//    restaurantVO.imageurl = this["imgurl"] as String
//    restaurantVO.documentId = documentId
//
//    return  restaurantVO
//}

fun ProgressBar.showHide(value: Boolean){
    if(value) this.visibility = View.VISIBLE else this.visibility = View.GONE
}





