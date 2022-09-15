package com.yunushamod.android.criminalintent.helpers

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import kotlin.math.roundToInt

fun getScaledBitmap(path: String, destHeight: Int, destWidth: Int): Bitmap {
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    val srcWidth = options.outWidth
    val srcHeight = options.outHeight

    var inSampleSize = 1
    if(srcHeight > destHeight || srcWidth > destWidth){
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth
        val sampleScale = if(heightScale > widthScale) heightScale else widthScale
        inSampleSize = sampleScale.toDouble().roundToInt()
    }
    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeFile(path,options)
}

fun getScaledBitmap(path: String, activity: Activity): Bitmap{
    val size = Point()
    activity.windowManager.defaultDisplay.getSize(size)
    return getScaledBitmap(path, size.x, size.y)
}