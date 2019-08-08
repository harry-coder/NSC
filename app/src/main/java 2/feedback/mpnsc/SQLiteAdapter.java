package feedback.mpnsc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by sushily on 28-05-2015.
 */
public class SQLiteAdapter {

    public static final String MYDATABASE_NAME = "DEMO";
    public static final int MYDATABASE_VERSION = 1;
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Master table to store field data &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    public static final String TICKETNUMBER = "TICKETNUMBER";
    public static final String CUSTACCUNT = "CUSTACCUNT";
    public static final String CUSTGROUP = "CUSTGROUP";
    public static final String DEVICEID = "DEVICEID";
    public static final String APPLICATIONNO = "APPLICATIONNO";
    public static final String OWNERSHIP = "OWNERSHIP";
    public static final String PANCODE = "PANCODE";
    public static final String PHASE = "PHASE";
    public static final String LOADREQUIRED = "LOADREQUIRED";
    public static final String REQUIREDLOADUNIT = "REQUIREDLOADUNIT";
    public static final String CAPACITY = "CAPACITY";
    public static final String REQUIREDUNIT = "REQUIREDUNIT";
    public static final String APPLIEDSANCTIONDEMAND = "APPLIEDSANCTIONDEMAND";
    public static final String SUPPLYLEVEL = "SUPPLYLEVEL";
    public static final String CUSTCATGROUP = "CUSTCATGROUP";
    public static final String MD_CODE = "MD_CODE";
    public static final String DIV_CODE = "DIV_CODE";
    public static final String SUB_DIV_CODE = "SUB_DIV_CODE";
    public static final String SEC_CODE = "SEC_CODE";
    public static final String SEC_NAME = "SEC_NAME";
    public static final String NEWFIRSTNAME = "NEWFIRSTNAME";
    public static final String NEWLASTNAME = "NEWLASTNAME";
    public static final String NEWMIDDLENAME = "NEWMIDDLENAME";
    public static final String OLDBUILDINGCOMPLIMENT = "OLDBUILDINGCOMPLIMENT";
    public static final String OLDCITY = "OLDCITY";
    public static final String OLDCOUNTRYREGIONID = "OLDCOUNTRYREGIONID";
    public static final String OLDDISTRICT = "OLDDISTRICT";
    public static final String OLDSTATE = "OLDSTATE";
    public static final String OLDSTREET = "OLDSTREET";
    public static final String OLDZIPCODE = "OLDZIPCODE";
    public static final String ADDRESSREFRECID = "ADDRESSREFRECID";
    public static final String APPLICATIONSTATUS = "APPLICATIONSTATUS";
    public static final String CONNECTIONID = "CONNECTIONID";
    public static final String COPYOFITSREQUIREDFEDBACK = "COPYOFITSREQUIREDFEDBACK";
    public static final String APPLICATIONDATE = "APPLICATIONDATE";
    public static final String APPLICATIONTYPE = "APPLICATIONTYPE";
    public static final String CONNECTIONSTATUS = "CONNECTIONSTATUS";
    public static final String HTIDLEDEMANDOFFACTORY = "HTIDLEDEMANDOFFACTORY";
    public static final String HTWORKINGHOUR = "HTWORKINGHOUR";
    public static final String INDLOADFACTOR = "INDLOADFACTOR";
    public static final String METERCHANGEREQ = "METERCHANGEREQ";
    public static final String ORGANISATIONTYPE = "ORGANISATIONTYPE";
    public static final String GROUPCHANG = "GROUPCHANG";
    public static final String ISAPPROVED = "ISAPPROVED";
    public static final String ISCONFIGURATIONTERMINATED = "ISCONFIGURATIONTERMINATED";
    public static final String ISEXISTINGAGREEMENTENDED = "ISEXISTINGAGREEMENTENDED";
    public static final String ISEXISTINGDEVICEREPLACED = "ISEXISTINGDEVICEREPLACED";
    public static final String ISNEWAGREEMENTCREATED = "ISNEWAGREEMENTCREATED";
    public static final String ISNEWCONFIGURATIONCREATED = "ISNEWCONFIGURATIONCREATED";
    public static final String ISREQBLOCKUNBLOCK = "ISREQBLOCKUNBLOCK";
    public static final String ISSERIALNUMBERCHANGED = "ISSERIALNUMBERCHANGED";
    public static final String ISUPDATEBLOCKUNBLOCK = "ISUPDATEBLOCKUNBLOCK";
    public static final String ISVALIDATE = "ISVALIDATE";
    public static final String EXTERNALID1 = "EXTERNALID1";
    public static final String MESSAGE = "MESSAGE";
    public static final String REJECTIONREMARKS = "REJECTIONREMARKS";
    public static final String RESPONSIBLEJOBID = "RESPONSIBLEJOBID";
    public static final String RESPONSIBLEWORKER = "RESPONSIBLEWORKER";
    public static final String STREETNUMBER = "STREETNUMBER";
    public static final String MODIFIEDDATETIME = "MODIFIEDDATETIME";
    public static final String MODIFIEDBY = "MODIFIEDBY";
    public static final String CREATEDDATETIME = "CREATEDDATETIME";
    public static final String CREATEDBY = "CREATEDBY";
    public static final String CO_CODE = "CO_CODE";
    public static final String DISCOM_CODE = "DISCOM_CODE";
    public static final String CIRCLE_CODE = "CIRCLE_CODE";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String BLOCK = "BLOCK";
    public static final String BUILDING = "BUILDING";
    public static final String CITY = "CITY";
    public static final String DISTRICT = "DISTRICT";
    public static final String GP = "GP";
    public static final String PLOT = "PLOT";
    public static final String STREET = "STREET";
    public static final String TEHSIL = "TEHSIL";
    public static final String VILLAGE = "VILLAGE";
    public static final String BLOCK1 = "BLOCK1";
    public static final String CITY1 = "CITY1";
    public static final String DISTRICT1 = "DISTRICT1";
    public static final String GP1 = "GP1";
    public static final String PLOT1 = "PLOT1";
    public static final String STREET1 = "STREET1";
    public static final String TEHSIL1 = "TEHSIL1";
    public static final String VILLAGE1 = "VILLAGE1";
    public static final String BUILDING1 = "BUILDING1";
    public static final String ISHOOKING = "ISHOOKING";
    public static final String FATHERNAME = "FATHERNAME";
    public static final String NEARESTLANDMARK = "NEARESTLANDMARK";
    public static final String CONNECTIONTYPE = "CONNECTIONTYPE";
    public static final String COMPLAINTSUBCATEGORY = "COMPLAINTSUBCATEGORY";
    public static final String COMPLAINTDETAILS = "COMPLAINTDETAILS";
    public static final String SOURCEOFCOMPLAINT = "SOURCEOFCOMPLAINT";
    public static final String COMPLAINTCATEGORY = "COMPLAINTCATEGORY";
    public static final String COMPLAINT_CLOSEDDATE = "COMPLAINT_CLOSEDDATE";
    public static final String COMPLAINT_COMMENT = "COMPLAINT_COMMENT";
    public static final String COMPLAINT_STATUS = "COMPLAINT_STATUS";
    public static final String COMPLAINTTYPE = "COMPLAINTTYPE";
    public static final String TEMPORARYFROMDATE = "TEMPORARYFROMDATE";
    public static final String TEMPORARYTODATE = "TEMPORARYTODATE";
    public static final String COMPLAINT_CLOSEDBY = "COMPLAINT_CLOSEDBY";
    public static final String USER_COMMENT = "USER_COMMENT";
    public static final String CYCLE_ID = "CYCLE_ID";
    public static final String ROUTE_ID = "ROUTE_ID";
    public static final String CESU_CONSUMER_NO = "CESU_CONSUMER_NO";
    public static final String CONSUMER_TYPE = "CONSUMER_TYPE";
    public static final String SCEME = "SCEME";
    public static final String CONNECTION_FOR = "CONNECTION_FOR";
    public static final String ED_EXEMPTION = "ED_EXEMPTION";
    public static final String ED_EXEMPTION_PERCENTAGE = "ED_EXEMPTION_PERCENTAGE";
    public static final String NAME_OF_ORGANISATION = "NAME_OF_ORGANISATION";
    public static final String FATHERORHUSBAND_NAME = "FATHERORHUSBAND_NAME";
    public static final String TYPE_OF_ORGANISATION = "TYPE_OF_ORGANISATION";
    public static final String LANDLINE_NUMBER = "LANDLINE_NUMBER";
    public static final String TRANSFER_OWNERSHIP = "TRANSFER_OWNERSHIP";
    public static final String METERING_SIDE = "METERING_SIDE";
    public static final String TRANSFERMER_CAPACITY = "TRANSFERMER_CAPACITY";
    public static final String INDUSTRY_TYPE = "INDUSTRY_TYPE";
    public static final String NATURE_OF_JOB = "NATURE_OF_JOB";
    public static final String WORKING_HOURS = "WORKING_HOURS";
    public static final String LOAD_FACTOR = "LOAD_FACTOR";
    public static final String MAX_DEMAND = "MAX_DEMAND";
    public static final String SHIFTS = "SHIFTS";
    public static final String MD_PEAK_HOURS = "MD_PEAK_HOURS";
    public static final String SUBSTATION = "SUBSTATION";
    public static final String FEEDER = "FEEDER";
    public static final String DT = "DT";
    public static final String IS_METER = "IS_METER";
    public static final String PIN_NO = "PIN_NO";
    public static final String SUPPLY_TYPE = "SUPPLY_TYPE";
    public static final String TITLE = "TITLE";
    public static final String DESIGNATION_OF_SIGNATORY = "DESIGNATION_OF_SIGNATORY";
    public static final String SIGNATORY_NAME = "SIGNATORY_NAME";
    public static final String DEPARTMENT = "DEPARTMENT";
    public static final String PIN_NO_BILLED = "PIN_NO_BILLED";
    public static final String ORDER_NO = "ORDER_NO";
    public static final String ORDER_DATE = "ORDER_DATE";
    public static final String OTHER_CONNECTIONFOR = "OTHER_CONNECTIONFOR";
    public static final String DATEOF_POWERSUPPLY = "DATEOF_POWERSUPPLY";
    public static final String DISCON_RECON_DATE = "DISCON_RECON_DATE";
    public static final String KWH = "KWH";
    public static final String KVAH = "KVAH";
    public static final String KVARH = "KVARH";
    public static final String MDKW = "MDKW";
    public static final String MDKW_UNIT = "MDKW_UNIT";
    public static final String PEAKKWH = "PEAKKWH";
    public static final String PEAKKVAH = "PEAKKVAH";
    public static final String PEAKKVARH = "PEAKKVARH";
    public static final String PEAKMDKW = "PEAKMDKW";
    public static final String PEAKMDKWUNIT = "PEAKMDKWUNIT";
    public static final String OFFPEAKKWH = "OFFPEAKKWH";
    public static final String OFFPEAKKVAH = "OFFPEAKKVAH";
    public static final String OFFPEAKKVARH = "OFFPEAKKVARH";
    public static final String OFFPEAKMDKW = "OFFPEAKMDKW";
    public static final String OFFPEAKMDKWUNIT = "OFFPEAKMDKWUNIT";
    public static final String METER_STATUS = "METER_STATUS";
    public static final String REGISTER_NUMBER = "REGISTER_NUMBER";
    public static final String PAGE_NUMBER = "PAGE_NUMBER";
    public static final String ITEM_NUMBER = "ITEM_NUMBER";
    public static final String RECORD_UPDATED_FLAG = "RECORD_UPDATED_FLAG";

