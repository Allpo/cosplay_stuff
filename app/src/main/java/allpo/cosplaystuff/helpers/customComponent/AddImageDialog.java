package allpo.cosplaystuff.helpers.customComponent;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.R;

/**
 * Created by Allpo on 10/08/2014.
 */
public class AddImageDialog extends CSDialog {

    List<AddImageDialogListener> listeners;

    ImageView mCameraButton;

    ImageView mGalleryButton;

    public AddImageDialog(Context context) {
        super(context);
    }

    public AddImageDialog(Context context, int layoutId){
        super(context, layoutId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        mCameraButton = (ImageView) findViewById(R.id.popup_add_camera_button);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddImageDialogListener listener : listeners) {
                    listener.clickOnCamera();
                }
            }
        });
        mGalleryButton = (ImageView) findViewById(R.id.popup_add_gallery_button);
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddImageDialogListener listener : listeners) {
                    listener.clickOnGallery();
                }
            }
        });
    }

    public void addListener(AddImageDialogListener listener){
        if (listeners == null){
            listeners = new ArrayList<AddImageDialogListener>();
        }

        if (listeners != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(AddImageDialogListener listener){
        if (listeners != null){
            listeners.remove(listener);
        }
    }

    public interface AddImageDialogListener{
        public void clickOnCamera();

        public void clickOnGallery();
    }
}
