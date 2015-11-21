package allpo.cosplay_stuffv2.models;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

/**
 * A project for a costume
 */
public class CosplayProject extends SugarRecord<CosplayProject> {

    //String used to search
    public static final String QUERY_PICTURE_FOR_PROJECT = "projectId = ?";

    protected String mProjectName;
    protected String mCharacterName;
    protected String mSerieName;
    //Main picture of the project used while displaying the project
    protected String mPicture;

    //Default, creation date of the project in the app
    protected Date mStartedDate;
    protected Date mDueDate;

    //Filed constructed with the objects
    @Ignore
    protected List<CosplayPicture> mRefPicture;
    @Ignore
    protected List<CosplayPicture> mWIPPicture;
    @Ignore
    protected List<CosplayPart> mParts;

    public CosplayProject() {
        mProjectName = "";
        mCharacterName = "";
        mSerieName = "";

        mStartedDate = Calendar.getInstance().getTime();
        mDueDate = Calendar.getInstance().getTime();
    }

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
    }

    public String getCharacterName() {
        return mCharacterName;
    }

    public void setCharacterName(String characterName) {
        mCharacterName = characterName;
    }

    public String getSerieName() {
        return mSerieName;
    }

    public void setSerieName(String serieName) {
        mSerieName = serieName;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        String path = picture;
        if (path != null && !path.contains("file://")) {
            path = "file://" + path;
        }
        this.mPicture = path;
    }

    public Date getStartedDate() {
        return mStartedDate;
    }

    public void setStartedDate(Date startedDate) {
        mStartedDate = startedDate;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }


    /**
     * PICTURES ------------------------------------------------------------------------------------
     */

    public List<CosplayPicture> getRefPicture() {
        checkInitPictures();
        return mRefPicture;
    }

    public List<CosplayPicture> getWIPPicture() {
        checkInitPictures();
        return mWIPPicture;
    }

    public void addRefPicture(CosplayPicture picture) {
        if (picture != null && checkInitPictures()) {
            mRefPicture.add(picture);
        }
    }

    public void removeRefPicture(CosplayPicture picture) {
        if (picture != null && checkInitPictures()) {
            mRefPicture.remove(picture);
        }
    }

    public void addWIPPicture(CosplayPicture picture) {
        if (picture != null && checkInitPictures()) {
            mWIPPicture.add(picture);
        }
    }

    public void removeWIPPicture(CosplayPicture picture) {
        if (picture != null && checkInitPictures()) {
            mWIPPicture.remove(picture);
        }
    }

    /**
     * Will check if the pictures list have been initialized
     * and do it if not.
     */
    private boolean checkInitPictures() {
        if (mRefPicture == null || mWIPPicture == null) {
            initPictures();
        }

        return true;
    }

    /**
     *  Initializa the Ref & Wip picture list
     *  Called only once
     */
    private void initPictures() {
        List<CosplayPicture> pictures = getPictureForProject();
        mRefPicture = new ArrayList<>();
        mWIPPicture = new ArrayList<>();

        if (pictures != null && !pictures.isEmpty()){
            for (CosplayPicture picture : pictures) {
                switch (picture.getPicType()) {
                    case CosplayPicture.REF_PIC :
                        mRefPicture.add(picture);
                        break;
                    case CosplayPicture.WIP_PIC:
                        mWIPPicture.add(picture);
                        break;
                    default:
                        throw new IllegalArgumentException("Picture type unknown" + picture.getPicType());
                }
            }
        }
    }

    /**
     * Will get all the picture related to this project
     * @return the list of {@link CosplayPicture}
     */
    private List<CosplayPicture> getPictureForProject() {
        Select projectPictureQuery = Select.from(CosplayPicture.class)
                .where(Condition.prop("project_Id").eq(id));

        List<CosplayPicture> resList = projectPictureQuery.list();

        return resList;
    }

    /**
     * PARTS ---------------------------------------------------------------------------------------
     */
    private void initParts() {
        if (mParts == null) {
            mParts = new ArrayList<>();

            mParts.addAll(getPartsForProject());
        }
    }

    private List<CosplayPart> getPartsForProject() {
        Select partQuery = Select.from(CosplayPart.class)
                .where(Condition.prop("project_Id").eq(id));

        List<CosplayPart> resList = partQuery.list();

        return resList;
    }

    public List<CosplayPart> getParts() {
        initParts();

        return mParts;
    }

    public void addPart(CosplayPart cosplayPart) {
        initParts();

        if (!mParts.contains(cosplayPart)) {
            mParts.add(cosplayPart);
        }
    }

    public void removePart(CosplayPart cosplayPart) {
        initParts();

        mParts.remove(cosplayPart);
    }
}
