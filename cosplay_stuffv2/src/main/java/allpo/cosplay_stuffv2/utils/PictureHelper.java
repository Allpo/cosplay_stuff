package allpo.cosplay_stuffv2.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allpo on 25/06/2015.
 */
public class PictureHelper {

    public static final int REQUEST_TAKE_PICTURE_ACTIVITY = 4242;
    public static final int REQUEST_GET_GALLERY_PICTURE_ACTIVITY = 4243;
    public static final int REQUEST_TAKE_PICTURE_FRAGMENT = 4244;
    public static final int REQUEST_GET_GALLERY_PICTURE_FRAGMENT = 4245;

    private static final String PICTURE_FOLDER = "CosplayStuff/";

    public static File createPictureFile() throws IllegalAccessException {
        File image = null;
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "CosplayStuff_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalAccessException("Could not create picture file");
        }

        return image;
    }

    public static void addImageToGallery(Context context, String picturePath) {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, picturePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static void launchTakePicture(Activity activity, File imageFile, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

        activity.startActivityForResult(takePictureIntent, requestCode);
    }

    public static void launchGetGalleryPicture(Activity activity, int requestCode) {
        Intent getGalleryImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (getGalleryImageIntent.resolveActivity(activity.getPackageManager()) != null) {

            getGalleryImageIntent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(getGalleryImageIntent, "Select File"), requestCode);
        }
    }
}
