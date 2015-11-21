package allpo.cosplaystuff.helpers;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Allpo on 14/07/2014.
 */
public class PersistentData implements Serializable {

    public static final String DB_FIELD_ID = "id";

    @DatabaseField(index = true, columnName = DB_FIELD_ID, generatedId = true)
    protected int id;

    public PersistentData() {
    }

    public int getId() {
        return id;
    }
}
