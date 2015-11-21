package allpo.cosplay_stuffv2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.interfaces.ProjectClickListener;
import allpo.cosplay_stuffv2.adapters.ProjectAdapter;
import allpo.cosplay_stuffv2.models.CosplayProject;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ProjectClickListener {

    private Toolbar mToolbar;
    private View mAddButton;
    private ProjectAdapter mAdaper;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();

        initUI();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
    }

    private void initUI() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        mAdaper = new ProjectAdapter(this);
        mAddButton = findViewById(R.id.main_add);
        mAddButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProject();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.main_add:
                ProjectActivity.startNewProject(this);
                break;
        }
    }

    @Override
    public void onProjectClicked(CosplayProject project) {
        ProjectActivity.startForProject(this, project);
    }

    private void loadProject() {
        mAdaper.clear();
        mAdaper.addProjects(CosplayProject.listAll(CosplayProject.class));
        mAdaper.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_action_add) {
            ProjectActivity.startNewProject(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
