package allpo.cosplaystuff.datas;

import com.j256.ormlite.field.DatabaseField;

import allpo.cosplaystuff.helpers.PersistentData;

/**
 * Created by Allpo on 16/08/2014.
 */
public class CosplayPicture extends PersistentData {

    private static String TAG = "Picture";
    public static final String DB_FIELD_PATH = "path";
    public static final String DB_FIELD_RELATED_PROJECT_ID = "relatedProjectId";
    public static final String DB_FIELD_GALLERY_TYPE = "galleryType";

    @DatabaseField(canBeNull = false)
    private String path;

    @DatabaseField()
    private int relatedProjectId;

    @DatabaseField(canBeNull = false)
    private String galleryType;

    public CosplayPicture(){
//        super();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRelatedProjectId() {
        return relatedProjectId;
    }

    public void setRelatedProjectId(int relatedProjectId) {
        this.relatedProjectId = relatedProjectId;
    }

    public String getGalleryType() {
        return galleryType;
    }

    public void setGalleryType(String galleryType) {
        this.galleryType = galleryType;
    }
}