    //############################## MASTER TABLE FOR division  ########################
    public static final String TBL_DIVISION = "TBL_DIVISION";
    public static final String KEY_DIVISIONID = "KEY_DIVISIONID";
    public static final String CIRCLE_ID = "CIRCLE_ID";
    //    public static final String 	   CIRCLE_CODE="CIRCLE_CODE";
    //    public static final String     DIVISION_CODE="DIVISION_CODE";
    public static final String DIV_NAME = "DIV_NAME";
    //    public static final String     DISPLAY_CODE="DISPLAY_CODE";
    public static final String CENTER_NAME = "CENTER_NAME";

    private static final String MASTERDIVISION = "CREATE TABLE " + TBL_DIVISION +
            "(\n" +
            "  KEY_DIVISIONID INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
            "  CIRCLE_CODE    VARCHAR2(6)               NOT NULL,\n" +
            "  DIVISION_CODE  VARCHAR2(6)               NOT NULL,\n" +
            "  DIV_NAME       VARCHAR2(60),\n" +
            "  DISPLAY_CODE   VARCHAR2(6),\n" +
            "  CENTER_NAME    VARCHAR2(20)\n" +
            ");";

    public long masterdivision_insert(String content1, String content2, String content3, String content4, String content5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CIRCLE_CODE, content1);
        contentValues.put(DIVISION_CODE, content2);
        contentValues.put(DIV_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);
        contentValues.put(CENTER_NAME, content5);

