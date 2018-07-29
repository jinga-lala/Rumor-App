package com.hfad.layoutprac;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.hfad.layoutprac.Database.DatabaseHandler;


public class Uploadimg extends AppCompatActivity {
    DatabaseHandler db;
    Activity activity = this;
    private Button btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private  String strImagePath="";
    private int GALLERY = 1, CAMERA = 2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    List<Info>data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadimg);

        btn = (Button) findViewById(R.id.btn);
        imageview = (ImageView) findViewById(R.id.iv);
        if (check_permission()) {// checking permission for phone.whenever we have to access db in the phone taki iska koi b file phone may write kr ske
            db = new DatabaseHandler(this);
        }
        data=db.getInfoList();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    strImagePath = saveImage(bitmap);
                    Log.d("test#000","imagegalary#000"+strImagePath);

                    if(!(strImagePath.equals(""))){
                        //imsert code here
                        Log.d("test#000","imagegalary#000data is dre");
                        Info in=new Info();
                        in.setPathImagename(strImagePath);
                        db.InsertInfo(in);
                        Toast.makeText(Uploadimg.this, "Image inserted in datadase!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Uploadimg.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bitmap);
                    Intent i=new Intent(Uploadimg.this,MainActivity.class);
                    startActivity(i);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Uploadimg.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            strImagePath= saveImage(thumbnail);
            Log.d("test#000","imagecamera"+strImagePath);
            if(!(strImagePath.equals(""))){
                //imsert code here
                Log.d("test#000","dataa is dre");
                Info in=new Info();
                in.setPathImagename(strImagePath);
                db.InsertInfo(in);

            }
            Toast.makeText(Uploadimg.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Uploadimg.this,MainActivity.class);
            startActivity(i);
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG#000", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    boolean check_permission() {

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            requestPermission();
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Write External Storage permission allows Reckon to save data l" +
                    "ocally. Please allow in App Settings for better offline experience .")
                    .setCancelable(false)

                    .setNegativeButton("Enable Access",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                                }
                            });

            // Creating dialog box
            AlertDialog alert = builder.create();
            alert.show();

        } else {

            ActivityCompat.requestPermissions(Uploadimg.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
}