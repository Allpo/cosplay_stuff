package allpo.cosplaystuff.datas;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import allpo.cosplaystuff.helpers.Constant;
import allpo.cosplaystuff.helpers.DatabaseHelper;
import allpo.cosplaystuff.helpers.PersistentData;

/**
 * Created by Allpo on 14/07/2014.
 */
public class CosplayProject extends PersistentData {

    private static String TAG = "CosplayProject";

    public static final String DB_FIELD_NAME = "name";
    public static final String DB_FIELD_HEADER_PATH = "headerPicPath";

    @DatabaseField(columnName = DB_FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = DB_FIELD_HEADER_PATH)
    private String headerPicPath;

    private List<CosplayPicture> refList;

    private List<CosplayPicture> wipList;

    private List<CheckElement> checklist;

    public CosplayProject() {
    }

    public CosplayProject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeaderPicPath(String path) {
        if (headerPicPath != null) {
            File header = new File(headerPicPath);
            if (header.exists()) {
                header.delete();
            }
        }

        if (new File(path).exists()) {
            this.headerPicPath = path;
        }
    }

    public String getHeaderPicPath() {
        if (headerPicPath != null && new File(headerPicPath).exists()) {
            return headerPicPath;
        }

        return null;
    }

    public void removePicsBeforeProjectDelete(){
        File pic;
        boolean deleted;

        //delete headerPic
        if (headerPicPath != null) {
            pic = new File(headerPicPath);
            deleted = pic.delete();
            Log.d(TAG, "file " + headerPicPath + " deleted : " + deleted);
        }
    }

    public List<CosplayPicture> getRefList(){
        if (refList == null){
            refList = DatabaseHelper.getInstance().getCosplayPicture(this, Constant.REF_LIST);
        }
        if (refList == null){
            refList = new ArrayList<CosplayPicture>();
        }

        return refList;
    }

    public void addImageToRefList(CosplayPicture cosplayPicture){
        if (refList == null){
            getRefList();
        }

        refList.add(cosplayPicture);
        cosplayPicture.setRelatedProjectId(this.getId());
        cosplayPicture.setGalleryType(Constant.REF_LIST);
        DatabaseHelper.getInstance().saveCosplayPicture(cosplayPicture);
    }

    public List<CosplayPicture> getWipList(){
        if (wipList == null){
            wipList = DatabaseHelper.getInstance().getCosplayPicture(this, Constant.WIP_LIST);
        }
        if (wipList == null){
            wipList = new ArrayList<CosplayPicture>();
        }

        return wipList;
    }

    public void addImageToWipList(CosplayPicture cosplayPicture){
        if (wipList == null){
            getWipList();
        }

        wipList.add(cosplayPicture);
        cosplayPicture.setRelatedProjectId(this.getId());
        cosplayPicture.setGalleryType(Constant.WIP_LIST);
        DatabaseHelper.getInstance().saveCosplayPicture(cosplayPicture);
    }

    public List<CheckElement> getChecklist(){
        if (checklist == null){
            checklist = DatabaseHelper.getInstance().getCheckElementsFromProject(this);
        }
        if (checklist == null){
            checklist = new ArrayList<CheckElement>();
        }

        return checklist;
    }

    public void addElementToCheckList(CheckElement checkElement){
        if (checkElement == null){
            getChecklist();
        }

        checklist.add(checkElement);
        checkElement.setRelatedProjectId(this.getId());
        DatabaseHelper.getInstance().saveCheckElement(checkElement);
    }
}
