package com.example.imagepicker;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyApplication globalVariable = new MyApplication();

    GridView gridView;
    Button button_mark;
    Button button_upload;
    Boolean isMark = false;
    ImageView img;
    ImageView imgMark;
    TextView textCount;



    ArrayList<ImageCraft> myImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckUserPermissions();

        img = findViewById(R.id.imageCraft);
        imgMark = findViewById(R.id.wrongLabel);
        textCount = findViewById(R.id.imageCount);
        gridView = findViewById(R.id.gridView);
        button_mark = findViewById(R.id.button1);
        button_upload = findViewById(R.id.button2);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<ImageCraft> images = getData();
                ImageCraft selectedImage = images.get(i);
                startActivity(new Intent(MainActivity.this, onClicked_fullscreen.class).putExtra("image", selectedImage.getUri().toString()));
                return true;
            }
        });




        button_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVariable.choice == null || globalVariable.choice == false){
                    globalVariable.choice = true;
                }
                else {
                    globalVariable.choice = false;
                }

            }
        });

    }

    void CheckUserPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        gridView.setAdapter(new CustomAdapter(MainActivity.this, getData()));

    }


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gridView.setAdapter(new CustomAdapter(MainActivity.this, getData()));  // init the contact list
                    myImageFile = getData();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private ArrayList<ImageCraft> getData(){
        ArrayList<ImageCraft> imageCrafts = new ArrayList<ImageCraft>();
        File cameraFolder = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        ImageCraft imgCraft;

        if(cameraFolder.exists()){
            File[] files = cameraFolder.listFiles();

            for (int i = 0; i < files.length; i++) {

                File file = files[i];

                imgCraft = new ImageCraft();
                imgCraft.setUri(Uri.fromFile(file));

                imageCrafts.add(imgCraft);

            }
        }
        return imageCrafts;
    }





    class CustomAdapter extends BaseAdapter {

        ArrayList<Integer> marks_remove = new ArrayList();
        ArrayList<Integer> marks_upload = new ArrayList<>();

        int count = 0;

        Context c;
        ArrayList<ImageCraft> imageCrafts;

        public CustomAdapter(Context c, ArrayList<ImageCraft> imageCrafts) {
            this.c = c;
            this.imageCrafts = imageCrafts;
        }


        @Override
        public int getCount() {
            return imageCrafts.size();
        }

        @Override
        public Object getItem(int i) {
            return imageCrafts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(c).inflate(R.layout.row_items, viewGroup, false);
            }

            ImageCraft imgCraft = (ImageCraft) this.getItem(i);



            ImageView img = view.findViewById(R.id.imageCraft);
            ImageView marker = view.findViewById(R.id.wrongLabel);
            TextView imgCount = view.findViewById(R.id.imageCount);

            Picasso
                    .get()
                    .load(imgCraft.getUri())
                    .placeholder(R.drawable.ic_launcher_background)
                    .resize(300, 300)
                    .into(img);

            imgCount.setText(String.valueOf(count));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(globalVariable.choice){
                        if (marker.getVisibility() == View.INVISIBLE) {
                            marker.setVisibility(View.VISIBLE);
                            marks_remove.add(i);
                            img.setColorFilter(Color.argb(150,200,200,200));
                        }
                        else if(imgCount.isShown()){
                            imgCount.setVisibility(View.INVISIBLE);
                            marks_upload.remove(marks_upload.indexOf(i));
                            System.out.println("IDs are" + marks_upload );
                            count--;
                            gridView.invalidateViews();
                            marker.setVisibility(View.VISIBLE);
                            marks_remove.add(i);
                            img.setColorFilter(Color.argb(150,200,200,200));
                            gridView.invalidateViews();
                        }
                        else {
                            marker.setVisibility(View.INVISIBLE);
                            img.setColorFilter(null);
                            marks_remove.remove(marks_remove.indexOf(i));
                        }

                    }

                    else {
                        if (imgCount.isShown()){
                            imgCount.setVisibility(View.INVISIBLE);
                            marks_upload.remove(marks_upload.indexOf(i));
                            System.out.println("IDs are" + marks_upload );
                            count--;
                            gridView.invalidateViews();
                        }
                        else if(marker.getVisibility() == View.VISIBLE)
                        {
                            Toast.makeText(MainActivity.this, "Marked for upload", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            imgCount.setVisibility(View.VISIBLE);
                            marks_upload.add(i);
                            System.out.println("IDs are" + marks_upload );
                            count++;
                            imgCount.setText(String.valueOf(count));
                            gridView.invalidateViews();
//                            gridView.setAdapter(new CustomAdapter(view.getContext(), getData()));
                        }
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return false;
                }
            });

            return view;
        }
    }
}

