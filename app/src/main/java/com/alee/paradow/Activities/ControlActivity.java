package com.alee.paradow.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alee.paradow.R;
import com.alee.paradow.databinding.ActivityControlBinding;
import com.alee.paradow.databinding.ActivityLoginBinding;
import com.alee.paradow.model.LoginRes;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;
import com.alee.paradow.network.RetrofitInstanceDevice;
import com.alee.paradow.utils.Links;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlActivity extends AppCompatActivity {

    public static final String TAG = "ControlActivity:";
    ActivityControlBinding binding;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isStoragePermissionGranted();


        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        downloadImage(image);

//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG, "Permission is granted");
//                //File write logic here
//            } else
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }

        //TODO(19) download image and convert it to bytes and upload
        //TODO(20) data is going to be the file path
        prepareImage();

        //TODO(18) add the other methods and connect all together
        binding.start.setOnClickListener(v -> control(1));
        binding.stop.setOnClickListener(v -> control(0));

        binding.left.setOnClickListener(v -> {
            //left in
            control(12);
            //right out
            control(15);
        });
        binding.right.setOnClickListener(v -> {
            //right in
            control(14);
            //left out
            control(13);
        });

        binding.up.setOnClickListener(v -> {
            control(12);
            control(14);
        });
        binding.down.setOnClickListener(v -> {
            control(13);
            control(15);
        });
    }

    private void prepareImage() {
        Log.i(TAG, Environment.getExternalStoragePublicDirectory("/Paradow")+"");
        Uri file_path = null;
        File folder = new File(Environment.getExternalStoragePublicDirectory("/Paradow")+"");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
            Log.i(TAG, String.valueOf(success));

            Toast.makeText(ControlActivity.this, "folder created please put the svg 1.svg in the folder ", Toast.LENGTH_SHORT).show();

        }
        if (success) {
            // Do something on success
            //todo download the image over here
            file_path = Uri.parse("file://"+Environment.getExternalStoragePublicDirectory("/Paradow")+"/1.svg");

        } else {
            // Do something else on failure
            Log.i("Response:", "Error");
        }

        try {

//            InputStream is = getContentResolver().openInputStream(data.getData());
            Log.i("Response:", String.valueOf(file_path));

            ContentResolver res = this.getContentResolver();
            InputStream is = res.openInputStream(file_path);

            Log.i("Response:", "Upload");

            uploadImage(getBytes(is));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ControlActivity.this, "File not in the right Path ", Toast.LENGTH_SHORT).show();

        }
    }


    private void control(int i) {


        DataService service = RetrofitInstanceDevice.getRetrofitInstance().create(DataService.class);
        Call<Void> call = service.device(i);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 400){
                    Log.i("Response:", "Error");
                }else if(response.code() == 200 ) {
                    Log.i("Response:", "success");
                }else
                    Log.i("Response:","Server have a problem");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("Failure:", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    void uploadImage(byte[] imageBytes) {

        DataService service = RetrofitInstanceDevice.getRetrofitInstance().create(DataService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/svg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("1", "1.svg", requestFile);
        Call<Void> call = service.uploadImage(body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Response:" , "success");
                } else {
                    Log.i("Response:","Error");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Control", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }



    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    void downloadImage(String image){

        File folder = new File(Environment.getExternalStoragePublicDirectory("/Android/data/com.alee.paradow/files/Paradow")+ "");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
            Log.i(TAG, "folder created "+success);
            Toast.makeText(ControlActivity.this, "folder created please put the svg 1.svg in the folder ", Toast.LENGTH_SHORT).show();
        }
        if (success){
            Log.i(TAG, ""+Links.IMAGE_BASE_URL + image);

            DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(Links.IMAGE_BASE_URL + image);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setTitle("PARADOW")
                    .setDescription("SVG Image for PARADOW");
            // in order for this if to run, you must use the android 3.2 to compile your app
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            request.setDestinationInExternalFilesDir(this,"/Paradow", "1.svg");

            mgr.enqueue(request);


//            try (BufferedInputStream inputStream = new BufferedInputStream(new URL(Links.IMAGE_BASE_URL + image).openStream());
//                 FileOutputStream fileOS = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + "Paradow/1.svg")) {
//                byte[] data = new byte[1024];
//                int byteContent;
//                while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
//                    fileOS.write(data, 0, byteContent);
//                }
//            } catch (IOException e) {
//                // handles IO exceptions
//            }
        }
//        if (!isStoragePermissionGranted()) {
//            return;
//        }
//
//        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/";
//
//        final File dir = new File(dirPath);
//
//        final String fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1);
//
//        Glide.with(this)
//                .load(imageURL)
//                .into(new CustomTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//
//                        Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
//                        Toast.makeText(ControlActivity.this, "Saving Image...", Toast.LENGTH_SHORT).show();
//                        saveImage(bitmap, dir, fileName);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                        super.onLoadFailed(errorDrawable);
//
//                        Toast.makeText(ControlActivity.this, "Failed to Download Image! Please try again later.", Toast.LENGTH_SHORT).show();
//                    }
//                });

    }

//    private void saveImage(Bitmap image, File storageDir, String imageFileName) {
//
//        boolean successDirCreated = false;
//        if (!storageDir.exists()) {
//            successDirCreated = storageDir.mkdir();
//        }
//        if (successDirCreated) {
//            File imageFile = new File(storageDir, imageFileName);
//            String savedImagePath = imageFile.getAbsolutePath();
//            try {
//                OutputStream fOut = new FileOutputStream(imageFile);
//                image.compress(Bitmap.CompressFormat.SVG, 100, fOut);
//                fOut.close();
//                Toast.makeText(ControlActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                Toast.makeText(ControlActivity.this, "Error while saving image!", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//
//        }else{
//            Toast.makeText(this, "Failed to make folder!", Toast.LENGTH_SHORT).show();
//        }
//    }

}
