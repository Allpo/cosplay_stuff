package allpo.cosplaystuff.activities.checklist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.R;
import allpo.cosplaystuff.activities.AbstractActivity;
import allpo.cosplaystuff.datas.CheckElement;
import allpo.cosplaystuff.datas.CosplayProject;
import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.DatabaseHelper;
import allpo.cosplaystuff.helpers.customComponent.TouchListView;

/**
* Created by Allpo on 04/09/2014.
*/
public class CheckListActivity extends AbstractActivity {

    private CosplayProject mCosplayProject;
    private List<CheckElement> mCheckList;
    private TouchListView mListView;
    private CheckListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checklist);

        mCosplayProject = (CosplayProject) getIntent().getSerializableExtra(Constant.INTENT_PROJECT);
        if (mCosplayProject != null) {
            mCheckList = DatabaseHelper.getInstance().getCheckElementsFromProject(mCosplayProject);
            if (mCheckList == null){
                mCheckList = new ArrayList<CheckElement>();
            }

            //ONLY FOR TEST PURPOSE
            CheckElement checkItem = new CheckElement();
            checkItem.setPosition(0);
            checkItem.setName("dadidadaire 0");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(1);
            checkItem.setName("trolololo 1");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(2);
            checkItem.setName("trolololo 2");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(3);
            checkItem.setName("trolololo 3");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(4);
            checkItem.setName("trolololo 4");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(1);
            checkItem.setName("trolololo 1");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(2);
            checkItem.setName("trolololo 2");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(3);
            checkItem.setName("trolololo 3");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(4);
            checkItem.setName("trolololo 4");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(1);
            checkItem.setName("trolololo 1");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(2);
            checkItem.setName("trolololo 2");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(3);
            checkItem.setName("trolololo 3");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(4);
            checkItem.setName("trolololo 4");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(1);
            checkItem.setName("trolololo 1");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(2);
            checkItem.setName("trolololo 2");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(3);
            checkItem.setName("trolololo 3");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(4);
            checkItem.setName("trolololo 4");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(1);
            checkItem.setName("trolololo 1");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(2);
            checkItem.setName("trolololo 2");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(3);
            checkItem.setName("trolololo 3");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);
            checkItem = new CheckElement();
            checkItem.setPosition(4);
            checkItem.setName("trolololo 4");
            checkItem.setRelatedProjectId(mCosplayProject.getId());
            mCheckList.add(checkItem);

            mAdapter = new CheckListAdapter(this, R.layout.item_check_list, mCheckList);
            mListView = (TouchListView) findViewById(R.id.check_list_view);


            mListView.setAdapter(mAdapter);

            mListView.setDropListener(new TouchListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        CheckElement item = mCheckList.get(from);

                        mCheckList.remove(from);
                        mCheckList.add(to, item);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    mCheckList.get(position).setChecked(!mCheckList.get(position).isChecked());
                    mAdapter.notifyDataSetChanged();
                    CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.check_item_check_box);
                    checkBox.setChecked(mCheckList.get(position).isChecked());
                }
            });
        }
    }

    public class CheckListAdapter extends ArrayAdapter<CheckElement>{

        List<CheckElement> mDatas;

        public CheckListAdapter(Context context, int resource, List<CheckElement> checkElementsList) {
            super(context, resource);
            mDatas = checkElementsList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = convertView;
            final CheckElementViewHolder holder;

            //if rowview == null then we need to create the view
            //it means the list view is not full of view
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_check_list, parent, false);
                holder = new CheckElementViewHolder();
//                holder.title = (TextView) rowView.findViewById(R.id.check_list_view);
                holder.checkBox = (CheckBox) rowView.findViewById(R.id.check_item_check_box);
                holder.title = (TextView) rowView.findViewById(R.id.check_item_title);
                rowView.setTag(holder);
            } else {
                //in this case it means we've reach the maximum number of view
                //we will re-use a recyclable view
                holder = (CheckElementViewHolder) rowView.getTag();
            }

            final CheckElement data = mDatas.get(position);

            holder.title.setText(data.getName());
            holder.checkBox.setChecked(data.isChecked());

            return rowView;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }
    }

    public class CheckElementViewHolder {
        public TextView title;
        public CheckBox checkBox;
    }
}
