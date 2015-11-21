package allpo.cosplaystuff.helpers.Utils;

import android.app.Activity;
import android.view.Window;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.helpers.customComponent.AddImageDialog;

/**
 * Created by Allpo on 10/08/2014.
 */
public class DialogHelper {

    public static AddImageDialog getAddImageDialog(Activity activity){

        AddImageDialog addImageDialog = new AddImageDialog(activity);
        // Remove the title bar
        addImageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set the custom layout
        addImageDialog.setContentView(R.layout.popup_add_image);
        addImageDialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.rounded_border_defaul_color));

        return addImageDialog;
    }
}
