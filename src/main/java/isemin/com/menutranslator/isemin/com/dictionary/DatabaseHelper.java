package isemin.com.menutranslator.isemin.com.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import isemin.com.menutranslator.Item;

/**
 * Created by Shahruk on 4/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static String DATABASE_NAME = "dict";

    // Current version of database
    private static final int DATABASE_VERSION = 1;

    // Name of table
    private static final String TABLE_DICT = "dictionary";
    private static final String TABLE_RES = "restaurant";
    private static final String TABLE_SHOP = "shopping";
    private static final String TABLE_GENERAL = "general";



    // All Keys used in dictionary table
    private static final String KEY_WORD = "word";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DEFINITION = "definition";

    // All Keys used in restaurant, shopping, general table
    private static final String KEY_RES_HW = "headword";
    private static final String KEY_RES_MY = "malay";
    private static final String KEY_RES_FR = "french";
    private static final String KEY_RES_SP = "spanish";
    private static final String KEY_RES_IT = "italian";




    public static String TAG = "tag";

    private static final String CREATE_TABLE_DICTIONARY = "CREATE TABLE "
            + TABLE_DICT + "(" + KEY_WORD
            + " TEXT, " + KEY_TYPE + " TEXT,"
            + KEY_DEFINITION + " TEXT);";

    private static final String CREATE_TABLE_RES = "CREATE TABLE "
            + TABLE_RES + "(" + KEY_RES_HW
            + " TEXT," + KEY_RES_MY + " TEXT,"
            + KEY_RES_FR + " TEXT," +  KEY_RES_SP + " TEXT," + KEY_RES_IT + " TEXT);";

    private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
            + TABLE_SHOP + "(" + KEY_RES_HW
            + " TEXT," + KEY_RES_MY + " TEXT,"
            + KEY_RES_FR + " TEXT," +  KEY_RES_SP + " TEXT," + KEY_RES_IT + " TEXT);";

    private static final String CREATE_TABLE_GENERAL = "CREATE TABLE "
            + TABLE_GENERAL + "(" + KEY_RES_HW
            + " TEXT," + KEY_RES_MY + " TEXT,"
            + KEY_RES_FR + " TEXT," +  KEY_RES_SP + " TEXT," + KEY_RES_IT + " TEXT);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called by system if the database is accessed but not yet
     * created.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(CREATE_TABLE_STATIONS);// create stations table
        db.execSQL(CREATE_TABLE_RES);
        db.execSQL(CREATE_TABLE_SHOP);
        db.execSQL(CREATE_TABLE_GENERAL);
        db.execSQL(CREATE_TABLE_DICTIONARY);



    }

    /**
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STATIONS); // drop table if exists
        //db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_REVIEWS); // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENERAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICT);



        onCreate(db);
    }

    /**
     *
     * This method is used to add dict detail in stations Table
     *
     * @param
     * @return
     */


    public long addDictWord(Dict dict) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, dict.word);
        values.put(KEY_DEFINITION, dict.definition);
        values.put(KEY_TYPE, dict.type);

        long insert = db.insert(TABLE_DICT, null, values);

        return insert;
    }


    public long addRestaurant(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_RES_HW, item.HeadSentence);
        values.put(KEY_RES_MY, item.Malay);
        values.put(KEY_RES_FR, item.French);
        values.put(KEY_RES_SP, item.Spanish);
        values.put(KEY_RES_IT, item.Italian);



        long insert = db.insert(TABLE_RES, null, values);

        return insert;
    }

    public long addShopping(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_RES_HW, item.HeadSentence);
        values.put(KEY_RES_MY, item.Malay);
        values.put(KEY_RES_FR, item.French);
        values.put(KEY_RES_SP, item.Spanish);
        values.put(KEY_RES_IT, item.Italian);



        long insert = db.insert(TABLE_SHOP, null, values);

        return insert;
    }

    public long addGeneral(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_RES_HW, item.HeadSentence);
        values.put(KEY_RES_MY, item.Malay);
        values.put(KEY_RES_FR, item.French);
        values.put(KEY_RES_SP, item.Spanish);
        values.put(KEY_RES_IT, item.Italian);



        long insert = db.insert(TABLE_GENERAL, null, values);

        return insert;
    }


    /**
     * This method is used to update particular dict entry
     *
     * @param dict
     * @return
     */
    public int updateEntry(Dict dict) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, dict.type);
        values.put(KEY_DEFINITION, dict.definition);
        values.put(KEY_TYPE, dict.type);

        // update row in stations table base on dict.is value
        return db.update(TABLE_DICT, values, KEY_WORD + " = ?",
                new String[] { String.valueOf(dict.word) });
    }

    /**
     * Used to delete particular station entry
     *
     * @param name
     */
    public void deleteEntry(String name) {

        // delete row in stations table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DICT, KEY_DEFINITION + " = ?",
                new String[] { name });
    }

    /**
     * Used to get particular station details
     *
     * @param id
     * @return
     */

    public Dict getWord(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT * FROM stations WHERE id = ?;
        String selectQuery = "SELECT  * FROM " + TABLE_DICT + " WHERE "
                + KEY_WORD + " = " + id;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Dict dict = new Dict();
        dict.word = c.getString(c.getColumnIndex(KEY_WORD));
        dict.type = c.getString(c.getColumnIndex(KEY_TYPE));
        dict.definition = c.getString(c.getColumnIndex(KEY_DEFINITION));
        return dict;
    }



    /**
     * Used to get particular station details
     *
     * @param name
     * @return
     */

    public Dict getAWord(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT * FROM stations WHERE id = ?;
        String selectQuery = "SELECT  * FROM " + TABLE_DICT + " WHERE "
                + KEY_WORD + " = '" + name + "'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) c.moveToFirst();
        Dict dict = new Dict();
        dict.word = c.getString(c.getColumnIndex(KEY_WORD));
        dict.type = c.getString(c.getColumnIndex(KEY_TYPE));
        dict.definition = c.getString(c.getColumnIndex(KEY_DEFINITION));
        return dict;
    }

    /**
     * Used to get detail of entire database and save in array list of data type
     * StationsModel
     *
     * @return
     */
    public List<Dict> getAllWordList() {
        List<Dict> wordsArrayList = new ArrayList<Dict>();

        String selectQuery = "SELECT  * FROM " + TABLE_DICT;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Dict dict = new Dict();
                dict.word = c.getString(c.getColumnIndex(KEY_WORD));
                dict.type = c.getString(c.getColumnIndex(KEY_TYPE));
                dict.definition = c.getString(c.getColumnIndex(KEY_DEFINITION));


                // adding to Stations list
                wordsArrayList.add(dict);
            } while (c.moveToNext());
        }

        return wordsArrayList;
    }

        public List<Item> getAllRestaurentList() {
        List<Item> resArrayList = new ArrayList<Item>();

        String selectQuery = "SELECT  * FROM " + TABLE_RES;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Item item = new Item();
                item.HeadSentence = c.getString(c.getColumnIndex(KEY_RES_HW));
                item.Malay = c.getString(c.getColumnIndex(KEY_RES_MY));
                item.French = c.getString(c.getColumnIndex(KEY_RES_FR));
                item.Spanish = c.getString(c.getColumnIndex(KEY_RES_SP));
                item.Italian = c.getString(c.getColumnIndex(KEY_RES_IT));


                // adding to Stations list
                resArrayList.add(item);
            } while (c.moveToNext());
        }

        return resArrayList;
    }

    public List<Item> getAllShoppingList() {
        List<Item> shopArrayList = new ArrayList<Item>();

        String selectQuery = "SELECT  * FROM " + TABLE_SHOP;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Item item = new Item();
                item.HeadSentence = c.getString(c.getColumnIndex(KEY_RES_HW));
                item.Malay = c.getString(c.getColumnIndex(KEY_RES_MY));
                item.French = c.getString(c.getColumnIndex(KEY_RES_FR));
                item.Spanish = c.getString(c.getColumnIndex(KEY_RES_SP));
                item.Italian = c.getString(c.getColumnIndex(KEY_RES_IT));


                // adding to Stations list
                shopArrayList.add(item);
            } while (c.moveToNext());
        }

        return shopArrayList;
    }


    public List<Item> getAllGeneralList() {
        List<Item> generalArrayList = new ArrayList<Item>();

        String selectQuery = "SELECT  * FROM " + TABLE_GENERAL;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Item item = new Item();
                item.HeadSentence = c.getString(c.getColumnIndex(KEY_RES_HW));
                item.Malay = c.getString(c.getColumnIndex(KEY_RES_MY));
                item.French = c.getString(c.getColumnIndex(KEY_RES_FR));
                item.Spanish = c.getString(c.getColumnIndex(KEY_RES_SP));
                item.Italian = c.getString(c.getColumnIndex(KEY_RES_IT));


                // adding to Stations list
                generalArrayList.add(item);
            } while (c.moveToNext());
        }

        return generalArrayList;
    }

    //Special
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
