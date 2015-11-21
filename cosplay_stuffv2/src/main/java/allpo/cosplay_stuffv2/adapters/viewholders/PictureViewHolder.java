package allpo.cosplay_stuffv2.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;

import allpo.cosplay_stuffv2.R;

/**
 * Created by Allpo on 28/06/2015.
 */
public class PictureViewHolder {
    public ImageView picture;

    public PictureViewHolder(View view) {
        picture = (ImageView) view.findViewById(R.id.row_picture_pic);
    }
}
