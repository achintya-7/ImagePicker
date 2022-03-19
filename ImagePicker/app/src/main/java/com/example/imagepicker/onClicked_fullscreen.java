package com.example.imagepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class onClicked_fullscreen extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_clicked_fullscreen);
        imageView = findViewById(R.id.image);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            String selectedImageString = intent.getStringExtra("image");
            Toast.makeText(this, selectedImageString, Toast.LENGTH_SHORT).show();
            Picasso.get().load(Uri.parse(selectedImageString)).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }



    }
}