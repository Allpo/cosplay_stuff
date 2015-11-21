package allpo.cosplaystuff.activities.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.activities.AbstractActivity;
import allpo.cosplaystuff.activities.project.ProjectActivity;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.DatabaseHelper;


public class HomeProjectListActivity extends AbstractActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String TAG = "HomeProjectListActivity";

    public Bitmap image;

    List<CosplayProject> mProjectList;

    GridView mGridView;

    ArrayAdapter<CosplayProject> mGridAdapter;

    Button mAddButton;

    List<View> mLongClickList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_project_list);

        initDrawer();

        //New Project Button
        mAddButton = (Button) findViewById(R.id.project_list_add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Open the fragment to create a project
                Intent intent = new Intent(HomeProjectListActivity.this, ProjectActivity.class);
                startActivity(intent);

            }
        });

        //Get the database content
        mProjectList = DatabaseHelper.getInstance().getCosplayProject();

        //Grid View stuff initialize
        mGridView = (GridView) findViewById(R.id.project_list_list);
        mGridAdapter = new ProjectGridListAdapter(this, R.layout.item_grid_list_project, mProjectList);
        //Tried another adapter with Universal Image Loader but it's laggy
//        mGridAdapter = new ProjectGridListCustomAdapter(getActivity(), R.layout.item_grid_list_project, mProjectList);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnItemLongClickListener(this);
        mGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int visibility = view.findViewById(R.id.grid_list_element_delete).getVisibility();
        if (visibility == View.VISIBLE) {
            mProjectList.get(position).removePicsBeforeProjectDelete();
            DatabaseHelper.getInstance().removeProject(mProjectList.get(position));
            mProjectList.remove(position);
            mGridAdapter.notifyDataSetChanged();
            List<CosplayProject> reducedList = DatabaseHelper.getInstance().getCosplayProject();
            reducedList.size();
        } else {
            //TODO : Open already existing project
            Intent intent = new Intent(HomeProjectListActivity.this, ProjectActivity.class);
            intent.putExtra(Constant.INTENT_PROJECT, mProjectList.get(position));
            startActivity(intent);
        }
        unSelectProjects();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGridAdapter.clear();
        mGridAdapter.addAll(DatabaseHelper.getInstance().getCosplayProject());
        mGridAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int visibility = view.findViewById(R.id.grid_list_element_delete).getVisibility();
        if (visibility == View.VISIBLE) {
            view.findViewById(R.id.grid_list_element_delete).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.grid_list_element_delete).setVisibility(View.VISIBLE);
            if (mLongClickList == null) {
                mLongClickList = new ArrayList<View>();
            }
            unSelectProjects();
            mLongClickList.add(view);
        }
        return true;
    }

    private void unSelectProjects() {
        if (mLongClickList != null) {
            for (View view1 : mLongClickList) {
                view1.findViewById(R.id.grid_list_element_delete).setVisibility(View.GONE);
            }
            mLongClickList.clear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
