package allpo.cosplaystuff.activities.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.activities.AbstractActivity;
import allpo.cosplaystuff.datas.CosplayPicture;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.DatabaseHelper;
import allpo.cosplaystuff.helpers.Utils.DialogHelper;
import allpo.cosplaystuff.helpers.Utils.ImageHelper;
import allpo.cosplaystuff.helpers.customComponent.AddImageDialog;

/**
 * Created by Allpo on 17/08/2014.
 */
public class GalleryActivity extends AbstractActivity implements AddImageDialog.AddImageDialogListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "GalleryActivity";

    private List<CosplayPicture> mPicList;

    private String mTypeList;

    private GridView mGridView;

    private PictureGalleryAdapter mAdapter;

    private File mFile;

    private AddImageDialog mAddImageDialog;

    private CosplayProject mCurrentProject;

    List<View> mLongClickList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        //Get data from previous activity
        mCurrentProject = (CosplayProject) getIntent().getSerializableExtra(Constant.INTENT_PROJECT);
        mTypeList = (String) getIntent().getSerializableExtra(Constant.INTENT_GALLERY_TYPE);

        if (mTypeList != null && mCurrentProject != null) {
            if (mTypeList.equals(Constant.REF_LIST)) {
                mPicList = mCurrentProject.getRefList();
            } else {
                mPicList = mCurrentProject.getWipList();
            }
        }

        if (mPicList == null) {
            mPicList = new ArrayList<CosplayPicture>();
        }

        mGridView = (GridView) findViewById(R.id.picture_gallery_list);
        mAdapter = new PictureGalleryAdapter(this, R.layout.item_grid_list_picture, mPicList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemLongClickListener(this);
        mGridView.setOnItemClickListener(this);

        Button addPic = (Button) findViewById(R.id.picture_gallery_add_image);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddImageDialog = DialogHelper.getAddImageDialog(GalleryActivity.this);
                mAddImageDialog.addListener(GalleryActivity.this);
                mAddImageDialog.show();
            }
        });
    }

    //Get picture from the camera
    private void getCameraPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                mFile = ImageHelper.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
            startActivityForResult(takePictureIntent, Constant.REQUEST_IMAGE_CAPTURE);
        }
    }

    private void getGalleryPicture() {
        Intent getGalleryImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (getGalleryImageIntent.resolveActivity(getPackageManager()) != null) {

            try {
                mFile = ImageHelper.createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            getGalleryImageIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(getGalleryImageIntent, "Select File"), Constant.REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mAddImageDialog != null) {
            mAddImageDialog.dismiss();
            mAddImageDialog = null;
        }

        if (resultCode == Activity.RESULT_OK) {
            //It means that mFile got the picture
            if (requestCode == Constant.REQUEST_IMAGE_CAPTURE) {
                addImageToList(ImageHelper.getBitmapFromFile(mFile));
            }
            if (requestCode == Constant.REQUEST_IMAGE_GALLERY) {
                Uri selectedImageUri = data.getData();
                Bitmap image = ImageHelper.getBitmapFromUri(this, selectedImageUri);

                addImageToList(image);
            }
        }
    }

    private void addImageToList(Bitmap image) {
        CosplayPicture cosplayPicture = new CosplayPicture();
        cosplayPicture.setPath(ImageHelper.saveBitmapAndGetPath(this, image));
        if (mTypeList.equals(Constant.REF_LIST)) {
            mCurrentProject.addImageToRefList(cosplayPicture);
        } else {
            mCurrentProject.addImageToWipList(cosplayPicture);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickOnCamera() {
        getCameraPicture();
    }

    @Override
    public void clickOnGallery() {
        getGalleryPicture();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int visibility = view.findViewById(R.id.picture_gallery_item_delete).getVisibility();
        if (visibility == View.VISIBLE) {
            DatabaseHelper.getInstance().removeCosplayPicture(mPicList.get(position));
            mPicList.remove(position);
            mAdapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + mPicList.get(position).getPath()), "image/*");
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
        unSelectPictures();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int visibility = view.findViewById(R.id.picture_gallery_item_delete).getVisibility();
        if (visibility == View.VISIBLE) {
            view.findViewById(R.id.picture_gallery_item_delete).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.picture_gallery_item_delete).setVisibility(View.VISIBLE);
            if (mLongClickList == null) {
                mLongClickList = new ArrayList<View>();
            }
            unSelectPictures();
            mLongClickList.add(view);
        }
        return true;
    }

    private void unSelectPictures() {
        if (mLongClickList != null) {
            for (View view1 : mLongClickList) {
                view1.findViewById(R.id.picture_gallery_item_delete).setVisibility(View.GONE);
            }
            mLongClickList.clear();
        }
    }
}