        return sqLiteDatabase.insert(TBL_DIVISION, null, contentValues);
    }

    public  Cursor tariffname()
    {
      Cursor  cursor = sqLiteDatabase.rawQuery("Select * from TBL_TARIFF_CATEGORY", null);
      return cursor;
    }

    public Cursor masterdivisioncursorAll() {
        String[] columns = new String[]{KEY_DIVISIONID, CIRCLE_CODE, DIVISION_CODE, DIV_NAME, DISPLAY_CODE, CENTER_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_DIVISION, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor get_division_name(String division) {
        String selectQuery =
                DIVISION_CODE + " = '" + division + "'";
        String[] columns = new String[]{KEY_DIVISIONID, DIV_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_DIVISION, columns, selectQuery, null, null, null, null);
        return cursor;
    }


    ////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  MASTER TABLE FOR tariff %%%%%%%%%%%%%%%%%%

    public static final String TBL_TARIFF_CATEGORY = "TBL_TARIFF_CATEGORY";
    public static final String KEY_TARIFFID = "KEY_TARIFFID";
    public static final String TARIFF_CODE = "TARIFF_CODE";
    public static final String TARIFF_NAME = "TARIFF_NAME";
    public static final String CONSUMER_NAME_TARIFF = "CONSUMER_NAME_TARIFF";

    private static final String MASTERTARIFF = "CREATE TABLE " + TBL_TARIFF_CATEGORY +
            "(\n" +
            "  KEY_TARIFFID INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
            "  CONSUMER_NAME_TARIFF    VARCHAR2(6)               NOT NULL,\n" +
            "  TARIFF_CODE  VARCHAR2(20)               NOT NULL,\n" +
            "  TARIFF_NAME       VARCHAR2(60),\n" +
            "  DISPLAY_CODE   VARCHAR2(6),\n" +
            "  CENTER_NAME    VARCHAR2(20)\n" +
            ");";

    public long mastertariff_insert(String content1, String content2, String content3, String content4, String content5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONSUMER_NAME_TARIFF, content1);
        contentValues.put(TARIFF_CODE, content2);
        contentValues.put(TARIFF_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);
        contentValues.put(CENTER_NAME, content5);

        return sqLiteDatabase.insert(TBL_TARIFF_CATEGORY, null, contentValues);
    }

    public Cursor mastertariffcursorAll() {
        String[] columns = new String[]{KEY_TARIFFID, CONSUMER_NAME_TARIFF, TARIFF_CODE, TARIFF_NAME, DISPLAY_CODE, CENTER_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_TARIFF_CATEGORY, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor get_tariff_name(String tariff) {
        String selectQuery =
                TARIFF_CODE + " = '" + tariff + "'";
        String[] columns = new String[]{KEY_TARIFFID, TARIFF_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_TARIFF_CATEGORY, columns, selectQuery, null, null, null, null);
        return cursor;
    }


    ////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  MASTER TABLE FOR FEEDER %%%%%%%%%%%%%%%%%%

    public static final String TBL_FEEDER_CATEGORY = "TBL_FEEDER_CATEGORY";
    public static final String KEY_FEEDERID = "KEY_FEEDERID";
    public static final String FEEDER_CODE = "FEEDER_CODE";
    public static final String FEEDER_NAME = "FEEDER_NAME";
    public static final String CONSUMER_NAME_FEEDER = "CONSUMER_NAME_FEEDER";

    private static final String MASTERFEEDER = "CREATE TABLE " + TBL_FEEDER_CATEGORY +
            "(\n" +
            "  KEY_FEEDERID INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
            "  CONSUMER_NAME_FEEDER    VARCHAR2(6)               NOT NULL,\n" +
            "  FEEDER_CODE  VARCHAR2(30)               NOT NULL,\n" +
            "  FEEDER_NAME       VARCHAR2(60),\n" +
            "  DISPLAY_CODE   VARCHAR2(6),\n" +
            "  CENTER_NAME    VARCHAR2(20)\n" +
            ");";

    public long masterfeeder_insert(String content1, String content2, String content3, String content4, String content5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONSUMER_NAME_FEEDER, content1);
        contentValues.put(FEEDER_CODE, content2);
        contentValues.put(FEEDER_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);
        contentValues.put(CENTER_NAME, content5);

        return sqLiteDatabase.insert(TBL_FEEDER_CATEGORY, null, contentValues);
    }

    public Cursor masterfeedercursorAll() {
        String[] columns = new String[]{KEY_FEEDERID, CONSUMER_NAME_FEEDER, FEEDER_CODE, FEEDER_NAME, DISPLAY_CODE, CENTER_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_FEEDER_CATEGORY, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor get_feeder_name(String feeder) {
        String selectQuery =
                FEEDER_CODE + " = '" + feeder + "'";
        String[] columns = new String[]{KEY_FEEDERID, FEEDER_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_FEEDER_CATEGORY, columns, selectQuery, null, null, null, null);
        return cursor;
    }


    ////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  MASTER TABLE FOR GOVT DEPARTMENT %%%%%%%%%%%%%%%%%%

    public static final String TBL_GOVT_DEPARTMENT = "TBL_GOVT_DEPARTMENT";
    public static final String KEY_DEPTD = "KEY_DEPTID";
    public static final String DEPT_CODE = "DEPT_CODE";
    public static final String DEPT_NAME = "DEPT_NAME";
    public static final String CONSUMER_NAME_DEPT = "CONSUMER_NAME_DEPT";

    private static final String MASTERDEPT = "CREATE TABLE " + TBL_GOVT_DEPARTMENT +
            "(\n" +
            "  KEY_DEPTD INTEGER PRIMARY KEY AUTOINCREMENT     NOT NULL,\n" +
            "  CONSUMER_NAME_DEPT    VARCHAR2(6)               NOT NULL,\n" +
            "  DEPT_CODE  VARCHAR2(30)               NOT NULL,\n" +
            "  DEPT_NAME       VARCHAR2(60),\n" +
            "  DISPLAY_CODE   VARCHAR2(6),\n" +
            "  CENTER_NAME    VARCHAR2(20)\n" +
            ");";

    public long masterdept_insert(String content1, String content2, String content3, String content4, String content5) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONSUMER_NAME_DEPT, content1);
        contentValues.put(DEPT_CODE, content2);
        contentValues.put(DEPT_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);
        contentValues.put(CENTER_NAME, content5);

        return sqLiteDatabase.insert(TBL_GOVT_DEPARTMENT, null, contentValues);
    }

    public Cursor masterdepartcursorAll() {
        String[] columns = new String[]{KEY_DEPTD, CONSUMER_NAME_DEPT, DEPT_CODE, DEPT_NAME, DISPLAY_CODE, CENTER_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_GOVT_DEPARTMENT, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor get_depart_name(String depart) {
        String selectQuery =
                DEPT_CODE + " = '" + depart + "'";
        String[] columns = new String[]{KEY_DEPTD, DEPT_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_GOVT_DEPARTMENT, columns, selectQuery, null, null, null, null);
        return cursor;
    }


    ////%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  MASTER TABLE FOR SUB DIVISION %%%%%%%%%%%%%%%%%%
    public static final String TBL_SUB_DIVISION = "TBL_SUB_DIVISION";
    public static final String KEY_SUB_DIVISIONID = "KEY_SUB_DIVISIONID";
    public static final String DIVISION_CODE = "DIVISION_CODE";
    //    public static final String SUB_DIV_CODE ="SUB_DIV_CODE";
    public static final String SUB_DIV_NAME = "SUB_DIV_NAME";
    public static final String DISPLAY_CODE = "DISPLAY_CODE";
    private static final String MASTERSUBDIVISION = "CREATE TABLE " + TBL_SUB_DIVISION +
            "(\n" +
            "  KEY_SUB_DIVISIONID    INTEGER PRIMARY KEY AUTOINCREMENT          NOT NULL,\n" +
            "  DIVISION_CODE  VARCHAR2(6)               NOT NULL,\n" +
            "  SUB_DIV_CODE   VARCHAR2(6)               NOT NULL,\n" +
            "  SUB_DIV_NAME   VARCHAR2(25),\n" +
            "  DISPLAY_CODE   VARCHAR2(25)\n" +
            ");";

    public long mastersubdivision_insert(String content1, String content2, String content3, String content4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIVISION_CODE, content1);
        contentValues.put(SUB_DIV_CODE, content2);
        contentValues.put(SUB_DIV_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);

        return sqLiteDatabase.insert(TBL_SUB_DIVISION, null, contentValues);
    }

    public Cursor mastersubdivisioncursorAll() {
        String[] columns = new String[]{KEY_SUB_DIVISIONID, DIVISION_CODE, SUB_DIV_CODE, SUB_DIV_NAME, DISPLAY_CODE};
        Cursor cursor = sqLiteDatabase.query(TBL_SUB_DIVISION, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor subdivisionqueueOne(String whereClause) {
        String[] columns = new String[]{KEY_SUB_DIVISIONID, DIVISION_CODE, SUB_DIV_CODE, SUB_DIV_NAME, DISPLAY_CODE};
        Cursor cursor = sqLiteDatabase.query(TBL_SUB_DIVISION, columns, whereClause, null, null, null, null);
        return cursor;
    }

    public Cursor get_subdivision_name(String division, String subdivison) {
        String selectQuery = DIVISION_CODE + " = '" + division + "'"
                + " AND " + SUB_DIV_CODE + " = '" + subdivison + "'";
        String[] columns = new String[]{KEY_SUB_DIVISIONID, SUB_DIV_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_SUB_DIVISION, columns, selectQuery, null, null, null, null);
        return cursor;
    }

    //*********************************   MASTER TABLE FOR TBL_SECTION ****************************
    public static final String TBL_SECTION = "TBL_SECTION";
    public static final String KEY_SECTIONID = "KEY_SECTIONID";
    private static final String MASTERSECTION = "CREATE TABLE " + TBL_SECTION +
            "(\n" +
            "  KEY_SECTIONID INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "  SUB_DIV_CODE  VARCHAR2(6)                NOT NULL,\n" +
            "  SEC_CODE      VARCHAR2(6)                NOT NULL,\n" +
            "  SEC_NAME      VARCHAR2(25),\n" +
            "  DISPLAY_CODE  VARCHAR2(25)\n" +
            ");";

    public long mastersection_insert(String content1, String content2, String content3, String content4) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUB_DIV_CODE, content1);
        contentValues.put(SEC_CODE, content2);
        contentValues.put(SEC_NAME, content3);
        contentValues.put(DISPLAY_CODE, content4);
        return sqLiteDatabase.insert(TBL_SECTION, null, contentValues);
    }

    public Cursor mastersectioncursorAll() {
        String[] columns = new String[]{KEY_SECTIONID, SUB_DIV_CODE, SEC_CODE, SEC_NAME, DISPLAY_CODE};
        Cursor cursor = sqLiteDatabase.query(TBL_SECTION, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor sectionqueueOne(String whereClause) {
        String[] columns = new String[]{KEY_SECTIONID, SUB_DIV_CODE, SEC_CODE, SEC_NAME, DISPLAY_CODE};
        Cursor cursor = sqLiteDatabase.query(TBL_SECTION, columns, whereClause, null, null, null, null);
        return cursor;
    }

    public Cursor get_section_name(String subdivision, String section) {
        String selectQuery = SUB_DIV_CODE + " = '" + subdivision + "'"
                + " AND " + SEC_CODE + " = '" + section + "'";
        String[] columns = new String[]{KEY_SECTIONID, SEC_NAME};
        Cursor cursor = sqLiteDatabase.query(TBL_SECTION, columns, selectQuery, null, null, null, null);
        return cursor;
    }

    public Cursor routequeueOne(String whereClause) {
        String[] columns = new String[]{KEY_SECTIONCYCLEROUTEMAPPINGID, ROUTENO};
        Cursor cursor = sqLiteDatabase.query(TBL_SECTIONCYCLEROUTEMAPPING, columns, whereClause, null, null, null, null);
        // Log.e("cursor value route",""+cursor);
        return cursor;
    }

    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  MASTER TABLE FOR ROUTE  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public static final String TBL_SECTIONCYCLEROUTEMAPPING = "TBL_SECTIONCYCLEROUTEMAPPING";
    public static final String KEY_SECTIONCYCLEROUTEMAPPINGID = "KEY_SECTIONCYCLEROUTEMAPPINGID";
    public static final String CYCLEID = "CYCLEID";
    public static final String DIVISIONIDATTACHED = "DIVISIONIDATTACHED";
    public static final String ROUTENO = "ROUTENO";
    public static final String SECTIONID = "SECTIONID";
    public static final String SUBDIVATTACHED = "SUBDIVATTACHED";
    public static final String DATAAREAID = "DATAAREAID";
    public static final String RECVERSION = "RECVERSION";
    public static final String RECID = "RECID";

    private static final String MASTERROUTE = "CREATE TABLE " + TBL_SECTIONCYCLEROUTEMAPPING +
            "(\n" +
            "  KEY_SECTIONCYCLEROUTEMAPPINGID      INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "  CYCLEID             VARCHAR2(20)         NOT NULL,\n" +
            "  DIVISIONIDATTACHED  VARCHAR2(20)         NOT NULL,\n" +
            "  ROUTENO             VARCHAR2(20)         NOT NULL,\n" +
            "  SECTIONID           VARCHAR2(20)         NOT NULL,\n" +
            "  SUBDIVATTACHED      VARCHAR2(20)         NOT NULL,\n" +
            "  MODIFIEDDATETIME    DATE                      NOT NULL,\n" +
            "  MODIFIEDBY          VARCHAR2(8)          NOT NULL,\n" +
            "  CREATEDDATETIME     DATE                      NOT NULL,\n" +
            "  CREATEDBY           VARCHAR2(8)          NOT NULL,\n" +
            "  DATAAREAID          VARCHAR2(4)          NOT NULL,\n" +
            "  RECVERSION          NUMBER                    NOT NULL,\n" +
            "  RECID               NUMBER(10)                NOT NULL\n" +
            ");";

    public long mastersectioncycleroutemapping_insert(String content1, String content2, String content3, String content4,
                                                      String content5, String content6, String content7, String content8,
                                                      String content9, String content10, String content11, String content12) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CYCLEID, content1);
        contentValues.put(DIVISIONIDATTACHED, content2);
        contentValues.put(ROUTENO, content3);
        contentValues.put(SECTIONID, content4);
        contentValues.put(SUBDIVATTACHED, content5);
        contentValues.put(MODIFIEDDATETIME, content6);
        contentValues.put(MODIFIEDBY, content7);
        contentValues.put(CREATEDDATETIME, content8);
        contentValues.put(CREATEDBY, content9);
        contentValues.put(DATAAREAID, content10);
        contentValues.put(RECVERSION, content11);
        contentValues.put(RECID, content12);


        return sqLiteDatabase.insert(TBL_SECTIONCYCLEROUTEMAPPING, null, contentValues);
    }

    public Cursor mastersectioncycleroutemappingcursorAll(String division, String subdivison, String section, String cycle) {

        String selectQuery =
                DIVISIONIDATTACHED + " = '" + division + "'"
                        + " AND " + SUBDIVATTACHED + " = '" + subdivison + "'"
                        + " AND " + SECTIONID + " = '" + section + "'"
                        + " AND " + CYCLEID + " = '" + cycle + "'";


       /* SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);*/

        String[] columns = new String[]{KEY_SECTIONCYCLEROUTEMAPPINGID, ROUTENO};
        Cursor cursor = sqLiteDatabase.query(TBL_SECTIONCYCLEROUTEMAPPING, columns, selectQuery, null, null, null, null);

        return cursor;
    }

    private static final String TBL_TERIFFCATEGORYLOADMAPPING = "TBL_TERIFFCATEGORYLOADMAPPING";
    public static final String KEY_TERIFFCATEGORYLOADMAPPING = "KEY_TERIFFCATEGORYLOADMAPPING";
    //    public  static final String  PHASE                                ="PHASE";
    public static final String LOAD = "LOAD";
    //    public  static final String  SUPPLY_TYPE                          ="SUPPLY_TYPE";
    public static final String TERIFF_CATEGORY = "TERIFF_CATEGORY";
    public static final String TERIFF_SHORTNAME = "TERIFF_SHORTNAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LOAD_MIN = "LOAD_MIN";
    public static final String LOAD_MAX = "LOAD_MAX";
    public static final String PHASE1 = "PHASE1";
    public static final String TARIFF_SHORTCODE = "TARIFF_SHORTCODE";

    private static final String MASTERTERIFFCATEGORYLOADMAPPING = "CREATE TABLE " + TBL_TERIFFCATEGORYLOADMAPPING +
            "(\n" +
            "  KEY_TERIFFCATEGORYLOADMAPPING      INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "  PHASE             VARCHAR2(50),\n" +
            "  LOAD              VARCHAR2(60),\n" +
            "  SUPPLY_TYPE       VARCHAR2(50),\n" +
            "  TERIFF_CATEGORY   VARCHAR2(60),\n" +
            "  TERIFF_SHORTNAME  VARCHAR2(20),\n" +
            "  DESCRIPTION       VARCHAR2(100),\n" +
            "  LOAD_MIN          NUMBER,\n" +
            "  LOAD_MAX          NUMBER,\n" +
            "  PHASE1            VARCHAR2(10),\n" +
            "  TARIFF_SHORTCODE  VARCHAR2(20)\n" +
            ");";

    private static final String TBL_DEPARTMENT = "TBL_DEPARTMENT";
    public static final String KEY_DEPARTMENT = "KEY_DEPARTMENT";
    public static final String DEPARTMENT_ID = "DEPARTMENT_ID";
    public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";

    private static final String MASTERDEPARTMENT = "CREATE TABLE " + TBL_DEPARTMENT +
            "(\n" +
            "  KEY_DEPARTMENT   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "  DEPARTMENT_ID    INTEGER                      NOT NULL,\n" +
            "  DEPARTMENT_NAME  VARCHAR2(200)\n" +
            ");";


    private static final String TBL_CONNECTIONFOR = "TBL_CONNECTIONFOR";
    public static final String KEY_CONNECTIONFOR = "KEYCONNECTIONFOR";
    //    public  static final String  CONNECTION_FOR          ="CONNECTION_FOR";
//    public  static final String  INDUSTRY_TYPE           ="INDUSTRY_TYPE";
    public static final String SINGLEPHASE_FLAG = "SINGLEPHASE_FLAG";

    private static final String MASTERCONNECTIONFOR = " CREATE TABLE " + TBL_CONNECTIONFOR +
            "    (\n" +
            "    KEY_DEPARTMENT   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    CONNECTION_FOR    VARCHAR2(60),\n" +
            "    INDUSTRY_TYPE     VARCHAR2(60),\n" +
            "    SINGLEPHASE_FLAG  NUMBER\n" +
            "    );";

    //.................new meter table.............................//
    private static final String TBL_MANUFACTURER_CODE = "TBL_MANUFACTURER_CODE";
    public static final String KEY_MANUFACTURER_CODE = "KEY_MANUFACTURER_CODE";
    public static final String MANUFACTURER_CODE = "MANUFACTURER_CODE";
    private static final String CREATE_MANUFACTURER_CODE = " CREATE TABLE " + TBL_MANUFACTURER_CODE +
            "    (\n" +
            "    KEY_MANUFACTURER_CODE   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    MANUFACTURER_CODE    VARCHAR2(60)\n" +
            "    );";


    public long insert_manufacturer_code(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MANUFACTURER_CODE, content1);
        return sqLiteDatabase.insert(TBL_MANUFACTURER_CODE, null, contentValues);
    }


    public Cursor cursor_manufacturer_code() {
        String[] columns = new String[]{KEY_MANUFACTURER_CODE, MANUFACTURER_CODE};
        Cursor cursor = sqLiteDatabase.query(TBL_MANUFACTURER_CODE, columns, null, null, null, null, null);
        return cursor;
    }


    private static final String TBL_METER_TYPE = "TBL_METER_TYPE";
    public static final String KEY_METER_TYPE = "KEY_METER_TYPE";
    public static final String METER_TYPE = "METER_TYPE";
    private static final String CREATE_METER_TYPE = " CREATE TABLE " + TBL_METER_TYPE +
            "    (\n" +
            "    KEY_METER_TYPE   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_TYPE    VARCHAR2(60)\n" +
            "    );";

    public long insert_meter_type(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_TYPE, content1);
        return sqLiteDatabase.insert(TBL_METER_TYPE, null, contentValues);
    }


    public Cursor cursor_meter_type() {
        String[] columns = new String[]{KEY_METER_TYPE, METER_TYPE};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_TYPE, columns, null, null, null, null, null);
        return cursor;
    }


    private static final String TBL_METER_PHASE = "TBL_METER_PHASE";
    public static final String KEY_METER_PHASE = "KEY_METER_PHASE";
    public static final String METER_PHASE = "METER_PHASE";
    private static final String CREATE_METER_PHASE = " CREATE TABLE " + TBL_METER_PHASE +
            "    (\n" +
            "    KEY_METER_PHASE   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_PHASE    VARCHAR2(60)\n" +
            "    );";

    public long insert_meter_phase(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_PHASE, content1);
        return sqLiteDatabase.insert(TBL_METER_PHASE, null, contentValues);
    }


    public Cursor cursor_meter_phase() {
        String[] columns = new String[]{KEY_METER_PHASE, METER_PHASE};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_PHASE, columns, null, null, null, null, null);
        return cursor;
    }


    private static final String TBL_METER_BILL_BASIS = "TBL_BILL_BASIS";
    public static final String KEY_METER_BILL_BASIS = "KEY_METER_BILL_BASIS";
    public static final String METER_BILL_BASIS = "METER_BILL_BASIS";
    private static final String CREATE_METER_BILL_BASIS = " CREATE TABLE " + TBL_METER_BILL_BASIS +
            "    (\n" +
            "    KEY_METER_BILL_BASIS   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_BILL_BASIS    VARCHAR2(60)\n" +
            "    );";

    public long insert_meter_bill_basis(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_BILL_BASIS, content1);
        return sqLiteDatabase.insert(TBL_METER_BILL_BASIS, null, contentValues);
    }


    public Cursor cursor_bill_basis() {
        String[] columns = new String[]{KEY_METER_BILL_BASIS, METER_BILL_BASIS};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_BILL_BASIS, columns, null, null, null, null, null);
        return cursor;
    }

    private static final String TBL_METER_DIGIT = "TBL_METER_DIGIT";
    public static final String KEY_METER_DIGIT = "KEY_METER_DIGIT";
    public static final String METER_DIGIT = "METER_DIGIT";
    private static final String CREATE_METER_DIGIT = " CREATE TABLE " + TBL_METER_DIGIT +
            "    (\n" +
            "    KEY_METER_DIGIT   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_DIGIT    VARCHAR2(60)\n" +
            "    );";

    public long insert_meter_digit(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_DIGIT, content1);
        return sqLiteDatabase.insert(TBL_METER_DIGIT, null, contentValues);
    }


    public Cursor cursor_meter_digit() {
        String[] columns = new String[]{KEY_METER_DIGIT, METER_DIGIT};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_DIGIT, columns, null, null, null, null, null);
        return cursor;
    }


    private static final String TBL_METER_OWNERSHIP = "TBL_METER_OWNERSHIP";
    public static final String KEY_METER_OWNERSHIP = "KEY_METER_OWNERSHIP";
    public static final String METER_OWNERSHIP = "METER_OWNERSHIP";
    private static final String CREATE_METER_OWNERSHIP = " CREATE TABLE " + TBL_METER_OWNERSHIP +
            "    (\n" +
            "    KEY_METER_OWNERSHIP   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_OWNERSHIP    VARCHAR2(60)\n" +
            "    );";

    public long insert_meter_ownership(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_OWNERSHIP, content1);
        return sqLiteDatabase.insert(TBL_METER_OWNERSHIP, null, contentValues);
    }


    public Cursor cursor_meter_ownership() {
        String[] columns = new String[]{KEY_METER_OWNERSHIP, METER_OWNERSHIP};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_OWNERSHIP, columns, null, null, null, null, null);
        return cursor;
    }


    private static final String TBL_METER_METERED = "TBL_METER_METERED";
    public static final String KEY_METER_METERED = "KEY_METER_METERED";
    public static final String METER_METERED = "METER_METERED";
    private static final String CREATE_METER_METERED = " CREATE TABLE " + TBL_METER_METERED +
            "    (\n" +
            "    KEY_METER_METERED   INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL,\n" +
            "    METER_METERED    VARCHAR2(60)\n" +
            "    );";


    public long insert_meter_metered(String content1) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(METER_METERED, content1);
        return sqLiteDatabase.insert(TBL_METER_METERED, null, contentValues);
    }


    public Cursor cursor_meter_metered() {
        String[] columns = new String[]{KEY_METER_METERED, METER_METERED};
        Cursor cursor = sqLiteDatabase.query(TBL_METER_METERED, columns, null, null, null, null, null);
        return cursor;
    }


    //    public  static final String  SUPPLY_TYPE                          ="SUPPLY_TYPE";
