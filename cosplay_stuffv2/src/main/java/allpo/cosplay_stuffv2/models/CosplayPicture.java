package allpo.cosplay_stuffv2.models;

import com.orm.SugarRecord;

/**
 * A picture related to a {@link CosplayProject}
 */
public class CosplayPicture extends SugarRecord<CosplayPicture> {

    public static final int REF_PIC = 0;
    public static final int WIP_PIC = 1;
    public static final int HEADER_PIC = 2;

    protected String mPath;

    long projectId;

    protected int mPicType;

    public CosplayPicture() {
    }

    public CosplayPicture(int picType) {
        mPicType = picType;
    }

    public CosplayPicture(String mPath, long projectId, int picType) {
        this.mPath = mPath;
        this.projectId = projectId;
        this.mPicType = picType;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        String path = mPath;
        if (path != null && !path.contains("file://")) {
            path = "file://" + path;
        }
        this.mPath = path;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getPicType() {
        return mPicType;
    }

    public void setPicType(int mPicType) {
        this.mPicType = mPicType;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CosplayPicture) {
            return id == ((CosplayPicture) o).getId();
        }

        return super.equals(o);
    }
}
