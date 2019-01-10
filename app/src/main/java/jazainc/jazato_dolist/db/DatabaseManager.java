package jazainc.jazato_dolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static jazainc.jazato_dolist.db.DatabaseHelper.DATE;
import static jazainc.jazato_dolist.db.DatabaseHelper.DATE2;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String desc, String date, String time, String status) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, desc);
        contentValue.put(DATE, date);
        contentValue.put(DatabaseHelper.TIME, time);
        contentValue.put(DatabaseHelper.STATUS, status);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);}

    public void insert2(String title, String desc, String date, String time){

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TITLE, title);
        contentValue.put(DatabaseHelper.SUBJECT2, desc);
        contentValue.put(DatabaseHelper.DATE2, date);
        contentValue.put(DatabaseHelper.TIME2, time);
        database.insert(DatabaseHelper.TABLE_NAME2, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DATE, DatabaseHelper.TIME, DatabaseHelper.STATUS};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    //untuk table memo

    public Cursor fetch2(){
        String[] columns2 = new String[] { DatabaseHelper._ID2, DatabaseHelper.TITLE, DatabaseHelper.SUBJECT2, DatabaseHelper.DATE2, DatabaseHelper.TIME2 };
        Cursor cursor2 = database.query(DatabaseHelper.TABLE_NAME2, columns2, null, null, null, null, DatabaseHelper.DATE2);

        if (cursor2 != null) {
            cursor2.moveToFirst();
        }
        return cursor2;
    }

    public Cursor fetchFilterAktif(){
        Cursor row = null;
        String query = "SELECT * FROM "+DatabaseHelper.TABLE_NAME+" WHERE "+DatabaseHelper.STATUS+" = 'Aktif'";
        row = database.rawQuery(query, null);
        if (row != null){
            row.moveToFirst();
        }
        return row;
    }

    public Cursor fetchFilterNonaktif(){
        Cursor row = null;
        String query = "SELECT * FROM "+DatabaseHelper.TABLE_NAME+" WHERE "+DatabaseHelper.STATUS+" = 'Nonaktif'";
        row = database.rawQuery(query, null);
        if (row != null){
            row.moveToFirst();
        }
        return row;
    }

    public int update(long _id, String subject, String date, String time, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, subject);
        contentValues.put(DATE, date);
        contentValues.put(DatabaseHelper.TIME, time);
        contentValues.put(DatabaseHelper.STATUS, status);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

//tbl memo
    public int update2(long _id, String title, String subject, String date, String time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.SUBJECT2, subject);
        contentValues.put(DatabaseHelper.DATE2, date);
        contentValues.put(DatabaseHelper.TIME2, time);
        int a = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper._ID2 + " = " + _id, null);
        return a;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
        //tblmemo
        database.delete(DatabaseHelper.TABLE_NAME2, DatabaseHelper._ID2 + "=" + _id, null);
    }

    public Cursor fetchFilter(String input){
        Cursor row =null;
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;
        if(input == null || input.length()==0){
            row = database.rawQuery(query, null);
        }else{
            query = "SELECT * FROM "+DatabaseHelper.TABLE_NAME+" WHERE "+DatabaseHelper.SUBJECT+" like '%"+input+"%'";
            row = database.rawQuery(query, null);
        }
        if (row!=null){
            row.moveToFirst();
        }

        return row;
    }
    public Cursor fetchFilter1(String input){
        Cursor row =null;
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME2;
        if(input == null || input.length()==0){
            row = database.rawQuery(query, null);
        }else{
            query = "SELECT * FROM "+DatabaseHelper.TABLE_NAME2+" WHERE "+DatabaseHelper.SUBJECT+" OR "+DatabaseHelper.TITLE+" like '%"+input+"%'";
            row = database.rawQuery(query, null);
        }
        if (row!=null){
            row.moveToFirst();
        }

        return row;
    }


}
