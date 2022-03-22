package localstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppDB";
    private static final String TABLE_USERS = "Users";
    private static final String ROW_ID = "Id";
    private static final String ROW_USERNAME = "ad";
    private static final int DATABASE_VERSION = 1;

    SQLiteDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_USERNAME + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

    public void Insert(String adSoyad){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_USERNAME, adSoyad);
            db.insert(TABLE_USERS,null,cv);
        }catch (Exception e){
        }
        db.close();
    }

    public List<String> Read(){
        List<String> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] sutunlar = {ROW_ID,ROW_USERNAME};
            Cursor cursor = db.query(TABLE_USERS, sutunlar,null,null,null,null,"id");
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0) + " - " + cursor.getString(1));
            }
        }catch (Exception e){
        }
        db.close();
        return veriler;
    }

    public void Remove(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_ID + " = " + id ;
            db.delete(TABLE_USERS,where,null);
        }catch (Exception e){
        }
        db.close();
    }

    public void UPDATE(int id, String adSoyad){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_USERNAME, adSoyad);
            String where = ROW_ID +" = '"+ id + "'";
            db.update(TABLE_USERS,cv,where,null);
        }catch (Exception e){
        }
        db.close();
    }
}
