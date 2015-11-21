package allpo.cosplay_stuffv2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.interfaces.ProjectClickListener;
import allpo.cosplay_stuffv2.models.CosplayProject;

/**
 * Created by Allpo on 20/06/2015.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name;
    public TextView characterName;
    public TextView serieName;
    public ImageView picture;

    private Context mContext;
    private CosplayProject mProject;

    /**
     * Used to send back the click to the container of the recycler view
     */
    private ProjectClickListener mProjectClickListener;

    public ProjectViewHolder(View view, ProjectClickListener projectClickListener) {
        super(view);

        name = (TextView) view.findViewById(R.id.row_project_title);
        characterName = (TextView) view.findViewById(R.id.row_project_character);
        serieName = (TextView) view.findViewById(R.id.row_project_serie);
        picture = (ImageView) view.findViewById(R.id.row_project_picture);

        view.findViewById(R.id.row_project).setOnClickListener(this);

        mContext = name.getContext();
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.row_project) {
        }
    }

    public CosplayProject getProject() {
        return mProject;
    }

    public void setProject(CosplayProject project) {
        mProject = project;
    }
}
