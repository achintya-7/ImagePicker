package com.example.imagepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class onClicked_fullscreen extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_clicked_fullscreen);
        imageView = findViewById(R.id.image);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            int selectedImage = intent.getIntExtra("image", 0);
            imageView.setImageResource(selectedImage);
        }



    }
}