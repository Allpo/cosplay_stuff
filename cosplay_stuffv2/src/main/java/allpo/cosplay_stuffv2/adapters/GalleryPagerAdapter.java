package allpo.cosplay_stuffv2.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.models.CosplayPicture;

/**
 * Created by Allpo on 29/06/2015.
 */
public class GalleryPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CosplayPicture> mPictures;
    private View.OnClickListener mListener;

    public GalleryPagerAdapter(Context context, List<CosplayPicture> pictures) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPictures = pictures;
    }

    @Override
    public int getCount() {
        return mPictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.page_gallery, container, false);

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.page_gallery_picture);
        imageView.setImage(ImageSource.uri(mPictures.get(position).getPath()));
        imageView.setOnClickListener(this);

        container.addView(itemView);

        return itemView;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        switch (itemId) {
            case R.id.page_gallery_picture:
                if (mListener != null) {
                    mListener.onClick(v);
                }
                break;
        }
    }

    public View.OnClickListener getListener() {
        return mListener;
    }

    public void setListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    public List<CosplayPicture> getPictures() {
        return mPictures;
    }
}
