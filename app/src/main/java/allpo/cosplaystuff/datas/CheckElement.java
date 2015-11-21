package allpo.cosplaystuff.datas;

import com.j256.ormlite.field.DatabaseField;

import allpo.cosplaystuff.helpers.PersistentData;

/**
 * Created by Allpo on 04/09/2014.
 */
public class CheckElement extends PersistentData {

    public static final String DB_FIELD_NAME = "name";
    public static final String DB_FIELD_IS_CHECKED = "isChecked";
    public static final String DB_FIELD_RELATED_PROJECT = "relatedProject";

    @DatabaseField(columnName = DB_FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = DB_FIELD_IS_CHECKED, canBeNull = false)
    private boolean isChecked;

    @DatabaseField(columnName = DB_FIELD_IS_CHECKED, canBeNull = false)
    private int position;

    @DatabaseField(columnName = DB_FIELD_RELATED_PROJECT, canBeNull = false)
    private int relatedProjectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRelatedProjectId() {
        return relatedProjectId;
    }

    public void setRelatedProjectId(int relatedProjectId) {
        this.relatedProjectId = relatedProjectId;
    }
}
