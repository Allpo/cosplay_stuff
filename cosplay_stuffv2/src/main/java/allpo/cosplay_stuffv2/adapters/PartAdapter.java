package allpo.cosplay_stuffv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.adapters.viewholders.PartViewHolder;
import allpo.cosplay_stuffv2.models.CosplayPart;

/**
 * Created by Allpo on 11/07/2015.
 */
public class PartAdapter extends ArrayAdapter<CosplayPart> {
    public PartAdapter(Context context, List<CosplayPart> parts) {
        super(context, R.layout.row_part, parts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            //create the view
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_part, parent, false);
            createViewHolder(view);
        }

        final PartViewHolder viewHolder = (PartViewHolder) view.getTag();
        bindViewHolder(viewHolder, position);

        return view;
    }

    private void bindViewHolder(PartViewHolder viewHolder, int position) {
        final CosplayPart part = getItem(position);

        viewHolder.title.setText(part.getTitle());
    }

    private void createViewHolder(View view) {
        final PartViewHolder viewHolder = new PartViewHolder(view);
        view.setTag(viewHolder);
    }
}
