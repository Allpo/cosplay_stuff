package allpo.cosplay_stuffv2.models;

import com.orm.SugarRecord;

/**
 * Created by Allpo on 01/07/2015.
 */
public class CosplayPart extends SugarRecord<CosplayPart> {
    public static final int CRAFT = 0;
    public static final int BUY = 1;

    public static final int TODO = 0;
    public static final int INPROGRESS = 1;
    public static final int DONE = 2;

    long projectId;

    private int partType;

    private float mPrice;
    private float mTime;

    private String mTitle;
    private String mDescription;

    private int mStatus;

    public CosplayPart() {
        partType = CRAFT;
        mStatus = TODO;
    }

    public int getPartType() {
        return partType;
    }

    public void setPartType(int partType) {
        this.partType = partType;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public float getTime() {
        return mTime;
    }

    public void setTime(float mTime) {
        this.mTime = mTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CosplayPart) {
            return id == ((CosplayPart) o).getId();
        }

        return super.equals(o);
    }
}
