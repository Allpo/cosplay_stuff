package allpo.cosplay_stuffv2.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Allpo on 28/06/2015.
 */
public class Utils {
    public static String getRealPathFromURI(Context context, Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public static final String NULL_MESSAGE = "Object should not be null";

    public static void checkNonNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(NULL_MESSAGE);
        }
    }
}
