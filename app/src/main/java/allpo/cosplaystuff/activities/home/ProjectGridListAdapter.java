package allpo.cosplaystuff.activities.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Utils.ImageHelper;

/**
 * Created by Allpo on 14/07/2014.
 */
public class ProjectGridListAdapter extends ArrayAdapter<CosplayProject> {

    public static String TAG = "ProjectGridListAdapter";

    private final Context mContext;

    private List<CosplayProject> mDatas;

    private Map<String, Bitmap> mPics;

    public ProjectGridListAdapter(Context context, int resource, List<CosplayProject> objects) {
        super(context, resource, objects);
        mContext = context;
        mDatas = objects;
        mPics = new HashMap<String, Bitmap>();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        ProjectGridViewHolder holder;

        //if rowview == null then we need to create the view
        //it means the list view is not full of view
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.item_grid_list_project, parent, false);
            holder = new ProjectGridViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.grid_list_element_name);
            holder.picture = (ImageView) rowView.findViewById(R.id.grid_list_element_pic);
            rowView.setTag(holder);
        } else {
            //in this case it means we've reach the maximum number of view
            //we will re-use a recyclable view
            holder = (ProjectGridViewHolder) rowView.getTag();
        }

        CosplayProject project = mDatas.get(position);
        //Fill the holder, add this function is it's too complicated to not overcharge this function
        holder.name.setText(project.getName());
        holder.picture.setVisibility(View.INVISIBLE);
        //Get the Drawable as default image
        if (((HomeProjectListActivity) getContext()).image == null) {
            ((HomeProjectListActivity) getContext()).image = ImageHelper.createRoundImage(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.cs_cerberus));
        }
        holder.picture.setImageBitmap(((HomeProjectListActivity) getContext()).image);

        LoadImageTask task = new LoadImageTask();
        task.holder = holder;
        task.position = position;
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        return rowView;
    }

    //AsyncTask that will lazyload the picture to avoid freeze during the scroll
    private class LoadImageTask extends AsyncTask<Void, Void, Void>{

        public int position;

        public ProjectGridViewHolder holder;

        private Bitmap pic;

        private boolean isInCache = true;

        @Override
        protected Void doInBackground(Void... params) {

            CosplayProject project = mDatas.get(position);
            pic = mPics.get(project.getHeaderPicPath());

            if (pic == null) {
                isInCache = false;
                Log.d(TAG, "Getting picture from object");
                if (project.getHeaderPicPath() != null) {
                    pic = ImageHelper.createRoundImage(ImageHelper.getBitmapFromPath(project.getHeaderPicPath()));
                } else {
                    pic = ((HomeProjectListActivity) getContext()).image;
                }

                mPics.put(project.getHeaderPicPath(), pic);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            holder.picture.setImageBitmap(pic);
            if (!isInCache) {
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                holder.picture.startAnimation(myFadeInAnimation);
            }
            holder.picture.setVisibility(View.VISIBLE);
        }
    }
}
