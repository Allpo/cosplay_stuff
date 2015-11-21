package allpo.cosplay_stuffv2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplay_stuffv2.R;
import allpo.cosplay_stuffv2.activities.interfaces.ProjectClickListener;
import allpo.cosplay_stuffv2.adapters.viewholders.ProjectViewHolder;
import allpo.cosplay_stuffv2.models.CosplayProject;
import allpo.cosplay_stuffv2.utils.Utils;

/**
 * Used to display the {@link CosplayProject}
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

    /**
     * The listener that will handle the click on a project
     */
    private ProjectClickListener mProjectClickListener;

    /**
     * The list of CosplayProject
     */
    private List<CosplayProject> mProjectList;

    public ProjectAdapter(ProjectClickListener projectClickListener) {
        super();

        Utils.checkNonNull(projectClickListener);
        mProjectClickListener = projectClickListener;

        mProjectList = new ArrayList<>();
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_project, parent, false), mProjectClickListener);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        final CosplayProject item = mProjectList.get(position);

        holder.setProject(item);

        holder.name.setText(item.getProjectName());
        holder.serieName.setText(item.getSerieName());
        holder.characterName.setText(item.getCharacterName());

        Picasso.with(holder.picture.getContext())
                .load(item.getPicture())
                .centerCrop()
                .placeholder(R.drawable.ic_picture_placeholder)
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return mProjectList.size();
    }

    public void addProject(CosplayProject cosplayProject) {
        Utils.checkNonNull(cosplayProject);
        mProjectList.add(cosplayProject);
        notifyDataSetChanged();
    }

    public void addProjects(List<CosplayProject> projectList) {
        Utils.checkNonNull(projectList);
        mProjectList.addAll(projectList);
        notifyDataSetChanged();
    }

    public void clear() {
        mProjectList.clear();
        notifyDataSetChanged();
    }
}
