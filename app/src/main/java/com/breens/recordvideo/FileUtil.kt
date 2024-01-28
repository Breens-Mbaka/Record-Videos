package com.breens.recordvideo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createVideoFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val dir = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES)

    return File.createTempFile(
        "VIDEO_${timeStamp}_",
        ".mp4",
        dir
    )
}

fun File.getUri(context: Context): Uri? {
    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".fileprovider",
        this
    )
}

fun Uri.openVideoPlayer(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(this, "video/*")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No video player found", Toast.LENGTH_LONG).show()
    }
}