//#############################################   TABLE FINISH   #########################################


    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public SQLiteAdapter(Context c) {
        context = c;
    }

    public SQLiteAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public SQLiteAdapter openToWrite() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        sqLiteHelper.close();
    }

    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL(MASTERDIVISION);
            db.execSQL(MASTERSUBDIVISION);
            db.execSQL(MASTERSECTION);
            db.execSQL(MASTERROUTE);
            db.execSQL(MASTERTERIFFCATEGORYLOADMAPPING);
            db.execSQL(MASTERDEPARTMENT);
            db.execSQL(MASTERCONNECTIONFOR);
            db.execSQL(CREATE_MANUFACTURER_CODE);
            db.execSQL(CREATE_METER_TYPE);
            db.execSQL(CREATE_METER_PHASE);
            db.execSQL(CREATE_METER_BILL_BASIS);
            db.execSQL(CREATE_METER_DIGIT);
            db.execSQL(CREATE_METER_OWNERSHIP);
            db.execSQL(CREATE_METER_METERED);
            db.execSQL(MASTERTARIFF);
            db.execSQL(MASTERFEEDER);
            db.execSQL(MASTERDEPT);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TBL_DIVISION);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_SUB_DIVISION);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_SECTION);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_SECTIONCYCLEROUTEMAPPING);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_DEPARTMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_MANUFACTURER_CODE);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_TYPE);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_PHASE);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_BILL_BASIS);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_DIGIT);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_OWNERSHIP);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_METER_METERED);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_TARIFF_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_FEEDER_CATEGORY);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_GOVT_DEPARTMENT);

            onCreate(db);
        }
    }
}
