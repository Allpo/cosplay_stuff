package allpo.cosplay_stuffv2.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import java.io.File;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.interfaces.PicturePickerActivity;
import allpo.cosplay_stuffv2.adapters.ProjectPagerAdapter;
import allpo.cosplay_stuffv2.dialogs.GetPicturePickerDialog;
import allpo.cosplay_stuffv2.models.CosplayProject;
import allpo.cosplay_stuffv2.utils.PictureHelper;
import allpo.cosplay_stuffv2.utils.Utils;

/**
 * Created by Allpo on 20/06/2015.
 */
public class ProjectActivity extends CosplayActivity implements PicturePickerActivity, ViewPager.OnPageChangeListener {

    private static final String PROJECT_ATTRIBUTE = "ProjectActivity.PROJECT_ATTRIBUTE";

    private CosplayProject mProject;

    private ProjectPagerAdapter mAdaper;

    private ViewPager mViewPager;

    private PagerSlidingTabStrip mTabStrip;

    private Toolbar mToolbar;

    private ImageView mHeader;

    private File mIntentPictureFile;

    /**
     * Start the activity with an existing project
     *
     * @param context
     * @param project used to open the activity
     */
    public static void startForProject(Context context, CosplayProject project) {
        if (project == null)
            throw new IllegalArgumentException("startForProject() with an invalid project");

        Intent intent = new Intent(context, ProjectActivity.class);
        intent.putExtra(PROJECT_ATTRIBUTE, project.getId());

        context.startActivity(intent);
    }

    /**
     * Start the activity to create a new project
     *
     * @param context
     */
    public static void startNewProject(Context context) {
        Intent intent = new Intent(context, ProjectActivity.class);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project);

        if (savedInstanceState != null && savedInstanceState.containsKey(PROJECT_ATTRIBUTE)) {
            mProject = CosplayProject.findById(CosplayProject.class, savedInstanceState.getLong(PROJECT_ATTRIBUTE));
        } else {
            //Get argument and set project
            Bundle args = getIntent().getExtras();

            if (args != null && args.containsKey(PROJECT_ATTRIBUTE)) {
                //Project is present, fill the activity field with it
                mProject = CosplayProject.findById(CosplayProject.class, args.getLong(PROJECT_ATTRIBUTE));

                if (mProject == null)
                    throw new IllegalArgumentException("startForProject() with an invalid project");
            } else {
                //No project as arguments, create a new project
                mProject = new CosplayProject();
                mProject.save();
            }
        }

        initUI();
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.project_viewpager);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.project_tabstrip);
        mToolbar = (Toolbar) findViewById(R.id.project_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdaper = new ProjectPagerAdapter(this, getSupportFragmentManager(), mProject);
        mViewPager.setAdapter(mAdaper);
        mTabStrip.setViewPager(mViewPager);

        mHeader = (ImageView) findViewById(R.id.project_add_picture);
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mIntentPictureFile = PictureHelper.createPictureFile();
                    GetPicturePickerDialog dialog = GetPicturePickerDialog.newInstance(GetPicturePickerDialog.CALLING_ACTIVITY);
                    dialog.show(getFragmentManager(), GetPicturePickerDialog.REQUEST_CODE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        if (mProject.getPicture() != null && !mProject.getPicture().isEmpty()) {
            Picasso.with(ProjectActivity.this)
                    .load(mProject.getPicture())
                    .into(mHeader);
            findViewById(R.id.project_header_add_icon).setVisibility(View.GONE);
        }
        mTabStrip.setOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_remove:
                mProject.delete();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProject() {
        mProject.save();
    }

    public CosplayProject getProject() {
        return mProject;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(PROJECT_ATTRIBUTE, mProject.getId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveProject();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureHelper.REQUEST_TAKE_PICTURE_ACTIVITY && resultCode == RESULT_OK && mIntentPictureFile != null) {
            //Got the picture from the camera
            Picasso.with(ProjectActivity.this)
                    .load(mIntentPictureFile)
                    .resize(mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight())
                    .centerCrop()
                    .into(mHeader);
            mProject.setPicture(mIntentPictureFile.getAbsolutePath());
            mProject.save();
            mIntentPictureFile = null;
        }
        if (requestCode == PictureHelper.REQUEST_GET_GALLERY_PICTURE_ACTIVITY && resultCode == RESULT_OK) {
            //Got a picture from the gallery
            Picasso.with(ProjectActivity.this)
                    .load(data.getData())
                    .resize(mHeader.getMeasuredWidth(), mHeader.getMeasuredHeight())
                    .centerCrop()
                    .into(mHeader);
            mProject.setPicture(Utils.getRealPathFromURI(this, data.getData()));
            mProject.save();
            mIntentPictureFile = null;
        }

        if (resultCode == RESULT_OK
                && (requestCode == PictureHelper.REQUEST_GET_GALLERY_PICTURE_FRAGMENT
                || requestCode == PictureHelper.REQUEST_TAKE_PICTURE_FRAGMENT)) {
            ((ProjectPagerAdapter) mViewPager.getAdapter()).getItem(mViewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public File getPictureFile() {
        return mIntentPictureFile;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        hideKeyboard();
    }

    private void hideKeyboard() {
        //Hide Keyboard
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }
}
