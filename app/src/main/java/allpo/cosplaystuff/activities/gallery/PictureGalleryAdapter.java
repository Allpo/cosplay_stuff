package allpo.cosplaystuff.activities.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.activities.home.HomeProjectListActivity;
import allpo.cosplaystuff.datas.CosplayPicture;
import allpo.cosplaystuff.helpers.Utils.ImageHelper;

/**
 * Created by Allpo on 16/08/2014.
 */
public class PictureGalleryAdapter extends ArrayAdapter<CosplayPicture> {

    public static final String TAG = "PictureGalleryAdapter";

    private List<CosplayPicture> mDatas;

    private Map<Integer, Bitmap> mPics;

    public PictureGalleryAdapter(Context context, int resource, List<CosplayPicture> picList) {
        super(context, resource);

        mPics = new HashMap<Integer, Bitmap>();

        mDatas = picList;
    }

    @Override
    public int getCount() {
        if (mDatas == null){
            return 0;
        }

        return mDatas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        GalleryPictureViewHolder holder;

        //if rowview == null then we need to create the view
        //it means the list view is not full of view
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.item_grid_list_picture, parent, false);
            holder = new GalleryPictureViewHolder();
            holder.picture = (ImageView) rowView.findViewById(R.id.picture_gallery_item_picture);
            rowView.setTag(holder);
        } else {
            //in this case it means we've reach the maximum number of view
            //we will re-use a recyclable view
            holder = (GalleryPictureViewHolder) rowView.getTag();
        }

        CosplayPicture cosplayPicture = mDatas.get(position);

        holder.picture.setVisibility(View.INVISIBLE);
        //Get the Drawable as default image
//        if (((AbstractActivity) getContext()).image == null) {
//            ((AbstractActivity) getContext()).image = ImageHelper.createRoundImage(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.cs_cerberus));
//        }
//        holder.picture.setImageBitmap(((HomeProjectListActivity) getContext()).image);

        LoadImageTask task = new LoadImageTask();
        task.holder = holder;
        task.position = position;
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        return rowView;
    }

    private class GalleryPictureViewHolder{
        ImageView picture;
    }

    //AsyncTask that will lazyload the picture to avoid freeze during the scroll
    private class LoadImageTask extends AsyncTask<Void, Void, Void>{

        public int position;

        public GalleryPictureViewHolder holder;

        private Bitmap pic;

        boolean isInCache = true;

        @Override
        protected Void doInBackground(Void... params) {

            CosplayPicture project = mDatas.get(position);
            pic = mPics.get(project.getId());

            if (pic == null) {
                isInCache = false;
                Log.d(TAG, "Getting picture from object");
                if (project.getPath() != null) {
                    pic = ImageHelper.createPreviewImage(ImageHelper.getBitmapFromPath(project.getPath()));
                } else {
                    pic = ((HomeProjectListActivity) getContext()).image;
                }

                mPics.put(project.getId(), pic);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            holder.picture.setImageBitmap(pic);
            if (!isInCache) {
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                holder.picture.startAnimation(myFadeInAnimation);
            }

            holder.picture.setVisibility(View.VISIBLE);
        }
    }
}
