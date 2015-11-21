package allpo.cosplay_stuffv2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.ProjectActivity;
import allpo.cosplay_stuffv2.adapters.PartAdapter;
import allpo.cosplay_stuffv2.dialogs.CosplayPartDialog;
import allpo.cosplay_stuffv2.models.CosplayPart;
import allpo.cosplay_stuffv2.models.CosplayProject;
import allpo.cosplay_stuffv2.receivers.PartReceiver;

/**
 * Created by Allpo on 01/07/2015.
 */
public class ProjectPartFragment extends CosplayFragment implements View.OnClickListener {

    private CosplayProject mProject;

    private ListView mListView;
    private PartAdapter mAdapter;
    private FloatingActionButton mAddButton;

    private PartReceiver mPartReceiver;

    public static ProjectPartFragment newInstance() {
        ProjectPartFragment fragment = new ProjectPartFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPartReceiver = new PartReceiver(getActivity()) {
            @Override
            public void onCreatePart(long id) {
                super.onCreatePart(id);
                populateAdapter(fetchData());
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parts, container, false);

        mListView = (ListView) view.findViewById(R.id.fragment_part_list);
        mAddButton = (FloatingActionButton) view.findViewById(R.id.fragment_part_add);

        initUI();

        return view;
    }

    private void initUI() {
        mAddButton.setOnClickListener(this);
        mAdapter = new PartAdapter(getActivity(), new ArrayList<CosplayPart>());
        mListView.setAdapter(mAdapter);

        populateAdapter(fetchData());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof ProjectActivity) {
            mProject = ((ProjectActivity) getActivity()).getProject();

            if (mProject == null)
                throw new IllegalArgumentException("Project from activity can't be null");
        } else {
            throw new IllegalStateException("Calling activity must be ProjectActivity");
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.fragment_part_add:
                openPartDialog();
                break;
        }
    }

    private void openPartDialog() {
        CosplayPartDialog dialog = CosplayPartDialog.startNewPart();

        dialog.show(getActivity().getSupportFragmentManager(), "open new part dialog");
    }

    @Override
    public void onResume() {
        super.onResume();

        PartReceiver.register(mPartReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();


        PartReceiver.unregister(mPartReceiver);
    }

    private void populateAdapter(List<CosplayPart> parts) {
        if (parts != null && parts.size() != mAdapter.getCount()) {
            mAdapter.clear();
            mAdapter.addAll(parts);
        }
    }

    private List<CosplayPart> fetchData() {
        return mProject.getParts();
    }


}
