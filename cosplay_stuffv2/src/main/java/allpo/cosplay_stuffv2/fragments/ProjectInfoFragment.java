package allpo.cosplay_stuffv2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.ProjectActivity;
import allpo.cosplay_stuffv2.models.CosplayProject;

/**
 * Created by Allpo on 20/06/2015.
 */
public class ProjectInfoFragment extends CosplayFragment implements View.OnClickListener {

    private static final String PROJECT_ATTRIBUTE = "ProjectInfoFragment.PROJECT_ATTRIBUTE";

    private CosplayProject mProject;

    private EditText mTitle;

    private EditText mSerie;

    private EditText mCharacter;

    private TextView mParts;

    public static ProjectInfoFragment newInstance() {
        ProjectInfoFragment fragment = new ProjectInfoFragment();

        return fragment;
    }

    public static ProjectInfoFragment newInstance(CosplayProject project) {
        if (project == null)
            throw new IllegalArgumentException("Provide a project");

        ProjectInfoFragment fragment = newInstance();

        Bundle bundle = new Bundle();
        bundle.putLong(PROJECT_ATTRIBUTE, project.getId());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_info, container, false);

        mTitle = (EditText) view.findViewById(R.id.info_title);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveData();
            }
        });
        mSerie = (EditText) view.findViewById(R.id.info_serie);
        mSerie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveData();
            }
        });
        mCharacter = (EditText) view.findViewById(R.id.info_character);
        mCharacter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveData();
            }
        });

        initUI();

        return view;
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

    private void initUI() {
        mTitle.setOnClickListener(this);

        mTitle.setText(mProject.getProjectName());
        mSerie.setText(mProject.getSerieName());
        mCharacter.setText(mProject.getCharacterName());
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.info_title) {
        } else if (itemId == R.id.info_serie) {

        } else if (itemId == R.id.info_character) {

        }
    }

    private void saveData() {
        if (!mTitle.getText().toString().isEmpty()) {
            mProject.setProjectName(mTitle.getText().toString());
        }

        if (!mSerie.getText().toString().isEmpty()) {
            mProject.setSerieName(mSerie.getText().toString());
        }

        if (!mCharacter.getText().toString().isEmpty()) {
            mProject.setCharacterName(mCharacter.getText().toString());
        }
    }
}
