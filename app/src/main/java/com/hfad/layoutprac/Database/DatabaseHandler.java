package com.hfad.layoutprac.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import com.hfad.layoutprac.Info;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tinder";
    public static final String FILE_DIR = "tinder";
    public static SQLiteDatabase db;
    public Context dbContext;

    private final static String TAG = "Frag";
    public static final String TABLE_Info = "tblinfo";



    private static final String id = "id";
    private static final String title = "title";
    private static final String type = "type";
    private static final String pathimagename = "pathimage";
    private static final String status = "status";
    private static final String sender = "sender";
    private static final String favroite = "favroite";

    String CREATE__TABLE_Info = "CREATE TABLE IF NOT EXISTS " + TABLE_Info + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + title + " TEXT,"
            + type + " TEXT,"
            + pathimagename + " TEXT,"
            + status + " TEXT ,"
            + sender + " TEXT ,"
            + favroite + " TEXT "
            + ")";

    public DatabaseHandler(Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "blogdata1 Constructor#000dbhan");
        this.dbContext = context;
        Log.d(TAG, "blogdata1 Constructor Got Context#000dbhan");
        this.db = this.getWritableDatabase();
        Log.d(TAG, "blogdata1 Constructor done#000dbhan");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        Log.d(TAG, "Database99 Create Start :#000create");
        db.execSQL(CREATE__TABLE_Info);
        Log.d(TAG, "Database99 Create category table created#000create" + CREATE__TABLE_Info);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertInfo(Info infodata) {

        Log.d(TAG, "Info method Into Insert...#000Insert");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title, "");
        values.put(type, "image");
        values.put(pathimagename, infodata.getPathImagename());
        values.put(status, "1");
        values.put(sender, "");
        values.put(favroite, "myfav");
        db.insert(TABLE_Info, null, values);
        Log.d(TAG, "InfoyData Successfully Data Inserted...#000Insert" + infodata.getPathImagename());
    }


    public ArrayList<Info> getInfoListMy() {
        ArrayList<Info> procat = new ArrayList<Info>();
        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_Info + "  ORDER BY "+ id +" desc";
            Toast.makeText(dbContext, "dbproduct", Toast.LENGTH_SHORT).show();
            Log.d("DatabaseHanler","Stack007 DBhanlder Sartt query");

            Cursor cursor = db.rawQuery(selectQuery, null);
            SQLiteDatabase db = this.getWritableDatabase();

            if (cursor.moveToFirst()) {
                do {
                    //        Log.d(TAG, "ServiceBranch Branch getCount::"+cursor.getCount());
                    Info product = new Info();
                    product.setId(cursor.getString(cursor.getColumnIndex(id)));
                    //   product.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                    //  product.setType(cursor.getString(cursor.getColumnIndex(type)));
                    product.setPathImagename(cursor.getString(cursor.getColumnIndex(pathimagename)));
                    //   product.setPathImagename(cursor.getString(cursor.getColumnIndex(favroite)));


                    product.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                    Log.d("DatabaseHanler","Stack007 DBhanlder ImgPath:" + cursor.getString(cursor.getColumnIndex(pathimagename)));
                    Log.d("DatabaseHanler","Stack007 DBhanlder Fav:" + cursor.getString(cursor.getColumnIndex(favroite)));

                    procat.add(product);
                    Log.d(TAG, "DashUser movenext::");
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return procat;
        } catch (Exception e) {

            Log.e("all_offers", "" + e);
        }

        return procat;
    }

    public ArrayList<Info> MyFavroites() {
        ArrayList<Info> procat = new ArrayList<Info>();
        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_Info + " where " +favroite+"='fav'  ORDER BY "+ id +" desc";
            Toast.makeText(dbContext, "dbproduct", Toast.LENGTH_SHORT).show();
            Log.d("Info#000", "selectquery#000" + selectQuery);

            Cursor cursor = db.rawQuery(selectQuery, null);
            SQLiteDatabase db = this.getWritableDatabase();

            if (cursor.moveToFirst()) {
                do {
                    //        Log.d(TAG, "ServiceBranch Branch getCount::"+cursor.getCount());
                    Info product = new Info();
                    product.setId(cursor.getString(cursor.getColumnIndex(id)));
                    //   product.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                    //  product.setType(cursor.getString(cursor.getColumnIndex(type)));
                    product.setPathImagename(cursor.getString(cursor.getColumnIndex(pathimagename)));
                    //   product.setPathImagename(cursor.getString(cursor.getColumnIndex(favroite)));


                    product.setStatus(cursor.getString(cursor.getColumnIndex(status)));
                    Log.d("cursor value#000", cursor.getString(cursor.getColumnIndex(pathimagename)));
                    Log.d("cursor fav value#000", cursor.getString(cursor.getColumnIndex(favroite)));
                    procat.add(product);
                    Log.d(TAG, "DashUser movenext::");
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return procat;
        } catch (Exception e) {

            Log.e("all_offers", "" + e);
        }

        return procat;
    }
    public ArrayList<Info> getInfoList() {
        ArrayList<Info> procat = new ArrayList<Info>();

        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_Info;
            Toast.makeText(dbContext, "dbproduct", Toast.LENGTH_SHORT).show();
            Log.d("product", "Productlist" + selectQuery);

            Cursor cursor = db.rawQuery(selectQuery, null);
            SQLiteDatabase db = this.getWritableDatabase();

            if (cursor.moveToFirst()) {
                do {
                    Info product = new Info();
                    product.setId(cursor.getString(cursor.getColumnIndex(id)));

                    //product.setProd_id(cursor.getString(cursor.getColumnIndex(PROD_ID)));
                    product.setTitle(cursor.getString(cursor.getColumnIndex(title)));
                    product.setType(cursor.getString(cursor.getColumnIndex(type)));
                    product.setPathImagename(cursor.getString(cursor.getColumnIndex(pathimagename)));
                    product.setSender(cursor.getString(cursor.getColumnIndex(sender)));


                    product.setStatus(cursor.getString(cursor.getColumnIndex(status)));



                    Log.d("Infoname#000", cursor.getString(cursor.getColumnIndex(pathimagename)));
                    Log.d("Infonameid#000", cursor.getString(cursor.getColumnIndex(id)));
                   /* Log.d("productprice#333", cursor.getString(cursor.getColumnIndex(PROD_PRICE)));
                    Log.d("productdescription#333", cursor.getString(cursor.getColumnIndex(PROD_DESC)));
                    Log.d("productquantity#333", cursor.getString(cursor.getColumnIndex(PROD_QUANTITY)));*/

                    procat.add(product);
                    Log.d(TAG, "DashUser movenext::");
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return procat;
        } catch (Exception e) {

            Log.e("all_offers", "" + e);
        }

        return procat;
    }


    /* public void deleteProductTable(){
         SQLiteDatabase db= this.getWritableDatabase();
         db.execSQL("delete from " + TABLE_PRODUCTS);
         db.close();
     }*/
    public void deleteASingleRow(String strID){
        SQLiteDatabase db= this.getWritableDatabase();
        Log.d("Infodelete#000", "delete from "+TABLE_Info+" where "+id+"='"+strID+"'");
        db.execSQL("delete from "+TABLE_Info+" where "+id+"='"+strID+"'");
        db.close();
    }

    public void updatedata(String strID){
        SQLiteDatabase db= this.getWritableDatabase();
        Log.d("Infoupdate#000", " UPDATE "+TABLE_Info+" SET "+favroite+"='fav'"+" where "+id+"='"+strID+"'");
        db.execSQL(" UPDATE "+TABLE_Info+" SET "+favroite+"='fav'"+" where "+id+"='"+strID+"'");
        db.close();
    }
}