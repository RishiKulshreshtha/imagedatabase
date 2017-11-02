package rajiv.project.com.imagedatabase.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rajiv.project.com.imagedatabase.pojo.Image;

public class DBAdapter {

    private static DBAdapter adapter;
    private DBHelper helper;
    private SQLiteDatabase database;
    private Context mContext;


    private DBAdapter(Context mContext) {
        this.mContext = mContext;
        this.helper = new DBHelper(this.mContext);
        this.database = helper.getWritableDatabase();
    }

    public static DBAdapter getAdapter(Context context) {
        if (adapter == null) {
            adapter = new DBAdapter(context);
        }
        return adapter;
    }

    public String getAllData() {

        StringBuffer buffer = new StringBuffer("");
        Cursor cursor = database.query(helper.TABLE_NAME, new String[]{helper.COL_ID, helper.COL_NAME, helper.COL_PATH, helper.COL_HEIGHT, helper.COL_WIDTH, helper.COL_SIZE}, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                int id_index = cursor.getColumnIndex(helper.COL_ID);
                int name_index = cursor.getColumnIndex(helper.COL_NAME);
                int path_index = cursor.getColumnIndex(helper.COL_PATH);
                int height_index = cursor.getColumnIndex(helper.COL_HEIGHT);
                int width_index = cursor.getColumnIndex(helper.COL_WIDTH);
                int size_index = cursor.getColumnIndex(helper.COL_SIZE);

                String id = cursor.getString(id_index);
                String name = cursor.getString(name_index);
                String path = cursor.getString(path_index);
                String height = cursor.getString(height_index);
                String width = cursor.getString(width_index);
                String size = cursor.getString(size_index);

                buffer.append(id + " " + name + "" + "\n" + path + "\n" + "" + height + "" + width + "" + "\n" + size + "\n");
            }
        }

        return buffer.toString();
    }

    public List<Image> getAllImagesList() {

        List<Image> imageList = new ArrayList<>();
        Cursor cursor = database.query(helper.TABLE_NAME, new String[]{helper.COL_ID, helper.COL_NAME, helper.COL_PATH, helper.COL_HEIGHT, helper.COL_WIDTH, helper.COL_CAPTION, helper.COL_SIZE}, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                Image image = new Image();

                int id_index = cursor.getColumnIndex(helper.COL_ID);
                int name_index = cursor.getColumnIndex(helper.COL_NAME);
                int path_index = cursor.getColumnIndex(helper.COL_PATH);
                int height_index = cursor.getColumnIndex(helper.COL_HEIGHT);
                int width_index = cursor.getColumnIndex(helper.COL_WIDTH);
                int caption_index = cursor.getColumnIndex(helper.COL_CAPTION);
                int size_index = cursor.getColumnIndex(helper.COL_SIZE);

                image.setId(cursor.getString(id_index));
                image.setName(cursor.getString(name_index));
                image.setPath(cursor.getString(path_index));
                image.setHeight(cursor.getString(height_index));
                image.setWidth(cursor.getString(width_index));
                image.setSize(cursor.getString(size_index));
                if (cursor.getString(caption_index) != null) {
                    image.setCaption(cursor.getString(caption_index));
                }
                imageList.add(image);
            }
        }

        return imageList;
    }

    public boolean addImage(String name, String path, String height, String width, String size) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.COL_NAME, name);
        contentValues.put(helper.COL_PATH, path);
        contentValues.put(helper.COL_HEIGHT, height);
        contentValues.put(helper.COL_WIDTH, width);
        contentValues.put(helper.COL_SIZE, size);
        contentValues.put(helper.COL_CAPTION, "No Captions");

        return database.insert(helper.TABLE_NAME, null, contentValues) > 0;
    }

    public boolean deleteByName(String name) {
        return database.delete(helper.TABLE_NAME, helper.COL_NAME + " = ?", new String[]{name}) > 0;
    }


    public boolean addCaption(String id, String caption) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(helper.COL_CAPTION, caption);

        return database.update(helper.TABLE_NAME, contentValues, helper.COL_ID + "=?", new String[]{id}) > 0;
    }

    public boolean numOfImages() {

        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + helper.TABLE_NAME, null);
        return cursor.moveToFirst();

    }


    private static class DBHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "images.db";
        private static final int DB_VERSION = 2;

        private static final String TABLE_NAME = "saved_images_table";
        private static final String COL_ID = "_ID";
        private static final String COL_NAME = "col_name";
        private static final String COL_PATH = "col_path";
        private static final String COL_HEIGHT = "col_height";
        private static final String COL_WIDTH = "col_width";
        private static final String COL_CAPTION = "col_caption";
        private static final String COL_SIZE = "col_size";

        private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_PATH + " TEXT NOT NULL, " +
                COL_HEIGHT + " TEXT NOT NULL, " +
                COL_WIDTH + " TEXT NOT NULL, " +
                COL_CAPTION + " TEXT NOT NULL, " +
                COL_SIZE + " TEXT NOT NULL );";

        private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(QUERY_CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(QUERY_DROP_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}