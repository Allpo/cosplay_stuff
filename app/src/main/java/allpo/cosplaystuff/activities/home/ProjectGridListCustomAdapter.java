package allpo.cosplaystuff.activities.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Utils.ImageHelper;

/**
 * Created by Allpo on 16/08/2014.
 */
public class ProjectGridListCustomAdapter extends ArrayAdapter<CosplayProject> {

    List<CosplayProject> mCosplayProjectList;

    public ProjectGridListCustomAdapter(Context context, int resource, List<CosplayProject> cosplayProjectList) {
        super(context, resource);

        mCosplayProjectList = cosplayProjectList;
    }

    @Override
    public int getCount() {
        return mCosplayProjectList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        CosplayProject project = mCosplayProjectList.get(position);
        //Fill the holder, add this function is it's too complicated to not overcharge this function
        holder.name.setText(project.getName());
//        holder.picture.setVisibility(View.INVISIBLE);
        //Get the Drawable as default image
        if (((HomeProjectListActivity) getContext()).image == null) {
            ((HomeProjectListActivity) getContext()).image = ImageHelper.createRoundImage(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.cs_cerberus));
        }
//        holder.picture.setImageBitmap(((HomeActivity) getContext()).image);

//        ImageLoader.getInstance().displayImage("file:///" + mCosplayProjectList.get(position).getHeaderPicPath(), holder.picture);

        return rowView;
    }
}
