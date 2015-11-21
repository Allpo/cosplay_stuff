package allpo.cosplay_stuffv2.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.adapters.GalleryPagerAdapter;
import allpo.cosplay_stuffv2.models.CosplayPicture;
import allpo.cosplay_stuffv2.models.CosplayProject;

/**
 * Created by Allpo on 29/06/2015.
 */
public class GalleryActivity extends CosplayActivity implements View.OnClickListener {

    private static final String PROJECT_ARG = "GalleryActivity.PROJECT_ARG";
    private static final String PIC_TYPE_ARG = "GalleryActivity.PIC_TYPE_ARG";
    private static final String POSITION_ARG = "GalleryActivity.POSITION_ARG";

    private static final String ANIMATION_METHOD = "animationValue";

    public static void startForProject(CosplayProject project, int picType, int position, Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(PROJECT_ARG, project.getId());
        intent.putExtra(PIC_TYPE_ARG, picType);
        intent.putExtra(POSITION_ARG, position);

        context.startActivity(intent);
    }

    private ViewPager mPager;
    private GalleryPagerAdapter mAdapter;
    private Toolbar mToolbar;
    private int mPicType;
    private CosplayProject mProject;
    private int mStartingPosition;

    private ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        Bundle bundle = getIntent().getExtras();

        if (!bundle.containsKey(PROJECT_ARG)
                || !bundle.containsKey(PIC_TYPE_ARG)
                || !bundle.containsKey(POSITION_ARG)) {
            throw new IllegalArgumentException("Missions args, please, call this activity with startForProject");
        }

        mPicType = bundle.getInt(PIC_TYPE_ARG);
        mStartingPosition = bundle.getInt(POSITION_ARG);
        mProject = CosplayProject.findById(CosplayProject.class, bundle.getLong(PROJECT_ARG));

        if (mProject == null) {
            throw new IllegalArgumentException("Could not find project, please provide valid Id");
        }

        initUI();
    }

    private void initUI() {
        mPager = (ViewPager) findViewById(R.id.gallery_pager);

        List<CosplayPicture> pictures;
        if (mPicType == CosplayPicture.WIP_PIC) {
            pictures = mProject.getWIPPicture();
        } else {
            pictures = mProject.getRefPicture();
        }

        mAdapter = new GalleryPagerAdapter(getApplicationContext(), pictures);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(mStartingPosition);
        mAdapter.setListener(this);

        mToolbar = (Toolbar) findViewById(R.id.gallery_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mObjectAnimator = new ObjectAnimator().ofFloat(this, ANIMATION_METHOD, 1f, 0f).setDuration(300);
        mObjectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mToolbar.setAlpha(mToolbar.getAlpha() > 0.5f ? 1f : 0f);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_gallery_share:
                shareCurrentPicture();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAnimationValue(float currentFade) {
        ViewHelper.setAlpha(mToolbar, currentFade);
    }

    @Override
    public void onClick(View v) {
        if (!mObjectAnimator.isRunning()) {
            mObjectAnimator.cancel();
            if (mToolbar.getAlpha() == 1f) {
                mObjectAnimator.start();
            } else {
                mObjectAnimator.reverse();
            }
        }
    }

    private void shareCurrentPicture() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(mAdapter.getPictures().get(mPager.getCurrentItem()).getPath()));
        sendIntent.setType("image/jpeg");
        startActivity(sendIntent);
    }
}
