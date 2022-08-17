package moji.physics.sqlliteapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_PRODUCT_PRICE = "PRODUCT_PRICE";//2021-09-13
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //this is called the 1st time a DB is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME + " TEXT," +
                COLUMN_CUSTOMER_AGE + " INT," + COLUMN_PRODUCT_PRICE + " INT," +
                COLUMN_ACTIVE_CUSTOMER + " BOOL)";//2021-09-13
        db.execSQL(createTableStatement);
    }

    //this is called if the DB version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //id will not be added 'cuz it will autoincr
        cv.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        cv.put(COLUMN_PRODUCT_PRICE,customerModel.getPrice());//2021-09-13
        //cv.put();
        cv.put(COLUMN_ACTIVE_CUSTOMER,customerModel.isActive());
        long insert=db.insert(CUSTOMER_TABLE,null,cv);
        if (insert == -1) {return false;}
        else{return true;}
    }

    public boolean deleteOne(CustomerModel customerModel){
        //find customerModel in DB, if found delete
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStr = "DELETE FROM "+ CUSTOMER_TABLE+ " WHERE "+ COLUMN_ID+ " = "+ customerModel.getId();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()){return true;}
        else{return false;}
    }

    public Cursor sumAllPrices(CustomerModel customerModel){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStr="SELECT SUM ("+ customerModel.getSubTotal() + ") AS myTotal FROM "+ CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(queryStr,null);
        if( cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    /*@SuppressLint("Range")
    public int sumPrices(CustomerModel customerModel){
        //* Sum all the prices listed in the table
        * it works, however, it takes for the total value the number of items in the table as a factor
        * for a single item returns the correct total //
        SQLiteDatabase db = this.getReadableDatabase();
        int mySum = 0;
        String queryStr="SELECT SUM ("+ customerModel.getPrice() + ") AS myTotal FROM "+ CUSTOMER_TABLE;
        Cursor cursor = db.rawQuery(queryStr,null);
        if(cursor.moveToFirst())
            mySum=mySum+cursor.getInt(cursor.getColumnIndex("myTotal"));

        cursor.close();
        db.close();

        return mySum;
    }*/

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        //get data from DB
        String queryStr = "SELECT * FROM "+ CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr,null);
        if (cursor.moveToFirst()){
            /* loop through the results and create a new customer */
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                int product_price = cursor.getInt(3);//2021-09-13
                int subTotalPrice = cursor.getInt(4);
                boolean customerActive = cursor.getInt(5)== 1 ? true: false;
                CustomerModel newCustomer = new CustomerModel(customerID,customerName,customerAge,product_price,subTotalPrice,customerActive);
                returnList.add(newCustomer);
            }while (cursor.moveToNext());
        }
        else{
            //failure. do not add anything on the list.
        }
        //close the cursor and db
        cursor.close();
        db.close();
        return returnList;
    }
}
