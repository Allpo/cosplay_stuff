package allpo.cosplay_stuffv2.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.interfaces.PicturePickerActivity;
import allpo.cosplay_stuffv2.utils.PictureHelper;

/**
 * Created by Allpo on 27/06/2015.
 */
public class GetPicturePickerDialog extends DialogFragment implements View.OnClickListener {

    public static final String CALLING_ITEM = "GetPicturePickerDialog.CALLING_ITEM";
    public static final String REQUEST_CODE = "GetPicturePickerDialog.REQUEST_CODE";

    public static final int CALLING_ACTIVITY = 0;
    public static final int CALLING_FRAGMENT = 1;

    private File mFile;
    private Activity mActivity;
    private int mCallingItemType;

    private View mGallery;
    private View mCamera;

    public static GetPicturePickerDialog newInstance(int callingItemType) {
        GetPicturePickerDialog fragment = new GetPicturePickerDialog();
        fragment.setCancelable(true);

        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog);

        Bundle bundle = new Bundle();
        bundle.putInt(CALLING_ITEM, callingItemType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(CALLING_ITEM)) {
            throw new IllegalArgumentException("No requestCode given as argument");
        }

        mCallingItemType = getArguments().getInt(CALLING_ITEM);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof PicturePickerActivity) {
            mActivity = activity;
            mFile = ((PicturePickerActivity) activity).getPictureFile();
        } else {
            throw new IllegalStateException("Calling acitivty must be PicturePickerActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_picture_picker_dialog, container, false);

        mGallery = view.findViewById(R.id.picture_picker_gallery);
        mCamera = view.findViewById(R.id.picture_picker_camera);

        mGallery.setOnClickListener(this);
        mCamera.setOnClickListener(this);

        getDialog().setTitle(getString(R.string.picture_picker_title));

        return view;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        int requestCode;

        switch (viewId) {
            case R.id.picture_picker_camera:
                requestCode = PictureHelper.REQUEST_TAKE_PICTURE_ACTIVITY;
                if (mCallingItemType == CALLING_FRAGMENT) {
                    requestCode = PictureHelper.REQUEST_TAKE_PICTURE_FRAGMENT;
                }
                PictureHelper.launchTakePicture(mActivity, mFile, requestCode);
                break;
            case R.id.picture_picker_gallery:
                requestCode = PictureHelper.REQUEST_GET_GALLERY_PICTURE_ACTIVITY;
                if (mCallingItemType == CALLING_FRAGMENT) {
                    requestCode = PictureHelper.REQUEST_GET_GALLERY_PICTURE_FRAGMENT;
                }
                PictureHelper.launchGetGalleryPicture(mActivity, requestCode);
                break;
            default:
                throw new IllegalStateException("Unknown click on view " + v);
        }
        dismiss();
    }
}
