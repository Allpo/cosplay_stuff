package allpo.cosplaystuff.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allpo.cosplaystuff.datas.CheckElement;
import allpo.cosplaystuff.datas.CosplayPicture;
import allpo.cosplaystuff.datas.CosplayProject;

/**
 * Created by Allpo on 14/07/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "CSdb";
    private static final int DATABASE_VERSION = 1;

    private Map<Class, Dao<?,Integer>> mMapDao;

    private static DatabaseHelper dbHelper;
    private ConnectionSource dbConnexionSource;

    private Context mContext;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static void createDB(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance(){
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        dbConnexionSource = connectionSource;
        try {
            //Put here the table to create
            addTable(CosplayProject.class);
            addTable(CosplayPicture.class);
            addTable(CheckElement.class);

        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while creating database " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dbConnexionSource = connectionSource;
        try {
            //Put here the table to create
            addTable(CosplayProject.class);

        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while creating database " + e);
        }
    }

    private void addTable(Class dataClass) throws SQLException {
        TableUtils.createTable(connectionSource, dataClass);
        Log.i(DatabaseHelper.class.getName(), "Created new table : " + dataClass.getName());
    }

    @Override
    public void close() {
        super.close();
        mMapDao.clear();
    }

    @Override
    public final Dao<?,Integer> getDao(Class objectClass) {
        try {
            if (mMapDao == null) {
                mMapDao = new HashMap<Class, Dao<?, Integer>>();
            }
            @SuppressWarnings("unchecked")
            Dao<Class, Integer> dao = (Dao<Class, Integer>) mMapDao.get(objectClass);
            if (dao == null) {
                dao = super.getDao(objectClass);
                mMapDao.put(objectClass, dao);
            }
            return dao;
        } catch (SQLException e){

        }

        return null;
    }

    /**
     *
     * CosplayProject
     *
     */
    public void saveCosplayProject(CosplayProject project){
        try {
            Dao<CosplayProject,Integer> dao = (Dao<CosplayProject,Integer>) getDao(CosplayProject.class);
            if (dao != null) {
                dao.createOrUpdate(project);
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while saving CosplayProject " + e);
        }
    }

    public List<CosplayProject> getCosplayProject(){
        List<CosplayProject> cosplayProjectList = new ArrayList<CosplayProject>();

        try {
            Dao<CosplayProject,Integer> dao = (Dao<CosplayProject,Integer>) getDao(CosplayProject.class);
            if (dao != null) {
                cosplayProjectList = dao.queryForAll();
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while getting CosplayProject " + e);
        }

        return  cosplayProjectList;
    }

    public void removeProject(CosplayProject cosplayProject){
        try {
            Dao<CosplayProject,Integer> dao = (Dao<CosplayProject,Integer>) getDao(CosplayProject.class);
            dao.delete(cosplayProject);
        } catch (SQLException e){

        }
    }

    /**
     *
     * CosplayPicture
     *
     */
    public void saveCosplayPicture(CosplayPicture cosplayPicture){
        try {
            Dao<CosplayPicture,Integer> dao = (Dao<CosplayPicture,Integer>) getDao(CosplayPicture.class);
            if (dao != null) {
                dao.createOrUpdate(cosplayPicture);
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while saving CosplayPicture " + e);
        }
    }

    public List<CosplayPicture> getCosplayPicture(CosplayProject cosplayProject, String picType){
        List<CosplayPicture> cosplayPictureList = new ArrayList<CosplayPicture>();

        try {
            Dao<CosplayPicture,Integer> dao = (Dao<CosplayPicture,Integer>) getDao(CosplayPicture.class);
            if (dao != null) {
//                cosplayPictureList = dao.queryForEq(CosplayPicture.DB_FIELD_RELATED_PROJECT_ID, cosplayProject.getId());
                cosplayPictureList = dao.queryBuilder().where().eq(CosplayPicture.DB_FIELD_RELATED_PROJECT_ID, cosplayProject.getId()).and().eq(CosplayPicture.DB_FIELD_GALLERY_TYPE, picType).query();
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while getting CosplayPictureList " + e);
        }

        return  cosplayPictureList;
    }

    public void removeCosplayPicture(CosplayPicture cosplayPicture){
        try {
            Dao<CosplayPicture,Integer> dao = (Dao<CosplayPicture,Integer>) getDao(CosplayPicture.class);
            dao.delete(cosplayPicture);
        } catch (SQLException e){

        }
    }

    /**
     *
     * CheckElement
     *
     */
    public List<CheckElement> getCheckElementsFromProject(CosplayProject cosplayProject){
        List<CheckElement> checkElementList = null;

        try {
            Dao<CheckElement,Integer> dao = (Dao<CheckElement,Integer>) getDao(CheckElement.class);
            if (dao != null) {
                checkElementList = dao.queryBuilder().where().eq(CheckElement.DB_FIELD_RELATED_PROJECT, cosplayProject.getId()).and().eq(cosplayProject.DB_FIELD_ID, cosplayProject.getId()).query();
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while getting CheckElement " + e);
        }

        return checkElementList;
    }

    public void saveCheckElement(CheckElement checkElement){
        try {
            Dao<CheckElement,Integer> dao = (Dao<CheckElement,Integer>) getDao(CheckElement.class);
            if (dao != null) {
                dao.createOrUpdate(checkElement);
            }
        } catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Error while saving CosplayPicture " + e);
        }
    }

    public void removeCheckElement(CheckElement checkElement){
        try {
            Dao<CheckElement,Integer> dao = (Dao<CheckElement,Integer>) getDao(CheckElement.class);
            dao.delete(checkElement);
        } catch (SQLException e){

        }
    }
}
