package allpo.cosplaystuff.activities.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.activities.AbstractActivity;
import allpo.cosplaystuff.activities.checklist.CheckListActivity;
import allpo.cosplaystuff.activities.gallery.GalleryActivity;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.DatabaseHelper;
import allpo.cosplaystuff.helpers.Utils.DialogHelper;
import allpo.cosplaystuff.helpers.Utils.ImageHelper;
import allpo.cosplaystuff.helpers.customComponent.AddImageDialog;
import allpo.cosplaystuff.helpers.customComponent.SquareRelativeLayout;

//import allpo.cosplaystuff.activities.checklist.CheckListActivity;

/**
 * Created by Allpo on 17/08/2014.
 */
public class ProjectActivity extends AbstractActivity implements AddImageDialog.AddImageDialogListener {

    public static final String TAG = "ProjectActivity";

    private File mFile;

    private CosplayProject mCosplayProject;

    private EditText mProjectName;

    private ImageView mHeaderPic;

    private RelativeLayout mHeaderContainer;

    private AddImageDialog mAddImageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project);

        init();
    }

    private void init() {

        mProjectName = (EditText) findViewById(R.id.edit_project_name);
        mHeaderPic = (ImageView) findViewById(R.id.edit_project_header_pic);
        mHeaderContainer = (RelativeLayout) findViewById(R.id.edit_project_header_container);

        //Force edit text to have focus and single line
        mProjectName.setSingleLine(true);
        mProjectName.requestFocus();

        mCosplayProject = (CosplayProject) getIntent().getSerializableExtra(Constant.INTENT_PROJECT);

        if (mCosplayProject == null) {
            //initialize the project
            mCosplayProject = new CosplayProject();
        } else {
            displayLoader();
            //It means there is already a project, we need to load the info
            if (mCosplayProject.getName() != null) {
                mProjectName.setText(mCosplayProject.getName());
            }

            if (mCosplayProject.getHeaderPicPath() != null) {
                //Launch the AsyncTask that will display the picture
                new loaderHeaderTask().execute();
            }
        }

        //Save project Button
        Button saveButton = (Button) findViewById(R.id.edit_project_save_project);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verification if the project name is null
                if (!mProjectName.getText().toString().equals("")) {
                    mCosplayProject.setName(mProjectName.getText().toString());

                    // Save the project in the database
                    DatabaseHelper.getInstance().saveCosplayProject(mCosplayProject);

                    // Close the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mProjectName.getWindowToken(), 0);

                    onBackPressed();

                }
            }
        });

        SquareRelativeLayout refButton = (SquareRelativeLayout) findViewById(R.id.edit_project_ref_button);
        refButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, GalleryActivity.class);
                intent.putExtra(Constant.INTENT_PROJECT, mCosplayProject);
                intent.putExtra(Constant.INTENT_GALLERY_TYPE, Constant.REF_LIST);
                startActivity(intent);
            }
        });

        SquareRelativeLayout wipButton = (SquareRelativeLayout) findViewById(R.id.edit_project_wip_button);
        wipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, GalleryActivity.class);
                intent.putExtra(Constant.INTENT_PROJECT, mCosplayProject);
                intent.putExtra(Constant.INTENT_GALLERY_TYPE, Constant.WIP_LIST);
                startActivity(intent);
            }
        });

        SquareRelativeLayout checkButton = (SquareRelativeLayout) findViewById(R.id.edit_project_check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, CheckListActivity.class);
                intent.putExtra(Constant.INTENT_PROJECT, mCosplayProject);
                startActivity(intent);
            }
        });

        mHeaderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddImagePopup();
            }
        });

        mHeaderContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the header pic is visible
                openAddImagePopup();
            }
        });
    }

    /**
     * Popup to get Camera or Gallery image
     */
    private void openAddImagePopup() {
        mAddImageDialog = DialogHelper.getAddImageDialog(this);
        mAddImageDialog.addListener(this);
        mAddImageDialog.show();

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

        TreatIncomingPictureTask task = new TreatIncomingPictureTask();
        task.data = data;
        task.resultCode = resultCode;
        task.requestCode = requestCode;

        task.execute();
    }

    public void setHeaderPicture(Bitmap headerPicture) {

        //Save the picture in local storage & save the path in the project
        String imgPath = ImageHelper.saveBitmapAndGetPath(this.getApplicationContext(), headerPicture);
        mCosplayProject.setHeaderPicPath(imgPath);
        DatabaseHelper.getInstance().saveCosplayProject(mCosplayProject);

        setBitmapToHeader(ImageHelper.createPreviewImage(headerPicture));
    }

    public void setBitmapToHeader(Bitmap image) {

        //Visual stuff to display the image container and hide the picker
        mHeaderPic.setImageBitmap(image);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mHeaderPic.startAnimation(myFadeInAnimation);
        mHeaderPic.setVisibility(View.VISIBLE);
    }

    @Override
    public void clickOnCamera() {
        getCameraPicture();
    }

    @Override
    public void clickOnGallery() {
        getGalleryPicture();
    }

    private class TreatIncomingPictureTask extends AsyncTask<Void, Void, Void>{

        public int resultCode;
        public int requestCode;
        public Intent data;
        private Bitmap img;

        @Override
        protected Void doInBackground(Void... params) {
            if (resultCode == Activity.RESULT_OK) {
                //It means that mFile got the picture
                if (requestCode == Constant.REQUEST_IMAGE_CAPTURE) {
                    img = ImageHelper.getBitmapFromFile(mFile);
                }
                if (requestCode == Constant.REQUEST_IMAGE_GALLERY) {
                    Uri selectedImageUri = data.getData();
                    img = ImageHelper.getBitmapFromUri(ProjectActivity.this, selectedImageUri);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setHeaderPicture(img);
        }
    }

    private class loaderHeaderTask extends AsyncTask<Void, Void, Void> {

        Bitmap existingHeader;

        @Override
        protected Void doInBackground(Void... params) {
            existingHeader = ImageHelper.getBitmapFromPath(mCosplayProject.getHeaderPicPath());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (existingHeader != null && mHeaderPic != null) {
                setBitmapToHeader(existingHeader);
                //Todo : Hide the fragment loader
                hideLoader();
            }
        }
    }
}
