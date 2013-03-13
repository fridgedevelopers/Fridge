
package com.fridge.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class FridgeHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_PATH = "/data/data/com.fridge/databases/";

    private static final int DATABASE_VERSION = 22;

    private static final String DATABASE_NAME = "fridge.db";

    private SQLiteDatabase database;

    private Context context;

    public FridgeHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();

        if(!dbExist)
        {
            getReadableDatabase();

            try
            {
                copyDataBase();
            }
            catch(IOException e)
            {
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException
    {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while((length = myInput.read(buffer)) > 0)
            myOutput.write(buffer, 0, length);

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            //checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e)
        {

        }

        if(checkDB != null) checkDB.close();

        return checkDB != null ? true : false;
    }

    public SQLiteDatabase openDataBase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        //database = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    @Override
    public synchronized void close()
    {
        if(database != null)
            database.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}