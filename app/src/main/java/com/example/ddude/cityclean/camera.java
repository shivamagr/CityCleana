package com.example.ddude.cityclean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class camera extends Activity {
   String value,issue;
    DbHandler dbHandler;
    String test="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Bundle read=getIntent().getExtras();
        value=read.getString("message");
        issue=read.getString("gen");

        dbHandler=new DbHandler(this,null,null,1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1880;
    public  Uri fileUri;

    public static final int MEDIA_TYPE_IMAGE=1;
    //String imageFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/picture.jpg";
    public void openGallery(View view){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent,MEDIA_TYPE_IMAGE);
    }



    public  void clickImage(View view){
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        //File imageFile = new File(imageFilePath);
        //Uri imageFileUri=Uri.fromFile(imageFile);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    public static  Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }
    public static File mediaFile;
    public static  File getOutputMediaFile(int type)
    {
        //Environment.getExternalStorageState()
        File mediaStorageDir= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyCameraApp");

        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        if(type==MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            return mediaFile;
        }
        else    return null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                Bitmap bitmap = BitmapFactory.decodeFile(mediaFile.toString());
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(this, "you are in camera activity atleast",
                        Toast.LENGTH_LONG).show();
                //fileUri = data.getData();
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                */
            }
            // When an Image is picked
            else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                fileUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private static int RESULT_LOAD_IMG = 1;
    public void sendMail(View view){

        /*************/
        String[] str=value.split("~");
        String name=str[1];
        String location=str[3];
        String desc=str[5];
        /***************/

        PastIssue pastIssue=new PastIssue(desc,location,name);
        dbHandler.EnterIssue(pastIssue);

        String email="shivam@am.ism.ac.in";
        String emailAddress[]={email};
        // Uri selectedImageUri = data.getData();
        //selectedImagePath = getRealPathFromURI(selectedImageUri);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,emailAddress);
        emailIntent.putExtra(Intent.EXTRA_TEXT,value);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Regarding the issue of - "+issue);
        emailIntent.putExtra(Intent.EXTRA_STREAM,fileUri);
        emailIntent.setType("image/*");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.putExtra("exit_on_sent", true);
        startActivity(emailIntent);

    }


}
