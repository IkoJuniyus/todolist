package jazainc.jazato_dolist.db;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "TASKS";
    public static final String TABLE_NAME2 = "Memo";

    // Table columns
    public static final String _ID = "_id";
    public static final String SUBJECT = "tasks";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String STATUS = "status";

    public static final String _ID2 = "_id";
    public static final String TITLE = "title";
    public static final String SUBJECT2 = "tasks";
    public static final String DATE2 = "date";
    public static final String TIME2 = "time";

//    public static final String STATUS = "status";

    // Database Information
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";



    // database version
    static final int DB_VERSION = 1;


    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DATE + " TEXT, "+TIME+" TEXT ,"+STATUS+" TEXT);";

    private static final String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "(" + _ID2
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + SUBJECT2 + " TEXT, "+DATE2+" TEXT, "+ TIME2 +" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }
}
