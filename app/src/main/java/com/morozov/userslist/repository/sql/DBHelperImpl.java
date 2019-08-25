package com.morozov.userslist.repository.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import com.morozov.userslist.models.FriendModel;
import com.morozov.userslist.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelperImpl extends SQLiteOpenHelper implements DBHelper {

    private Context mContext;

    public static final String DATABASE_NAME = "UsersList.db";
    private static final int DATABASE_VERSION = 1;

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "UsersList";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ACTIVE = "isActive";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EYE_COLOR = "eyeColor";
        public static final String COLUMN_FRUIT = "favoriteFruit";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_REGISTERED = "registered";
        public static final String COLUMN_ABOUT = "about";
        public static final String COLUMN_FRIENDS = "friends";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String NUMERIC_TYPE = " NUMERIC";

    private static final String NOT_NULL = " NOT NULL";
    public static final String PRIMARY_KEY = " PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperItem.TABLE_NAME + " ("
                    + DBHelperItem._ID + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                    + DBHelperItem.COLUMN_ID + INT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_ACTIVE + NUMERIC_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_AGE + INT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_EMAIL + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_PHONE + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_COMPANY + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_ADDRESS + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_EYE_COLOR + INT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_FRUIT + INT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_LATITUDE + REAL_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_LONGITUDE + REAL_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_REGISTERED + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_ABOUT + TEXT_TYPE + NOT_NULL + COMMA_SEP
                    + DBHelperItem.COLUMN_FRIENDS + TEXT_TYPE
                    + ");";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBHelperItem.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelperImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public UserModel getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                DBHelperItem._ID,
                DBHelperItem.COLUMN_ID,
                DBHelperItem.COLUMN_ACTIVE,
                DBHelperItem.COLUMN_NAME,
                DBHelperItem.COLUMN_AGE,
                DBHelperItem.COLUMN_EMAIL,
                DBHelperItem.COLUMN_PHONE,
                DBHelperItem.COLUMN_COMPANY,
                DBHelperItem.COLUMN_ADDRESS,
                DBHelperItem.COLUMN_EYE_COLOR,
                DBHelperItem.COLUMN_FRUIT,
                DBHelperItem.COLUMN_LATITUDE,
                DBHelperItem.COLUMN_LONGITUDE,
                DBHelperItem.COLUMN_REGISTERED,
                DBHelperItem.COLUMN_ABOUT,
                DBHelperItem.COLUMN_FRIENDS
        };
        Cursor cursor = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToPosition(position)) {
            UserModel user = new UserModel();

            user.setId(cursor.getInt(cursor.getColumnIndex(DBHelperItem.COLUMN_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_NAME)));
            user.setAge(cursor.getInt(cursor.getColumnIndex(DBHelperItem.COLUMN_AGE)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_PHONE)));
            user.setCompany(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_COMPANY)));
            user.setAddress(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_ADDRESS)));
            user.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBHelperItem.COLUMN_LATITUDE)));
            user.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBHelperItem.COLUMN_LONGITUDE)));
            user.setRegistered(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_REGISTERED)));
            user.setAbout(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_ABOUT)));
            user.setEyeColor(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_EYE_COLOR)));
            user.setFavoriteFruit(cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_FRUIT)));

            Boolean isActive = 1 == cursor.getInt(cursor.getColumnIndex(DBHelperItem.COLUMN_ACTIVE));
            user.setIsActive(isActive);

            List<FriendModel> friends = new ArrayList<>();
            for ( String friend : cursor.getString(cursor.getColumnIndex(DBHelperItem.COLUMN_FRIENDS)).split(" ")) {
                try {
                    friends.add(new FriendModel(Integer.parseInt(friend)));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Некорректные данные в БД.", Toast.LENGTH_SHORT).show();
                }
            }
            user.setFriendModels(friends);

            cursor.close();
            db.close();
            return user;
        }
        return null;
    }

    @Override
    public void removeItemWithId(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = { String.valueOf(id) };
        db.delete(DBHelperItem.TABLE_NAME, "_ID=?", whereArgs);
    }

    @Override
    public void removeAll() {
        getWritableDatabase().execSQL("DELETE FROM " + DBHelperItem.TABLE_NAME);
    }

    @Override
    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { DBHelperItem._ID };
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    @Override
    public long addUser(UserModel user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelperItem.COLUMN_ID, user.getId());
        contentValues.put(DBHelperItem.COLUMN_NAME, user.getName());
        contentValues.put(DBHelperItem.COLUMN_AGE, user.getAge());
        contentValues.put(DBHelperItem.COLUMN_EMAIL, user.getEmail());
        contentValues.put(DBHelperItem.COLUMN_PHONE, user.getPhone());
        contentValues.put(DBHelperItem.COLUMN_COMPANY, user.getCompany());
        contentValues.put(DBHelperItem.COLUMN_ADDRESS, user.getAddress());
        contentValues.put(DBHelperItem.COLUMN_EYE_COLOR, user.getEyeColor());
        contentValues.put(DBHelperItem.COLUMN_FRUIT, user.getFavoriteFruit());
        contentValues.put(DBHelperItem.COLUMN_LATITUDE, user.getLatitude());
        contentValues.put(DBHelperItem.COLUMN_LONGITUDE, user.getLongitude());
        contentValues.put(DBHelperItem.COLUMN_REGISTERED, user.getRegistered());
        contentValues.put(DBHelperItem.COLUMN_ABOUT, user.getAbout());

        int activeInt = (user.getIsActive()) ? 1 : 0;
        contentValues.put(DBHelperItem.COLUMN_ACTIVE, activeInt);

        StringBuilder friendsId = new StringBuilder();
        for (FriendModel friend : user.getFriendModels()) {
            friendsId.append(friend.getId()).append(" ");
        }
        contentValues.put(DBHelperItem.COLUMN_FRIENDS, friendsId.toString());

        long rowId = db.insert(DBHelperItem.TABLE_NAME, null, contentValues);

        return rowId;
    }
}
