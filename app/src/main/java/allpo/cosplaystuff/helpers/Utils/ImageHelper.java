package allpo.cosplaystuff.helpers.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import allpo.cosplaystuff.R;

/**
 * Created by Allpo on 06/08/2014.
 */
public class ImageHelper {

    public static String TAG = "ImageHelper";

    /**
     * Will create a rounded version of the parameter image
     */
    public static Bitmap createRoundImage(Bitmap loadedImage) {
        System.out.println("loadbitmap" + loadedImage);
        Bitmap rescale = createPreviewImage(loadedImage);
        Bitmap circleBitmap = Bitmap.createBitmap(rescale.getWidth(),
                rescale.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(rescale,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas c = new Canvas(circleBitmap);
        Float radius;
        // This will assure the circle is made of the
        // smaller side
        if (rescale.getWidth() > rescale.getHeight()){
            radius = rescale.getHeight() / 2f;//(5f / 2f);
        } else {
            radius = rescale.getWidth() / 2f;//(5f / 2f);
        }
        c.drawCircle(rescale.getWidth() / 2, rescale.getHeight() / 2,
                radius, paint);
        return circleBitmap;
    }

    /**
     * Will create a smaller version of the image
     * smallest size will be 150, other one will scale accordingly
     */
    public static Bitmap createPreviewImage(Bitmap loadedImage) {
        float height = loadedImage.getHeight();
        float width = loadedImage.getWidth();
        float scale;
        int newHeight = 150;
        int newWidth = 150;
        if (height > width){
            scale = height / width;
            newHeight *= scale;
        } else {
            scale = width / height;
            newWidth *= scale;
        }
        return Bitmap.createScaledBitmap(loadedImage, newWidth, newHeight, true);
    }

    /**
     * Will add the parameter image to the phone's gallery
     */
    public static void addImageToGallery(Bitmap image, Context ctx) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(ctx.getContentResolver(), image, "Title", null);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.parse(path));
        ctx.sendBroadcast(mediaScanIntent);
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    public static Bitmap getBitmapFromFile(File file){
        Bitmap bMap = BitmapFactory.decodeFile(Uri.fromFile(file).getPath());

        return bMap;
    }

    public static Bitmap getBitmapFromUri(Activity activity, Uri uri){
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(uri, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        Bitmap res = BitmapFactory.decodeFile(imagePath);

        return res;
    }

    public static Uri getUriFromBitmap(Context context, Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);

        return Uri.parse(path);
    }

    public static String getPathFromBitmap(Context context, Bitmap image){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Title", null);

        return path;
    }

    /**
     * Will save the parameter image into local storage and return the path to the file
     */
    public static String saveBitmapAndGetPath(Context context, Bitmap image){
//        ContextWrapper cw = new ContextWrapper(context);
        Random rand = new Random();
//        File directory = cw.getDir(context.getString(R.string.image_directory_name), Context.MODE_PRIVATE);
//        File mypath = new File(directory,"" + Math.abs(rand.nextLong()) + ".jpg");
//        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "" + rand.nextLong(), "CosplayStuffPicture");
//        Log.d(TAG, "saveBitmapAndGetPath : path of local storage is " + directory.getPath());

//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(mypath);
//            // Use the compress method on the BitMap object to write image to the OutputStream
//            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "saveBitmapAndGetPath : image path is " + mypath.getPath());
//        return mypath.getPath();
//        return Uri.parse(path).getPath();

        String path = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(path + "/" + context.getString(R.string.image_directory_name));
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        OutputStream outputStream = null;
        File file = new File(path + "/" + context.getString(R.string.image_directory_name), "" + Math.abs(rand.nextLong()) + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public static Bitmap getBitmapFromPath(String path){
        Bitmap res = null;
        try {
            Log.d(TAG, "getBitmapFromPath : path of file is " + path);
            res = BitmapFactory.decodeFile(path);
        } catch (Exception e){
            Log.d(TAG, "Could not create the picture, maybe it has been corrupted or deleted");
        }

        return res;
    }

    public static Uri getUriFromPath(String path){
        Uri uri = Uri.fromFile(new File(path));

        return uri;
    }
}
