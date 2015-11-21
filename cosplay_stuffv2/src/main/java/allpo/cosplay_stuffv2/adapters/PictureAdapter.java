package allpo.cosplay_stuffv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.adapters.viewholders.PictureViewHolder;
import allpo.cosplay_stuffv2.models.CosplayPicture;

/**
 * Created by Allpo on 28/06/2015.
 */
public class PictureAdapter extends ArrayAdapter<CosplayPicture> {

    public PictureAdapter(Context context, List<CosplayPicture> list) {
        super(context, R.layout.row_picture, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            //create the view
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_picture, parent, false);
            createViewHolder(view);
        }

        final PictureViewHolder viewHolder = (PictureViewHolder) view.getTag();
        bindViewHolder(viewHolder, position);

        return view;
    }

    private void createViewHolder(View view) {
        final PictureViewHolder viewHolder = new PictureViewHolder(view);
        view.setTag(viewHolder);
    }

    private void bindViewHolder(PictureViewHolder viewHolder, int position) {
        final CosplayPicture item = getItem(position);

        Picasso.with(getContext())
                .load(item.getPath())
                .resize(getContext().getResources().getDimensionPixelOffset(R.dimen.row_picture_image_size), getContext().getResources().getDimensionPixelOffset(R.dimen.row_picture_image_size))
                .centerCrop()
                .placeholder(R.drawable.ic_picture_placeholder)
                .into(viewHolder.picture);
    }
}
