package allpo.cosplay_stuffv2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.fragments.CosplayFragment;
import allpo.cosplay_stuffv2.fragments.ProjectInfoFragment;
import allpo.cosplay_stuffv2.fragments.ProjectPartFragment;
import allpo.cosplay_stuffv2.fragments.ProjectPicturesFragment;
import allpo.cosplay_stuffv2.models.CosplayPicture;
import allpo.cosplay_stuffv2.models.CosplayProject;

/**
 * Created by Allpo on 21/06/2015.
 */
public class ProjectPagerAdapter extends FragmentStatePagerAdapter {
    private static final int nbFragments = 4;

    private static final int INFO_FRAGMENT = 0;
    private static final int REF_FRAGMENT = 1;
    private static final int WIP_FRAGMENT = 2;
    private static final int PART_FRAGMENT = 3;

    private SparseArray<CosplayFragment> mFragments;

    private CosplayProject mProject;

    private Context mContext;

    public ProjectPagerAdapter(Context context, FragmentManager fm, CosplayProject project) {
        super(fm);
        mProject = project;
        mFragments = new SparseArray<>();
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case INFO_FRAGMENT:
                if (mFragments.get(INFO_FRAGMENT) == null) {
                    mFragments.put(INFO_FRAGMENT, ProjectInfoFragment.newInstance());
                }
                return mFragments.get(INFO_FRAGMENT);
            case REF_FRAGMENT:
                if (mFragments.get(REF_FRAGMENT) == null) {
                    mFragments.put(REF_FRAGMENT, ProjectPicturesFragment.newInstance(CosplayPicture.REF_PIC));
                }
                return mFragments.get(REF_FRAGMENT);
            case WIP_FRAGMENT:
                if (mFragments.get(WIP_FRAGMENT) == null) {
                    mFragments.put(WIP_FRAGMENT, ProjectPicturesFragment.newInstance(CosplayPicture.WIP_PIC));
                }
                return mFragments.get(WIP_FRAGMENT);
            case PART_FRAGMENT:
                if (mFragments.get(PART_FRAGMENT) == null) {
                    mFragments.put(PART_FRAGMENT, ProjectPartFragment.newInstance());
                }
                return mFragments.get(PART_FRAGMENT);
        }

        return null;
    }

    @Override
    public int getCount() {
        return nbFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case INFO_FRAGMENT:
                return mContext.getString(R.string.fragment_title_info);
            case REF_FRAGMENT:
                return mContext.getString(R.string.fragment_title_ref);
            case WIP_FRAGMENT:
                return mContext.getString(R.string.fragment_title_wip);
            case PART_FRAGMENT:
                return mContext.getString(R.string.fragment_title_part);
            default:
                throw new IllegalArgumentException("Position + " + position + " not supported");
        }
    }


}
