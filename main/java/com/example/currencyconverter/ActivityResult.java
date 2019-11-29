package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ActivityResult extends AppCompatActivity {
    EditText textCurrTo, textCurrFrom;
    TextView textTo, textFrom, textView;
    ImageButton button;
    Button buttonsvd;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        textCurrFrom = findViewById(R.id.txt_from);
        textCurrTo = findViewById(R.id.txt_to);
        textFrom = findViewById(R.id.fromcur);
        textTo = findViewById(R.id.tocurrency);
        textView = findViewById(R.id.text);
        button = findViewById(R.id.btn_img);
        buttonsvd = findViewById(R.id.btn_save);
        imageView = findViewById(R.id.invoice_image);
        Intent intent = getIntent();

        String srt = intent.getStringExtra("toc");
        textCurrTo.setText(srt);
        String sr = intent.getStringExtra("fromc");
        textCurrFrom.setText(sr);
        String srs = intent.getStringExtra("fromcn");
        textFrom.setText(srs);
        String d = intent.getStringExtra("tomcn");
        textTo.setText(d);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.GONE);
                jump();
            }
        });


        buttonsvd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.GONE);
                jump();
            }
        });
    }

    private void jump() {
        Intent intent = getIntent();
        String srt = intent.getStringExtra("toc");
        String sr = intent.getStringExtra("fromc");
        String srs = intent.getStringExtra("fromcn");
        String d = intent.getStringExtra("tomcn");

        Intent intents = new Intent(ActivityResult.this, ImageResutActivity.class);
        intents.putExtra("toc", srt);
        intents.putExtra("fromc", sr);
        intents.putExtra("fromcn", srs);
        intents.putExtra("tomcn", d);

        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        ActivityResult.this.finish();


    }


}

