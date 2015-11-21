package allpo.cosplay_stuffv2.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Allpo on 16/06/2015.
 */
public class CosplayTask extends SugarRecord<CosplayTask> {

    public static final int STATUS_TODO = 0;
    public static final int STATUS_INPROGRESS = 1;
    public static final int STATUS_DONE = 2;

    private long _id;
    private String mName;
    private String mDescription;

    //Date it has to be done
    private Date mDueDate;
    //Alarm?

    //To do, In progress or Done
    private int mStatus;


    ArrayList<Long> mLinkedPartList;


    /**
     *  GETTERS AND SETTERS
     */

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public ArrayList<Long> getLinkedPartList() {
        return mLinkedPartList;
    }

    public void setLinkedPartList(ArrayList<Long> linkedPartList) {
        mLinkedPartList = linkedPartList;
    }
}
