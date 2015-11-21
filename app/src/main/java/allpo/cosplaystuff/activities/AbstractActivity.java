package allpo.cosplaystuff.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.helpers.CSApplication;
import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.listener.CSDrawerListener;

/**
 * Created by Allpo on 12/07/2014.
 */
public class AbstractActivity extends Activity implements Animation.AnimationListener {

    boolean shouldHideLoader = false;

    protected DrawerLayout mDrawerLayout;

    protected ListView mDrawerList;

    protected ActionBarDrawerToggle mDrawerToggle;

    protected RelativeLayout mLoader;

    protected CSDrawerListener mCSDrawerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setTitle(getString(R.string.app_name));

        mLoader = (RelativeLayout) findViewById(R.id.activity_loader);

        //Display the drawable on the left of the action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Set the action bar to trigger the butter
        getActionBar().setHomeButtonEnabled(true);
    }

    protected void initDrawer(){
        mDrawerList = (ListView) findViewById(R.id.activity_drawer);
        DrawerLayout.LayoutParams drawerLayoutParam = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        drawerLayoutParam.width = (getResources().getDisplayMetrics().widthPixels / 3) * 2;
        mDrawerList.setLayoutParams(drawerLayoutParam);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //Fill the drawer with default adapter & default xml for item
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.item_drawer, getDrawerList()));
        //default drawer background is black, needed to change it
        mDrawerList.setBackgroundColor(getResources().getColor(R.color.white));

        //Listeners
        mCSDrawerListener = new CSDrawerListener();
        mCSDrawerListener.setMotherActivity(this);
        mDrawerList.setOnItemClickListener(mCSDrawerListener);
        mDrawerLayout.setDrawerListener(mCSDrawerListener);
    }

    public List<String> getDrawerList(){
        ArrayList<String> drawerList = new ArrayList<String>();
        //Add the elements
        drawerList.add(Constant.drawer_home);
        drawerList.add(Constant.drawer_edit);
        drawerList.add(Constant.drawer_dummy_list);

        return drawerList;
    }

    public CSApplication getCSApplication(){
        return (CSApplication) getApplication();
    }

    public void displayLoader(){
        View loader = findViewById(R.id.activity_loader);
        if (loader != null) {
//            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//            loader.startAnimation(myFadeInAnimation);
            loader.setVisibility(View.VISIBLE);
            shouldHideLoader = true;
        }
    }

    public void hideLoader(){
        View loader = findViewById(R.id.activity_loader);
        if (loader != null){
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            myFadeInAnimation.setAnimationListener(this);
            loader.startAnimation(myFadeInAnimation);
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        View loader = findViewById(R.id.activity_loader);
        if (loader != null && shouldHideLoader){
            loader.setVisibility(View.GONE);
            shouldHideLoader = false;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
