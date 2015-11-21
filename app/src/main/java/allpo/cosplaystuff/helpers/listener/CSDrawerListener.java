package allpo.cosplaystuff.helpers.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import allpo.cosplaystuff.activities.home.HomeProjectListActivity;
import allpo.cosplaystuff.helpers.Constant;

/**
 * Created by Allpo on 17/08/2014.
 */
public class CSDrawerListener implements DrawerLayout.DrawerListener, AdapterView.OnItemClickListener {

    private Activity motherActivity;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO : Launch other activity
        switch (position) {
            case Constant.drawer_home_position :
                if (motherActivity instanceof HomeProjectListActivity){

                } else {
                    openActivity(HomeProjectListActivity.class);
                }
                break;
            case Constant.drawer_edit_position :
                break;
        }
    }

    private void openActivity(Class ObjectClass){
        Intent intent = new Intent(motherActivity, ObjectClass);
        motherActivity.startActivity(intent);

    }

    public void setMotherActivity(Activity motherActivity) {
        this.motherActivity = motherActivity;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
