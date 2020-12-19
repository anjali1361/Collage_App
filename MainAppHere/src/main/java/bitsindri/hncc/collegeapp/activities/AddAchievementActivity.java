package bitsindri.hncc.collegeapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import bitsindri.hncc.collegeapp.R;

public class AddAchievementActivity extends AppCompatActivity {

    Button add_achievement;
    Button add_achievement_button;
    ImageView achievement_image;
    public int PERMISSION_CODE = 1000;
    public byte[] selectedImageBytes;
    Bitmap selectedImageBmp = null;
    public  Boolean pictureJustChanged= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);

        add_achievement= findViewById(R.id.add_achievement);
        achievement_image = findViewById(R.id.achievement_image);
        add_achievement_button = findViewById(R.id.add_achievement_button);

        add_achievement.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                ) {
                    //permission not enabled ,request it
//                    val permission = arrayOf(
//                            android.Manifest.permission.CAMERA,
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    );

                    String[] permission = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    //permission already granted
                    openSource();

                }
            } else {
                // system os < marshmallow
                openSource();

            }
        });
    }

    private void openSource() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        startActivityForResult(
                Intent.createChooser(intent,"Select Picture"),
                0
        );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {

            if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //permission from popup is granted
                openSource();
            } else {
                //permission from popup is denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT)
                        .show();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @SuppressLint("CommitPrefEdits");
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//
//            // start picker to get image for cropping and then use the image in cropping activity
//
//            CropImage.activity()
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//
//        }
//        //to get the cropped image
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val selectedImagePath = result.uri
//                val selectedImageBmp =
//                        MediaStore.Images.Media.getBitmap(contentResolver, selectedImagePath)
//
//                val outputStream = ByteArrayOutputStream()
//                selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
//                selectedImageBytes = outputStream.toByteArray()
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//            }
//
//            GlideApp.with(this).load(selectedImageBytes).into(profile_pic)
//            pictureJustChanged = true
//
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            // start picker to get image for cropping and then use the image in cropping activity

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImagePath = result.getUri();

                try {
                    selectedImageBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                selectedImageBytes = outputStream.toByteArray();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

            achievement_image.setImageBitmap(selectedImageBmp);
            pictureJustChanged = true;

            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}