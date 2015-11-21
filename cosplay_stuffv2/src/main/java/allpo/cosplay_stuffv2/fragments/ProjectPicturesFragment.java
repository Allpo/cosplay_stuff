package allpo.cosplay_stuffv2.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.GalleryActivity;
import allpo.cosplay_stuffv2.activities.ProjectActivity;
import allpo.cosplay_stuffv2.adapters.PictureAdapter;
import allpo.cosplay_stuffv2.dialogs.GetPicturePickerDialog;
import allpo.cosplay_stuffv2.models.CosplayPicture;
import allpo.cosplay_stuffv2.models.CosplayProject;
import allpo.cosplay_stuffv2.utils.PictureHelper;
import allpo.cosplay_stuffv2.utils.Utils;

/**
 * Created by Allpo on 28/06/2015.
 */
public class ProjectPicturesFragment extends CosplayFragment implements View.OnClickListener {

    private static final String PIC_TYPE_ARGUMENT = "ProjectPicturesFragment.PIC_TYPE_ARGUMENT";

    private CosplayProject mProject;

    private GridView mGridView;
    private PictureAdapter mAdapter;
    private View mAddButton;

    private File mIntentPictureFile;

    private int mPicType;

    public static ProjectPicturesFragment newInstance(int picType) {
        ProjectPicturesFragment fragment = new ProjectPicturesFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(PIC_TYPE_ARGUMENT, picType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null || !getArguments().containsKey(PIC_TYPE_ARGUMENT)) {
            throw new IllegalArgumentException("missing argument " + PIC_TYPE_ARGUMENT);
        }

        mPicType = getArguments().getInt(PIC_TYPE_ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_grid, container, false);

        mGridView = (GridView) view.findViewById(R.id.fragment_picture_grid);
        mAddButton = view.findViewById(R.id.fragment_picture_add);

        initUI();

        return view;
    }

    private void initUI() {
        mAddButton.setOnClickListener(this);
        List<CosplayPicture> cosplayPictureList = null;
        if (mPicType == CosplayPicture.REF_PIC) {
            cosplayPictureList = mProject.getRefPicture();
        } else if (mPicType == CosplayPicture.WIP_PIC) {
            cosplayPictureList = mProject.getWIPPicture();
        }
        mAdapter = new PictureAdapter(getActivity(), cosplayPictureList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryActivity.startForProject(mProject, mPicType, position, getActivity());
            }
        });
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
        int itemId = v.getId();

        switch (itemId) {
            case R.id.fragment_picture_add:
                GetPicturePickerDialog dialog = GetPicturePickerDialog.newInstance(GetPicturePickerDialog.CALLING_FRAGMENT);
                dialog.show(getActivity().getFragmentManager(), GetPicturePickerDialog.REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureHelper.REQUEST_TAKE_PICTURE_FRAGMENT && resultCode == Activity.RESULT_OK && mIntentPictureFile != null) {
            //Got the picture from the camera
            CosplayPicture picture = createCosplayPicture(mIntentPictureFile.getAbsolutePath());

            mAdapter.add(picture);
            mAdapter.notifyDataSetChanged();
            mIntentPictureFile = null;
        }
        if (requestCode == PictureHelper.REQUEST_GET_GALLERY_PICTURE_FRAGMENT && resultCode == Activity.RESULT_OK) {
            //Got a picture from the gallery
            CosplayPicture picture = createCosplayPicture(Utils.getRealPathFromURI(getActivity(), data.getData()));

            mAdapter.add(picture);
            mAdapter.notifyDataSetChanged();
            mIntentPictureFile = null;
        }
    }

    private CosplayPicture createCosplayPicture(String path) {
        CosplayPicture picture = new CosplayPicture(mPicType);

        picture.setPath(path);
        picture.setProjectId(mProject.getId());
        picture.save();

        return picture;
    }
}
