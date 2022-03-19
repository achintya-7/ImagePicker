package com.example.imagepicker

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1){
                android.Manifest.permission.READ_EXTERNAL_STORAGE}, 121)
        }
        listImages()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 121 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            listImages();
        }
    }

    private fun listImages() {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val selection = MediaStore.Images.Media.BUCKET_ID + " = ?"
        val selectionArgs = arrayOf(CAMERA_IMAGE_BUCKET_ID)
        var cols = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()
        var rs = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null)!!

        gridview.adapter



    }

    inner class ImageAdapter : BaseAdapter {

        lateinit var context: Context
        constructor(context: Context)

        override fun getCount(): Int {
            return  rs.count
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            var iv = ImageView(context)
            rs.moveToPosition(p0)
            var path = rs.getString(0)
            var bitmap = BitmapFactory.decodeFile(path)
            iv.layoutParams = AbsListView.LayoutParams(300, 300)
            return iv;
        }

    }


    fun getBucketId(path: String): String {
        return path.lowercase().hashCode().toString()
    }
}

