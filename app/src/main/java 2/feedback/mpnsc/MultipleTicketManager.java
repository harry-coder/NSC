package feedback.mpnsc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sushily on 09-06-2015.
 */
public class MultipleTicketManager {
    public static final     String  MYDATABASE_NAME              ="TICKETLISTDB";
    public static final     int     MYDATABASE_VERSION           = 1;
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Master table to store field data &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    public static final String TBL_TICKETLIST           ="TBL_TICKETLIST";
    public static final String KEY_TICKETLISTID         ="KEY_TICKETLISTID";
    public static final String TICKETNUMBER             ="TICKETNUMBER";
    public static final String CUSTNAME                 ="CUSTNAME";

    private static final String MASTERTABLELIST="CREATE TABLE "+TBL_TICKETLIST +
            "(\n" +
            "  KEY_TICKETLISTID         INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL,\n" +
            "  TICKETNUMBER             VARCHAR2(20),\n" +
            "  CUSTNAME                 VARCHAR2(25)\n" +
            ");";

    public Cursor ticketlistoncursorAll(){
        String[] columns = new String[]{KEY_TICKETLISTID ,TICKETNUMBER ,CUSTNAME };
        Cursor cursor = sqLiteDatabase.query(TBL_TICKETLIST, columns, null, null, null, null, null);
        return cursor;
    }
   public void ticketlist_delete(int id){
        sqLiteDatabase.delete(TBL_TICKETLIST, KEY_TICKETLISTID+"="+id, null);
   }
    public long ticketlist_insert(String   content1  ,
                                  String   content2  )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         TICKETNUMBER             ,  content1     );
        contentValues.put  (         CUSTNAME                 ,  content2     );

        return sqLiteDatabase.insert(TBL_TICKETLIST, null, contentValues);
    }
    //#############################################   TABLE FINISH   #########################################
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    public MultipleTicketManager(Context c)
    {
        context = c;
    }
    public MultipleTicketManager openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }
    public MultipleTicketManager openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        sqLiteHelper.close();
    }
    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(MASTERTABLELIST);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TBL_TICKETLIST);
            onCreate(db);
    }
  }
}