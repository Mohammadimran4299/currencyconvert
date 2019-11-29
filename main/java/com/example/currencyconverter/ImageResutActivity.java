package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ImageResutActivity extends AppCompatActivity {

    EditText editText1, editText2;
    TextView textView1, textView2;
    Button button;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    private Uri sharePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_resut);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        editText1 = findViewById(R.id.edit1);
        editText2 = findViewById(R.id.edit2);
        textView1 = findViewById(R.id.txt1);
        textView2 = findViewById(R.id.txt2);
        button = findViewById(R.id.btn_home);
        imageView = findViewById(R.id.result_image);
        final Intent intent = getIntent();

        String srt = intent.getStringExtra("toc");
        editText2.setText(srt);

        String sr = intent.getStringExtra("fromc");
        editText1.setText(sr);

        String srs = intent.getStringExtra("fromcn");
        textView1.setText(srs);

        String d = intent.getStringExtra("tomcn");
        textView2.setText(d);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                takeScreenshot();
            }
        }, 1000);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ImageResutActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + "scrn" + ".jpeg";
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            String filePath = imageFile.getPath();
            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(ssbitmap);
            String sharePatha = filePath;
            File file = new File(sharePatha);
            Uri uri = Uri.fromFile(file);
            sharePath = uri;
            uploadFile(sharePath);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private void uploadFile(Uri mpaths) {
        if (mpaths != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(mpaths));
            uploadTask = fileReference.putFile(mpaths);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ImageResutActivity.this, "sss", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ImageResutActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ImageResutActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ImageResutActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
