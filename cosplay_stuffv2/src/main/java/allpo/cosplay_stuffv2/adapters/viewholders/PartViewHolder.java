package allpo.cosplay_stuffv2.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import allpo.cosplay_stuffv2.R;

/**
 * Created by Allpo on 11/07/2015.
 */
public class PartViewHolder {
    public View reorder;
    public TextView title;
    public TextView priceTime;
    public View container;

    public PartViewHolder(View view) {
        reorder = view.findViewById(R.id.row_part_order);
        container = view.findViewById(R.id.row_part_order);
        title = (TextView) view.findViewById(R.id.row_part_title);
        priceTime = (TextView) view.findViewById(R.id.row_part_price_time);
    }
}
