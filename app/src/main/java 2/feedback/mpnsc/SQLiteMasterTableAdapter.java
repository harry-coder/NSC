package feedback.mpnsc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by sushily on 28-05-2015.
 */
public class SQLiteMasterTableAdapter {

    public static final String MYDATABASE_NAME           ="MYDATABASE_MASTERTABLE";
    public static final int MYDATABASE_VERSION           = 1;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Master table to store field data &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    public static final String TBL_MASTERTABLE           ="DEMO_TABLE";
    public static final String KEY_MASTERTABLEID         ="KEY_MASTERTABLEID";
    public static final String TICKETNUMBER              ="TICKETNUMBER";
    public static final String CUSTACCUNT                ="CUSTACCUNT";
    public static final String CUSTGROUP                 ="CUSTGROUP";
    public static final String DEVICEID                  ="DEVICEID";
    public static final String APPLICATIONNO             ="APPLICATIONNO";
    public static final String OWNERSHIP                 ="OWNERSHIP";
    public static final String PANCODE                   ="PANCODE";
    public static final String PHASE                     ="PHASE";
    public static final String LOADREQUIRED              ="LOADREQUIRED";
    public static final String REQUIREDLOADUNIT          ="REQUIREDLOADUNIT";
    public static final String CAPACITY                  ="CAPACITY";
    public static final String REQUIREDUNIT              ="REQUIREDUNIT";
    public static final String APPLIEDSANCTIONDEMAND     ="APPLIEDSANCTIONDEMAND";
    public static final String SUPPLYLEVEL               ="SUPPLYLEVEL";
    public static final String CUSTCATGROUP              ="CUSTCATGROUP";
    public static final String MD_CODE                   ="MD_CODE";
    public static final String DIV_CODE                  ="DIV_CODE";
    public static final String SUB_DIV_CODE              ="SUB_DIV_CODE";
    public static final String SEC_CODE                  ="SEC_CODE";
    public static final String SEC_NAME                  ="SEC_NAME";
    public static final String NEWFIRSTNAME              ="NEWFIRSTNAME";
    public static final String NEWLASTNAME               ="NEWLASTNAME";
    public static final String NEWMIDDLENAME             ="NEWMIDDLENAME";
    public static final String OLDBUILDINGCOMPLIMENT     ="OLDBUILDINGCOMPLIMENT";
    public static final String OLDCITY                   ="OLDCITY";
    public static final String OLDCOUNTRYREGIONID        ="OLDCOUNTRYREGIONID";
    public static final String OLDDISTRICT               ="OLDDISTRICT";
    public static final String OLDSTATE                  ="OLDSTATE";
    public static final String OLDSTREET                 ="OLDSTREET";
    public static final String OLDZIPCODE                ="OLDZIPCODE";
    public static final String ADDRESSREFRECID           ="ADDRESSREFRECID";
    public static final String APPLICATIONSTATUS         ="APPLICATIONSTATUS";
    public static final String CONNECTIONID              ="CONNECTIONID";
    public static final String COPYOFITSREQUIREDFEDBACK  ="COPYOFITSREQUIREDFEDBACK";
    public static final String APPLICATIONDATE           ="APPLICATIONDATE";
    public static final String APPLICATIONTYPE           ="APPLICATIONTYPE";
    public static final String CONNECTIONSTATUS          ="CONNECTIONSTATUS";
    public static final String HTIDLEDEMANDOFFACTORY     ="HTIDLEDEMANDOFFACTORY";
    public static final String HTWORKINGHOUR             ="HTWORKINGHOUR";
    public static final String INDLOADFACTOR             ="INDLOADFACTOR";
    public static final String METERCHANGEREQ            ="METERCHANGEREQ";
    public static final String ORGANISATIONTYPE          ="ORGANISATIONTYPE";
    public static final String GROUPCHANG                ="GROUPCHANG";
    public static final String ISAPPROVED                ="ISAPPROVED";
    public static final String ISCONFIGURATIONTERMINATED ="ISCONFIGURATIONTERMINATED";
    public static final String ISEXISTINGAGREEMENTENDED  ="ISEXISTINGAGREEMENTENDED";
    public static final String ISEXISTINGDEVICEREPLACED  ="ISEXISTINGDEVICEREPLACED";
    public static final String ISNEWAGREEMENTCREATED     ="ISNEWAGREEMENTCREATED";
    public static final String ISNEWCONFIGURATIONCREATED ="ISNEWCONFIGURATIONCREATED";
    public static final String ISREQBLOCKUNBLOCK         ="ISREQBLOCKUNBLOCK";
    public static final String ISSERIALNUMBERCHANGED     ="ISSERIALNUMBERCHANGED";
    public static final String ISUPDATEBLOCKUNBLOCK      ="ISUPDATEBLOCKUNBLOCK";
    public static final String ISVALIDATE                ="ISVALIDATE";
    public static final String EXTERNALID1               ="EXTERNALID1";
    public static final String MESSAGE                   ="MESSAGE";
    public static final String REJECTIONREMARKS          ="REJECTIONREMARKS";
    public static final String RESPONSIBLEJOBID          ="RESPONSIBLEJOBID";
    public static final String RESPONSIBLEWORKER         ="RESPONSIBLEWORKER";
    public static final String STREETNUMBER              ="STREETNUMBER";
    public static final String MODIFIEDDATETIME          ="MODIFIEDDATETIME";
    public static final String MODIFIEDBY                ="MODIFIEDBY";
    public static final String CREATEDDATETIME           ="CREATEDDATETIME";
    public static final String CREATEDBY                 ="CREATEDBY";
    public static final String CO_CODE                   ="CO_CODE";
    public static final String DISCOM_CODE               ="DISCOM_CODE";
    public static final String CIRCLE_CODE               ="CIRCLE_CODE";
    public static final String MOBILE_NUMBER             ="MOBILE_NUMBER";
    public static final String EMAIL_ID                  ="EMAIL_ID";
    public static final String BLOCK                     ="BLOCK";
    public static final String BUILDING                  ="BUILDING";
    public static final String CITY                      ="CITY";
    public static final String DISTRICT                  ="DISTRICT";
    public static final String GP                        ="GP";
    public static final String PLOT                      ="PLOT";
    public static final String STREET                    ="STREET";
    public static final String TEHSIL                    ="TEHSIL";
    public static final String VILLAGE                   ="VILLAGE";
    public static final String BLOCK1                    ="BLOCK1";
    public static final String CITY1                     ="CITY1";
    public static final String DISTRICT1                 ="DISTRICT1";
    public static final String GP1                       ="GP1";
    public static final String PLOT1                     ="PLOT1";
    public static final String STREET1                   ="STREET1";
    public static final String TEHSIL1                   ="TEHSIL1";
    public static final String VILLAGE1                  ="VILLAGE1";
    public static final String BUILDING1                 ="BUILDING1";
    public static final String ISHOOKING                 ="ISHOOKING";
    public static final String FATHERNAME                ="FATHERNAME";
    public static final String NEARESTLANDMARK           ="NEARESTLANDMARK";
    public static final String CONNECTIONTYPE            ="CONNECTIONTYPE";
    public static final String COMPLAINTSUBCATEGORY      ="COMPLAINTSUBCATEGORY";
    public static final String COMPLAINTDETAILS          ="COMPLAINTDETAILS";
    public static final String SOURCEOFCOMPLAINT         ="SOURCEOFCOMPLAINT";
    public static final String COMPLAINTCATEGORY         ="COMPLAINTCATEGORY";
    public static final String COMPLAINT_CLOSEDDATE      ="COMPLAINT_CLOSEDDATE";
    public static final String COMPLAINT_COMMENT         ="COMPLAINT_COMMENT";
    public static final String COMPLAINT_STATUS          ="COMPLAINT_STATUS";
    public static final String COMPLAINTTYPE             ="COMPLAINTTYPE";
    public static final String TEMPORARYFROMDATE         ="TEMPORARYFROMDATE";
    public static final String TEMPORARYTODATE           ="TEMPORARYTODATE";
    public static final String COMPLAINT_CLOSEDBY        ="COMPLAINT_CLOSEDBY";
    public static final String USER_COMMENT              ="USER_COMMENT";
    public static final String CYCLE_ID                  ="CYCLE_ID";
    public static final String ROUTE_ID                  ="ROUTE_ID";
    public static final String CESU_CONSUMER_NO          ="CESU_CONSUMER_NO";
    public static final String CONSUMER_TYPE             ="CONSUMER_TYPE";
    public static final String SCEME                     ="SCEME";
    public static final String CONNECTION_FOR            ="CONNECTION_FOR";
    public static final String ED_EXEMPTION              ="ED_EXEMPTION";
    public static final String ED_EXEMPTION_PERCENTAGE   ="ED_EXEMPTION_PERCENTAGE";
    public static final String NAME_OF_ORGANISATION      ="NAME_OF_ORGANISATION";
    public static final String FATHERORHUSBAND_NAME      ="FATHERORHUSBAND_NAME";
    public static final String TYPE_OF_ORGANISATION      ="TYPE_OF_ORGANISATION";
    public static final String LANDLINE_NUMBER           ="LANDLINE_NUMBER";
    public static final String TRANSFER_OWNERSHIP        ="TRANSFER_OWNERSHIP";
    public static final String METERING_SIDE             ="METERING_SIDE";
    public static final String TRANSFERMER_CAPACITY      ="TRANSFERMER_CAPACITY";
    public static final String INDUSTRY_TYPE             ="INDUSTRY_TYPE";
    public static final String NATURE_OF_JOB             ="NATURE_OF_JOB";
    public static final String WORKING_HOURS             ="WORKING_HOURS";
    public static final String LOAD_FACTOR               ="LOAD_FACTOR";
    public static final String MAX_DEMAND                ="MAX_DEMAND";
    public static final String SHIFTS                    ="SHIFTS";
    public static final String MD_PEAK_HOURS             ="MD_PEAK_HOURS";
    public static final String SUBSTATION                ="SUBSTATION";
    public static final String FEEDER                    ="FEEDER";
    public static final String DT                        ="DT";
    public static final String IS_METER                  ="IS_METER";
    public static final String PIN_NO                    ="PIN_NO";
    public static final String SUPPLY_TYPE               ="SUPPLY_TYPE";
    public static final String TITLE                     ="TITLE";
    public static final String DESIGNATION_OF_SIGNATORY  ="DESIGNATION_OF_SIGNATORY";
    public static final String SIGNATORY_NAME            ="SIGNATORY_NAME";
    public static final String DEPARTMENT                ="DEPARTMENT";
    public static final String PIN_NO_BILLED             ="PIN_NO_BILLED";
    public static final String ORDER_NO                  ="ORDER_NO";
    public static final String ORDER_DATE                ="ORDER_DATE";
    public static final String OTHER_CONNECTIONFOR       ="OTHER_CONNECTIONFOR";
    public static final String DATEOF_POWERSUPPLY        ="DATEOF_POWERSUPPLY";
    public static final String DISCON_RECON_DATE         ="DISCON_RECON_DATE";
    public static final String KWH                       ="KWH";
    public static final String KVAH                      ="KVAH";
    public static final String KVARH                     ="KVARH";
    public static final String MDKW                      ="MDKW";
    public static final String MDKW_UNIT                 ="MDKW_UNIT";
    public static final String PEAKKWH                   ="PEAKKWH";
    public static final String PEAKKVAH                  ="PEAKKVAH";
    public static final String PEAKKVARH                 ="PEAKKVARH";
    public static final String PEAKMDKW                  ="PEAKMDKW";
    public static final String PEAKMDKWUNIT              ="PEAKMDKWUNIT";
    public static final String OFFPEAKKWH                ="OFFPEAKKWH";
    public static final String OFFPEAKKVAH               ="OFFPEAKKVAH";
    public static final String OFFPEAKKVARH              ="OFFPEAKKVARH";
    public static final String OFFPEAKMDKW               ="OFFPEAKMDKW";
    public static final String OFFPEAKMDKWUNIT           ="OFFPEAKMDKWUNIT";
    public static final String METER_STATUS              ="METER_STATUS";
    public static final String REGISTER_NUMBER           ="REGISTER_NUMBER";
    public static final String PAGE_NUMBER               ="PAGE_NUMBER";
    public static final String ITEM_NUMBER               ="ITEM_NUMBER";
    public static final String RECORD_UPDATED_FLAG       ="RECORD_UPDATED_FLAG";
    public static final String POLE_LAT                 ="POLE_LAT";
    public static final String POLE_LONG                 ="POLE_LONG";
    public static final String HOME_LAT                 ="HOME_LAT";
    public static final String HOME_LONG                 ="HOME_LONG";
    public static final String IMAGE                     ="IMAGE";
    public static final String IMAGE_NAME                ="IMAGE_NAME";

    private static final String MASTERTABLE="CREATE TABLE "+TBL_MASTERTABLE +
            "(\n" +
            "  KEY_MASTERTABLEID          INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL,\n" +
            "  TICKETNUMBER               VARCHAR2(15),\n" +
            "  CUSTACCUNT                 VARCHAR2(25),\n" +
            "  CUSTGROUP                  VARCHAR2(25),\n" +
            "  DEVICEID                   VARCHAR2(25),\n" +
            "  APPLICATIONNO              VARCHAR2(12),\n" +
            "  OWNERSHIP                  VARCHAR2(20),\n" +
            "  PANCODE                    VARCHAR2(25),\n" +
            "  PHASE                      VARCHAR2(30),\n" +
            "  LOADREQUIRED               VARCHAR2(126)         DEFAULT '0.00',\n" +
            "  REQUIREDLOADUNIT           VARCHAR2(12),\n" +
            "  CAPACITY                   VARCHAR2(126),\n" +
            "  REQUIREDUNIT               VARCHAR2(12),\n" +
            "  APPLIEDSANCTIONDEMAND      VARCHAR2(10),\n" +
            "  SUPPLYLEVEL                VARCHAR2(10,3),\n" +
            "  CUSTCATGROUP               VARCHAR2(5),\n" +
            "  MD_CODE                    VARCHAR2(6),\n" +
            "  DIV_CODE                   VARCHAR2(6),\n" +
            "  SUB_DIV_CODE               VARCHAR2(6),\n" +
            "  SEC_CODE                   VARCHAR2(6),\n" +
            "  SEC_NAME                   VARCHAR2(25),\n" +
            "  NEWFIRSTNAME               VARCHAR2(60),\n" +
            "  NEWLASTNAME                VARCHAR2(35),\n" +
            "  NEWMIDDLENAME              VARCHAR2(35),\n" +
            "  OLDBUILDINGCOMPLIMENT      VARCHAR2(255),\n" +
            "  OLDCITY                    VARCHAR2(60),\n" +
            "  OLDCOUNTRYREGIONID         VARCHAR2(60),\n" +
            "  OLDDISTRICT                VARCHAR2(50),\n" +
            "  OLDSTATE                   VARCHAR2(60),\n" +
            "  OLDSTREET                  VARCHAR2(50),\n" +
            "  OLDZIPCODE                 VARCHAR2(50),\n" +
            "  ADDRESSREFRECID            VARCHAR2(11),\n" +
            "  APPLICATIONSTATUS          VARCHAR2(25),\n" +
            "  CONNECTIONID               VARCHAR2(20),\n" +
            "  COPYOFITSREQUIREDFEDBACK   VARCHAR2(5),\n" +
            "  APPLICATIONDATE            VARCHAR2(10),\n" +
            "  APPLICATIONTYPE            VARCHAR2(25),\n" +
            "  CONNECTIONSTATUS           VARCHAR2(25),\n" +
            "  HTIDLEDEMANDOFFACTORY      VARCHAR2(10),\n" +
            "  HTWORKINGHOUR              VARCHAR2(255),\n" +
            "  INDLOADFACTOR              VARCHAR2(10),\n" +
            "  METERCHANGEREQ             VARCHAR2(10),\n" +
            "  ORGANISATIONTYPE           VARCHAR2(5),\n" +
            "  GROUPCHANG                 VARCHAR2(3),\n" +
            "  ISAPPROVED                 VARCHAR2(3)          DEFAULT '0',\n" +
            "  ISCONFIGURATIONTERMINATED  VARCHAR2(3),\n" +
            "  ISEXISTINGAGREEMENTENDED   VARCHAR2(3),\n" +
            "  ISEXISTINGDEVICEREPLACED   VARCHAR2(3),\n" +
            "  ISNEWAGREEMENTCREATED      VARCHAR2(3),\n" +
            "  ISNEWCONFIGURATIONCREATED  VARCHAR2(3),\n" +
            "  ISREQBLOCKUNBLOCK          VARCHAR2(3),\n" +
            "  ISSERIALNUMBERCHANGED      VARCHAR2(3),\n" +
            "  ISUPDATEBLOCKUNBLOCK       VARCHAR2(3),\n" +
            "  ISVALIDATE                 VARCHAR2(3),\n" +
            "  EXTERNALID1                VARCHAR2(25),\n" +
            "  MESSAGE                    VARCHAR2(255),\n" +
            "  REJECTIONREMARKS           VARCHAR2(255),\n" +
            "  RESPONSIBLEJOBID           VARCHAR2(25),\n" +
            "  RESPONSIBLEWORKER          VARCHAR2(6),\n" +
            "  STREETNUMBER               VARCHAR2(50),\n" +
            "  MODIFIEDDATETIME           VARCHAR2(10),\n" +
            "  MODIFIEDBY                 VARCHAR2(12),\n" +
            "  CREATEDDATETIME            VARCHAR2(10) ,\n" +
            "  CREATEDBY                  VARCHAR2(12) ,\n" +
            "  CO_CODE                    VARCHAR2(6),\n" +
            "  DISCOM_CODE                VARCHAR2(6),\n" +
            "  CIRCLE_CODE                VARCHAR2(6),\n" +
            "  MOBILE_NUMBER              VARCHAR2(11),\n" +
            "  EMAIL_ID                   VARCHAR2(50),\n" +
            "  BLOCK                      VARCHAR2(35),\n" +
            "  BUILDING                   VARCHAR2(35),\n" +
            "  CITY                       VARCHAR2(35),\n" +
            "  DISTRICT                   VARCHAR2(35),\n" +
            "  GP                         VARCHAR2(35),\n" +
            "  PLOT                       VARCHAR2(35),\n" +
            "  STREET                     VARCHAR2(35),\n" +
            "  TEHSIL                     VARCHAR2(35),\n" +
            "  VILLAGE                    VARCHAR2(60),\n" +
            "  BLOCK1                     VARCHAR2(35),\n" +
            "  CITY1                      VARCHAR2(35),\n" +
            "  DISTRICT1                  VARCHAR2(35),\n" +
            "  GP1                        VARCHAR2(35),\n" +
            "  PLOT1                      VARCHAR2(35),\n" +
            "  STREET1                    VARCHAR2(35),\n" +
            "  TEHSIL1                    VARCHAR2(35),\n" +
            "  VILLAGE1                   VARCHAR2(35),\n" +
            "  BUILDING1                  VARCHAR2(35),\n" +
            "  ISHOOKING                  VARCHAR2(3)          DEFAULT '0',\n" +
            "  FATHERNAME                 VARCHAR2(35),\n" +
            "  NEARESTLANDMARK            VARCHAR2(50),\n" +
            "  CONNECTIONTYPE             VARCHAR2(35),\n" +
            "  COMPLAINTSUBCATEGORY       VARCHAR2(100),\n" +
            "  COMPLAINTDETAILS           VARCHAR2(80),\n" +
            "  SOURCEOFCOMPLAINT          VARCHAR2(20),\n" +
            "  COMPLAINTCATEGORY          VARCHAR2(50),\n" +
            "  COMPLAINT_CLOSEDDATE       VARCHAR2(10),\n" +
            "  COMPLAINT_COMMENT          VARCHAR2(255),\n" +
            "  COMPLAINT_STATUS           VARCHAR2(20),\n" +
            "  COMPLAINTTYPE              VARCHAR2(45),\n" +
            "  TEMPORARYFROMDATE          VARCHAR2(10),\n" +
            "  TEMPORARYTODATE            VARCHAR2(10),\n" +
            "  COMPLAINT_CLOSEDBY         VARCHAR2(40),\n" +
            "  USER_COMMENT               VARCHAR2(200),\n" +
            "  CYCLE_ID                   VARCHAR2(6),\n" +
            "  ROUTE_ID                   VARCHAR2(6),\n" +
            "  CESU_CONSUMER_NO           VARCHAR2(12),\n" +
            "  CONSUMER_TYPE              VARCHAR2(100),\n" +
            "  SCEME                      VARCHAR2(50),\n" +
            "  CONNECTION_FOR             VARCHAR2(100),\n" +
            "  ED_EXEMPTION               VARCHAR2(3),\n" +
            "  ED_EXEMPTION_PERCENTAGE    VARCHAR2(126),\n" +
            "  NAME_OF_ORGANISATION       VARCHAR2(100),\n" +
            "  FATHERORHUSBAND_NAME       VARCHAR2(50),\n" +
            "  TYPE_OF_ORGANISATION       VARCHAR2(50),\n" +
            "  LANDLINE_NUMBER            VARCHAR2(15),\n" +
            "  TRANSFER_OWNERSHIP         VARCHAR2(50),\n" +
            "  METERING_SIDE              VARCHAR2(10),\n" +
            "  TRANSFERMER_CAPACITY       VARCHAR2(10),\n" +
            "  INDUSTRY_TYPE              VARCHAR2(50),\n" +
            "  NATURE_OF_JOB              VARCHAR2(50),\n" +
            "  WORKING_HOURS              VARCHAR2(10),\n" +
            "  LOAD_FACTOR                VARCHAR2(126),\n" +
            "  MAX_DEMAND                 VARCHAR2(126),\n" +
            "  SHIFTS                     VARCHAR2(20),\n" +
            "  MD_PEAK_HOURS              VARCHAR2(20),\n" +
            "  SUBSTATION                 VARCHAR2(100),\n" +
            "  FEEDER                     VARCHAR2(100),\n" +
            "  DT                         VARCHAR2(100),\n" +
            "  IS_METER                   VARCHAR2(10),\n" +
            "  PIN_NO                     INTEGER,\n" +
            "  SUPPLY_TYPE                VARCHAR2(10),\n" +
            "  TITLE                      VARCHAR2(10),\n" +
            "  DESIGNATION_OF_SIGNATORY   VARCHAR2(50),\n" +
            "  SIGNATORY_NAME             VARCHAR2(50),\n" +
            "  DEPARTMENT                 VARCHAR2(60),\n" +
            "  PIN_NO_BILLED              VARCHAR2(10),\n" +
            "  ORDER_NO                   VARCHAR2(12),\n" +
            "  ORDER_DATE                 VARCHAR2(10),\n" +
            "  OTHER_CONNECTIONFOR        VARCHAR2(40),\n" +
            "  DATEOF_POWERSUPPLY         VARCHAR2(10),\n" +
            "  DISCON_RECON_DATE          VARCHAR2(10),\n" +
            "  KWH                        VARCHAR2(126)         DEFAULT 0,\n" +
            "  KVAH                       VARCHAR2(126)         DEFAULT 0,\n" +
            "  KVARH                      VARCHAR2(126)         DEFAULT 0,\n" +
            "  MDKW                       VARCHAR2(126)         DEFAULT 0,\n" +
            "  MDKW_UNIT                  VARCHAR2(5),\n" +
            "  PEAKKWH                    VARCHAR2(126)         DEFAULT 0,\n" +
            "  PEAKKVAH                   VARCHAR2(126)         DEFAULT 0,\n" +
            "  PEAKKVARH                  VARCHAR2(126)         DEFAULT 0,\n" +
            "  PEAKMDKW                   VARCHAR2(126)         DEFAULT 0,\n" +
            "  PEAKMDKWUNIT               VARCHAR2(5),\n" +
            "  OFFPEAKKWH                 VARCHAR2(126)         DEFAULT 0,\n" +
            "  OFFPEAKKVAH                VARCHAR2(126)         DEFAULT 0,\n" +
            "  OFFPEAKKVARH               VARCHAR2(126)         DEFAULT 0,\n" +
            "  OFFPEAKMDKW                VARCHAR2(126)         DEFAULT 0,\n" +
            "  OFFPEAKMDKWUNIT            VARCHAR2(5),\n" +
            "  METER_STATUS               VARCHAR2(10),\n" +
            "  REGISTER_NUMBER            VARCHAR2(10),\n" +
            "  PAGE_NUMBER                VARCHAR2(10),\n" +
            "  ITEM_NUMBER                VARCHAR2(10),\n" +
            "  RECORD_UPDATED_FLAG        VARCHAR2(3),\n" +
            "  POLE_LAT                   VARCHAR2(15),\n" +
            "  POLE_LONG                  VARCHAR2(15),\n" +
            "  HOME_LAT                  VARCHAR2(15),\n" +
            "  HOME_LONG                  VARCHAR2(15),\n" +
            "  IMAGE                      TEXT,\n" +
            "  IMAGE_NAME                 VARCHAR2(20)\n" +
            ");";




    public Cursor mastersubdivisioncursorAll(){
        String[] columns = new String[]{KEY_MASTERTABLEID ,TICKETNUMBER ,CUSTACCUNT                 , CUSTGROUP                  ,
               DEVICEID                    , APPLICATIONNO              ,OWNERSHIP                  , PANCODE                    ,
                PHASE                      , LOADREQUIRED               ,REQUIREDLOADUNIT           , CAPACITY                   ,
                REQUIREDUNIT               , APPLIEDSANCTIONDEMAND      ,SUPPLYLEVEL                , CUSTCATGROUP               ,
                MD_CODE                    , DIV_CODE                   ,SUB_DIV_CODE               , SEC_CODE                   ,
                SEC_NAME                   , NEWFIRSTNAME               ,NEWLASTNAME                , NEWMIDDLENAME              ,
                OLDBUILDINGCOMPLIMENT      , OLDCITY                    ,OLDCOUNTRYREGIONID         , OLDDISTRICT                ,
                OLDSTATE                   , OLDSTREET                  ,OLDZIPCODE                 , ADDRESSREFRECID            ,                APPLICATIONSTATUS          ,
                CONNECTIONID               , COPYOFITSREQUIREDFEDBACK   ,APPLICATIONDATE            , APPLICATIONTYPE            ,
                CONNECTIONSTATUS           , HTIDLEDEMANDOFFACTORY      ,HTWORKINGHOUR              , INDLOADFACTOR              ,
                METERCHANGEREQ             , ORGANISATIONTYPE           ,GROUPCHANG                 , ISAPPROVED                 ,
                ISCONFIGURATIONTERMINATED  , ISEXISTINGAGREEMENTENDED   ,ISEXISTINGDEVICEREPLACED   , ISNEWAGREEMENTCREATED      ,
                ISNEWCONFIGURATIONCREATED  , ISREQBLOCKUNBLOCK          ,ISSERIALNUMBERCHANGED      , ISUPDATEBLOCKUNBLOCK       ,
                ISVALIDATE                 , EXTERNALID1                ,MESSAGE                    , REJECTIONREMARKS           ,
                RESPONSIBLEJOBID           , RESPONSIBLEWORKER          ,STREETNUMBER               , MODIFIEDDATETIME           ,
                MODIFIEDBY                 , CREATEDDATETIME            ,CREATEDBY                  , CO_CODE                    ,
                DISCOM_CODE                , CIRCLE_CODE                ,MOBILE_NUMBER              , EMAIL_ID                   ,
                BLOCK                      , BUILDING                   ,CITY                       , DISTRICT                   ,
                GP                         , PLOT                       ,STREET                     , TEHSIL                     ,
                VILLAGE                    , BLOCK1                     ,CITY1                      , DISTRICT1                  ,
                GP1                        , PLOT1                      ,STREET1                    , TEHSIL1                    ,
                VILLAGE1                   , BUILDING1                  ,ISHOOKING                  , FATHERNAME                 ,
                NEARESTLANDMARK            , CONNECTIONTYPE             ,COMPLAINTSUBCATEGORY       , COMPLAINTDETAILS           ,
                SOURCEOFCOMPLAINT          , COMPLAINTCATEGORY          ,COMPLAINT_CLOSEDDATE       , COMPLAINT_COMMENT          ,
                COMPLAINT_STATUS           , COMPLAINTTYPE              ,TEMPORARYFROMDATE          , TEMPORARYTODATE            ,
                COMPLAINT_CLOSEDBY         , USER_COMMENT               ,CYCLE_ID                   , ROUTE_ID                   ,
                CESU_CONSUMER_NO           , CONSUMER_TYPE              ,SCEME                      , CONNECTION_FOR             ,
                ED_EXEMPTION               , ED_EXEMPTION_PERCENTAGE    ,NAME_OF_ORGANISATION       , FATHERORHUSBAND_NAME       ,
                TYPE_OF_ORGANISATION       , LANDLINE_NUMBER            ,TRANSFER_OWNERSHIP         , METERING_SIDE              ,
                TRANSFERMER_CAPACITY       , INDUSTRY_TYPE              ,NATURE_OF_JOB              , WORKING_HOURS              ,
                LOAD_FACTOR                , MAX_DEMAND                 ,SHIFTS                     , MD_PEAK_HOURS              ,
                SUBSTATION                 , FEEDER                     ,DT                         , IS_METER                   ,
                PIN_NO                     , SUPPLY_TYPE                ,TITLE                      , DESIGNATION_OF_SIGNATORY   ,
                SIGNATORY_NAME             , DEPARTMENT                 ,PIN_NO_BILLED              , ORDER_NO                   ,
                ORDER_DATE                 , OTHER_CONNECTIONFOR        ,DATEOF_POWERSUPPLY         , DISCON_RECON_DATE          ,
                KWH                        , KVAH                       ,KVARH                      , MDKW                       ,
                MDKW_UNIT                  , PEAKKWH                    ,PEAKKVAH                   , PEAKKVARH                  ,
                PEAKMDKW                   , PEAKMDKWUNIT               ,OFFPEAKKWH                 , OFFPEAKKVAH                ,
                OFFPEAKKVARH               , OFFPEAKMDKW                ,OFFPEAKMDKWUNIT            , METER_STATUS               ,
                REGISTER_NUMBER            , PAGE_NUMBER                ,ITEM_NUMBER                , RECORD_UPDATED_FLAG        };
        Cursor cursor = sqLiteDatabase.query(TBL_MASTERTABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor mastersubdivisioncursorAll_new(){
        String[] columns = new String[]{KEY_MASTERTABLEID,    DIV_CODE,        SUB_DIV_CODE,
                                        SEC_CODE,             NEWFIRSTNAME,     FATHERORHUSBAND_NAME,
                                        NAME_OF_ORGANISATION, TYPE_OF_ORGANISATION,    SIGNATORY_NAME,
                BLOCK                      , BUILDING                   ,CITY                       , DISTRICT                   ,
                GP                         , PLOT                       ,STREET                     , TEHSIL                     ,
                VILLAGE                    , BLOCK1                     ,CITY1                      , DISTRICT1                  ,
                GP1                        , PLOT1                      ,STREET1                    , TEHSIL1                    ,
                VILLAGE1                   , BUILDING1                  ,MOBILE_NUMBER              ,EMAIL_ID                     ,
                LANDLINE_NUMBER,             DESIGNATION_OF_SIGNATORY,     LOADREQUIRED             ,CUSTGROUP                   ,
                POLE_LAT,             POLE_LONG,     HOME_LAT             ,HOME_LONG,
                IMAGE, IMAGE_NAME,PANCODE,PIN_NO,PIN_NO_BILLED,TICKETNUMBER};
        Cursor cursor = sqLiteDatabase.query(TBL_MASTERTABLE, columns, null, null, null, null, null);
        return cursor;

    }
    public void delete_byID(int id){
        sqLiteDatabase.delete(TBL_MASTERTABLE, KEY_MASTERTABLEID+"="+id, null);
    }



    public long mastersubdivision_insert(String   content1  ,
                                         String   content2  ,
                                         String   content3  ,
                                         String   content4  ,
                                         String   content5  ,
                                         String   content6  ,
                                         String   content7  ,
                                         String   content8  ,
                                         String   content9  ,
                                         String   content10 ,
                                         String   content11 ,
                                         String   content12 ,
                                         String   content13 ,
                                         String   content14 ,
                                         String   content15 ,
                                         String   content16 ,
                                         String   content17 ,
                                         String   content18 ,
                                         String   content19 ,
                                         String   content20 ,
                                         String   content21 ,
                                         String   content22 ,
                                         String   content23 ,
                                         String   content24 ,
                                         String   content25 ,
                                         String   content26 ,
                                         String   content27 ,
                                         String   content28 ,
                                         String   content29 ,
                                         String   content30 ,
                                         String   content31 ,
                                         String   content32 ,
                                         String   content33 ,
                                         String   content34 ,
                                         String   content35 ,
                                         String   content36 ,
                                         String   content37 ,
                                         String   content38 ,
                                         String   content39 ,
                                         String   content40 ,
                                         String   content41 ,
                                         String   content42 ,
                                         String   content43 ,
                                         String   content44 ,
                                         String   content45 ,
                                         String   content46 ,
                                         String   content47 ,
                                         String   content48 ,
                                         String   content49 ,
                                         String   content50 ,
                                         String   content51 ,
                                         String   content52 ,
                                         String   content53 ,
                                         String   content54 ,
                                         String   content55 ,
                                         String   content56 ,
                                         String   content57 ,
                                         String   content58 ,
                                         String   content59 ,
                                         String   content60 ,
                                         String   content61 ,
                                         String   content62 ,
                                         String   content63 ,
                                         String   content64 ,
                                         String   content65 ,
                                         String   content66 ,
                                         String   content67 ,
                                         String   content68 ,
                                         String   content69 ,
                                         String   content70 ,
                                         String   content71 ,
                                         String   content72 ,
                                         String   content73 ,
                                         String   content74 ,
                                         String   content75 ,
                                         String   content76 ,
                                         String   content77 ,
                                         String   content78 ,
                                         String   content79 ,
                                         String   content80 ,
                                         String   content81 ,
                                         String   content82 ,
                                         String   content83 ,
                                         String   content84 ,
                                         String   content85 ,
                                         String   content86 ,
                                         String   content87 ,
                                         String   content88 ,
                                         String   content89 ,
                                         String   content90 ,
                                         String   content91 ,
                                         String   content92 ,
                                         String   content93 ,
                                         String   content94 ,
                                         String   content95 ,
                                         String   content96 ,
                                         String   content97 ,
                                         String   content98 ,
                                         String   content99 ,
                                         String   content100,
                                         String   content101,
                                         String   content102,
                                         String   content103,
                                         String   content104,
                                         String   content105,
                                         String   content106,
                                         String   content107,
                                         String   content108,
                                         String   content109,
                                         String   content110,
                                         String   content111,
                                         String   content112,
                                         String   content113,
                                         String   content114,
                                         String   content115,
                                         String   content116,
                                         String   content117,
                                         String   content118,
                                         String   content119,
                                         String   content120,
                                         String   content121,
                                         String   content122,
                                         String   content123,
                                         String   content124,
                                         String   content125,
                                         String   content126,
                                         String   content127,
                                         String   content128,
                                         String   content129,
                                         String   content130,
                                         String   content131,
                                         String   content132,
                                         String   content133,
                                         String   content134,
                                         String   content135,
                                         String   content136,
                                         String   content137,
                                         String   content138,
                                         String   content139,
                                         String   content140,
                                         String   content141,
                                         String   content142,
                                         String   content143,
                                         String   content144,
                                         String   content145,
                                         String   content146,
                                         String   content147,
                                         String   content148,
                                         String   content149,
                                         String   content150,
                                         String   content151,
                                         String   content152,
                                         String   content153,
                                         String   content154,
                                         String   content155,
                                         String   content156,
                                         String   content157,
                                         String   content158,
                                         String   content159,
                                         String   content160,
                                         String   content161){
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         KEY_MASTERTABLEID          ,  content1      );
        contentValues.put  (         TICKETNUMBER               ,  content2     );
        contentValues.put  (         CUSTACCUNT                 ,  content3      );
        contentValues.put  (         CUSTGROUP                  ,  content4      );
        contentValues.put  (         DEVICEID                   ,  content5      );
        contentValues.put  (         APPLICATIONNO              ,  content6      );
        contentValues.put  (         OWNERSHIP                  ,  content7      );
        contentValues.put  (         PANCODE                    ,  content8      );
        contentValues.put  (         PHASE                      ,  content9      );
        contentValues.put  (         LOADREQUIRED               ,  content10      );
        contentValues.put  (         REQUIREDLOADUNIT           ,  content11      );
        contentValues.put  (         CAPACITY                   ,  content12      );
        contentValues.put  (         REQUIREDUNIT               ,  content13      );
        contentValues.put  (         APPLIEDSANCTIONDEMAND      ,  content14      );
        contentValues.put  (         SUPPLYLEVEL                ,  content15      );
        contentValues.put  (         CUSTCATGROUP               ,  content16      );
        contentValues.put  (         MD_CODE                    ,  content17      );
        contentValues.put  (         DIV_CODE                   ,  content18      );
        contentValues.put  (         SUB_DIV_CODE               ,  content19      );
        contentValues.put  (         SEC_CODE                   ,  content20      );
        contentValues.put  (         SEC_NAME                   ,  content21      );
        contentValues.put  (         NEWFIRSTNAME               ,  content22      );
        contentValues.put  (         NEWLASTNAME                ,  content23      );
        contentValues.put  (         NEWMIDDLENAME              ,  content24      );
        contentValues.put  (         OLDBUILDINGCOMPLIMENT      ,  content25      );
        contentValues.put  (         OLDCITY                    ,  content26      );
        contentValues.put  (         OLDCOUNTRYREGIONID         ,  content27      );
        contentValues.put  (         OLDDISTRICT                ,  content28      );
        contentValues.put  (         OLDSTATE                   ,  content29      );
        contentValues.put  (         OLDSTREET                  ,  content30      );
        contentValues.put  (         OLDZIPCODE                 ,  content31      );
        contentValues.put  (         ADDRESSREFRECID            ,  content32      );
        contentValues.put  (         APPLICATIONSTATUS          ,  content33      );
        contentValues.put  (         CONNECTIONID               ,  content34      );
        contentValues.put  (         COPYOFITSREQUIREDFEDBACK   ,  content35      );
        contentValues.put  (         APPLICATIONDATE            ,  content36      );
        contentValues.put  (         APPLICATIONTYPE            ,  content37      );
        contentValues.put  (         CONNECTIONSTATUS           ,  content38      );
        contentValues.put  (         HTIDLEDEMANDOFFACTORY      ,  content39      );
        contentValues.put  (         HTWORKINGHOUR              ,  content40      );
        contentValues.put  (         INDLOADFACTOR              ,  content41      );
        contentValues.put  (         METERCHANGEREQ             ,  content42      );
        contentValues.put  (         ORGANISATIONTYPE           ,  content43      );
        contentValues.put  (         GROUPCHANG                 ,  content44      );
        contentValues.put  (         ISAPPROVED                 ,  content45      );
        contentValues.put  (         ISCONFIGURATIONTERMINATED  ,  content46      );
        contentValues.put  (         ISEXISTINGAGREEMENTENDED   ,  content47      );
        contentValues.put  (         ISEXISTINGDEVICEREPLACED   ,  content48      );
        contentValues.put  (         ISNEWAGREEMENTCREATED      ,  content49      );
        contentValues.put  (         ISNEWCONFIGURATIONCREATED  ,  content50      );
        contentValues.put  (         ISREQBLOCKUNBLOCK          ,  content51      );
        contentValues.put  (         ISSERIALNUMBERCHANGED      ,  content52      );
        contentValues.put  (         ISUPDATEBLOCKUNBLOCK       ,  content53      );
        contentValues.put  (         ISVALIDATE                 ,  content54      );
        contentValues.put  (         EXTERNALID1                ,  content55      );
        contentValues.put  (         MESSAGE                    ,  content56      );
        contentValues.put  (         REJECTIONREMARKS           ,  content57      );
        contentValues.put  (         RESPONSIBLEJOBID           ,  content58      );
        contentValues.put  (         RESPONSIBLEWORKER          ,  content59      );
        contentValues.put  (         STREETNUMBER               ,  content60      );
        contentValues.put  (         MODIFIEDDATETIME           ,  content61      );
        contentValues.put  (         MODIFIEDBY                 ,  content62      );
        contentValues.put  (         CREATEDDATETIME            ,  content63      );
        contentValues.put  (         CREATEDBY                  ,  content64      );
        contentValues.put  (         CO_CODE                    ,  content65     );
        contentValues.put  (         DISCOM_CODE                ,  content66     );
        contentValues.put  (         CIRCLE_CODE                ,  content67      );
        contentValues.put  (         MOBILE_NUMBER              ,  content68      );
        contentValues.put  (         EMAIL_ID                   ,  content69      );
        contentValues.put  (         BLOCK                      ,  content70      );
        contentValues.put  (         BUILDING                   ,  content71      );
        contentValues.put  (         CITY                       ,  content72      );
        contentValues.put  (         DISTRICT                   ,  content73      );
        contentValues.put  (         GP                         ,  content74      );
        contentValues.put  (         PLOT                       ,  content75      );
        contentValues.put  (         STREET                     ,  content76      );
        contentValues.put  (         TEHSIL                     ,  content77      );
        contentValues.put  (         VILLAGE                    ,  content78      );
        contentValues.put  (         BLOCK1                     ,  content79      );
        contentValues.put  (         CITY1                      ,  content80      );
        contentValues.put  (         DISTRICT1                  ,  content81      );
        contentValues.put  (         GP1                        ,  content82      );
        contentValues.put  (         PLOT1                      ,  content83      );
        contentValues.put  (         STREET1                    ,  content84      );
        contentValues.put  (         TEHSIL1                    ,  content85      );
        contentValues.put  (         VILLAGE1                   ,  content86      );
        contentValues.put  (         BUILDING1                  ,  content87      );
        contentValues.put  (         ISHOOKING                  ,  content88      );
        contentValues.put  (         FATHERNAME                 ,  content89      );
        contentValues.put  (         NEARESTLANDMARK            ,  content90      );
        contentValues.put  (         CONNECTIONTYPE             ,  content91      );
        contentValues.put  (         COMPLAINTSUBCATEGORY       ,  content92      );
        contentValues.put  (         COMPLAINTDETAILS           ,  content93      );
        contentValues.put  (         SOURCEOFCOMPLAINT          ,  content94      );
        contentValues.put  (         COMPLAINTCATEGORY          ,  content95      );
        contentValues.put  (         COMPLAINT_CLOSEDDATE       ,  content96      );
        contentValues.put  (         COMPLAINT_COMMENT          ,  content97      );
        contentValues.put  (         COMPLAINT_STATUS           ,  content98      );
        contentValues.put  (         COMPLAINTTYPE              ,  content99       );
        contentValues.put  (         TEMPORARYFROMDATE          ,  content100      );
        contentValues.put  (         TEMPORARYTODATE            ,  content101      );
        contentValues.put  (         COMPLAINT_CLOSEDBY         ,  content102      );
        contentValues.put  (         USER_COMMENT               ,  content103      );
        contentValues.put  (         CYCLE_ID                   ,  content104      );
        contentValues.put  (         ROUTE_ID                   ,  content105      );
        contentValues.put  (         CESU_CONSUMER_NO           ,  content106      );
        contentValues.put  (         CONSUMER_TYPE              ,  content107      );
        contentValues.put  (         SCEME                      ,  content108      );
        contentValues.put  (         CONNECTION_FOR             ,  content109      );
        contentValues.put  (         ED_EXEMPTION               ,  content110     );
        contentValues.put  (         ED_EXEMPTION_PERCENTAGE    ,  content111      );
        contentValues.put  (         NAME_OF_ORGANISATION       ,  content112      );
        contentValues.put  (         FATHERORHUSBAND_NAME       ,  content113      );
        contentValues.put  (         TYPE_OF_ORGANISATION       ,  content114      );
        contentValues.put  (         LANDLINE_NUMBER            ,  content115      );
        contentValues.put  (         TRANSFER_OWNERSHIP         ,  content116      );
        contentValues.put  (         METERING_SIDE              ,  content117      );
        contentValues.put  (         TRANSFERMER_CAPACITY       ,  content118      );
        contentValues.put  (         INDUSTRY_TYPE              ,  content119      );
        contentValues.put  (         NATURE_OF_JOB              ,  content120      );
        contentValues.put  (         WORKING_HOURS              ,  content121      );
        contentValues.put  (         LOAD_FACTOR                ,  content122      );
        contentValues.put  (         MAX_DEMAND                 ,  content123      );
        contentValues.put  (         SHIFTS                     ,  content124      );
        contentValues.put  (         MD_PEAK_HOURS              ,  content125      );
        contentValues.put  (         SUBSTATION                 ,  content126      );
        contentValues.put  (         FEEDER                     ,  content127      );
        contentValues.put  (         DT                         ,  content128      );
        contentValues.put  (         IS_METER                   ,  content129      );
        contentValues.put  (         PIN_NO                     ,  content130      );
        contentValues.put  (         SUPPLY_TYPE                ,  content131      );
        contentValues.put  (         TITLE                      ,  content132      );
        contentValues.put  (         DESIGNATION_OF_SIGNATORY   ,  content133      );
        contentValues.put  (         SIGNATORY_NAME             ,  content134      );
        contentValues.put  (         DEPARTMENT                 ,  content135      );
        contentValues.put  (         PIN_NO_BILLED              ,  content136      );
        contentValues.put  (         ORDER_NO                   ,  content137      );
        contentValues.put  (         ORDER_DATE                 ,  content138      );
        contentValues.put  (         OTHER_CONNECTIONFOR        ,  content139      );
        contentValues.put  (         DATEOF_POWERSUPPLY         ,  content140      );
        contentValues.put  (         DISCON_RECON_DATE          ,  content141      );
        contentValues.put  (         KWH                        ,  content142      );
        contentValues.put  (         KVAH                       ,  content143      );
        contentValues.put  (         KVARH                      ,  content144      );
        contentValues.put  (         MDKW                       ,  content145      );
        contentValues.put  (         MDKW_UNIT                  ,  content146      );
        contentValues.put  (         PEAKKWH                    ,  content147      );
        contentValues.put  (         PEAKKVAH                   ,  content148      );
        contentValues.put  (         PEAKKVARH                  ,  content149      );
        contentValues.put  (         PEAKMDKW                   ,  content150      );
        contentValues.put  (         PEAKMDKWUNIT               ,  content151      );
        contentValues.put  (         OFFPEAKKWH                 ,  content152      );
        contentValues.put  (         OFFPEAKKVAH                ,  content153      );
        contentValues.put  (         OFFPEAKKVARH               ,  content154      );
        contentValues.put  (         OFFPEAKMDKW                ,  content155      );
        contentValues.put  (         OFFPEAKMDKWUNIT            ,  content156      );
        contentValues.put  (         METER_STATUS               ,  content157      );
        contentValues.put  (         REGISTER_NUMBER            ,  content158      );
        contentValues.put  (         PAGE_NUMBER                ,  content159      );
        contentValues.put  (         ITEM_NUMBER                ,  content160      );
        contentValues.put  (         RECORD_UPDATED_FLAG        ,  content161      );
        return sqLiteDatabase.insert(TBL_MASTERTABLE, null, contentValues);
    }

    public long mastersubdivision_insert_new(String   content1  ,
                                             String   content2  ,
                                             String   content3  ,
                                             String   content4  ,
                                             String   content5  ,
                                             String   content6  ,
                                             String   content7  ,
                                             String   content8  ,
                                             String   content9  ,
                                             String   content10 ,
                                             String   content11 ,
                                             String   content12 ,
                                             String   content13 ,
                                             String   content14 ,
                                             String   content15 ,
                                             String   content16 ,
                                             String   content17 ,
                                             String   content18 ,
                                             String   content19 ,
                                             String   content20 ,
                                             String   content21 ,
                                             String   content22 ,
                                             String   content23 ,
                                             String   content24 ,
                                             String   content25 ,
                                             String   content26 ,
                                             String   content27 ,
                                             String   content28 ,
                                             String   content29 ,
                                             String   content30 ,
                                             String   content31 ,
                                             String   content32 ,
                                             String   content33 ,
                                             String   content34 ,
                                             String   content35 ,
                                             String   content36,
                                             String   content37,
                                             String   content38,
                                             String   content39,
                                             String   content40,
                                             String   content41,
                                             String   content42)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         DIV_CODE                   ,  content1     );
        contentValues.put  (         SUB_DIV_CODE               ,  content2      );
        contentValues.put  (         SEC_CODE                   ,  content3      );
        contentValues.put  (         ROUTE_ID                   ,  content4      );
        contentValues.put  (         NEWFIRSTNAME               ,  content5      );
        contentValues.put  (         FATHERORHUSBAND_NAME       ,  content6      );
        contentValues.put  (         NAME_OF_ORGANISATION       ,  content7      );
        contentValues.put  (         TYPE_OF_ORGANISATION       ,  content8      );
        contentValues.put  (         SIGNATORY_NAME             ,  content9      );
        contentValues.put  (         BLOCK                      ,  content10      );
        contentValues.put  (         BUILDING                   ,  content11      );
        contentValues.put  (         CITY                       ,  content12      );
        contentValues.put  (         DISTRICT                   ,  content13      );
        contentValues.put  (         GP                         ,  content14      );
        contentValues.put  (         PLOT                       ,  content15      );
        contentValues.put  (         STREET                     ,  content16      );
        contentValues.put  (         TEHSIL                     ,  content17      );
        contentValues.put  (         VILLAGE                    ,  content18      );
        contentValues.put  (         BLOCK1                     ,  content19      );
        contentValues.put  (         CITY1                      ,  content20      );
        contentValues.put  (         DISTRICT1                  ,  content21      );
        contentValues.put  (         GP1                        ,  content22      );
        contentValues.put  (         PLOT1                      ,  content23      );
        contentValues.put  (         STREET1                    ,  content24      );
        contentValues.put  (         TEHSIL1                    ,  content25      );
        contentValues.put  (         VILLAGE1                   ,  content26      );
        contentValues.put  (         BUILDING1                  ,  content27      );
        contentValues.put  (         MOBILE_NUMBER              ,  content28      );
        contentValues.put  (         EMAIL_ID                   ,  content29      );
        contentValues.put  (         LANDLINE_NUMBER            ,  content30      );
        contentValues.put  (     DESIGNATION_OF_SIGNATORY  ,  content31     );
        contentValues.put  (     LOADREQUIRED  ,  content32     );
        contentValues.put  (     CUSTGROUP  ,  content33     );
        contentValues.put  (         POLE_LAT            ,  content34      );
        contentValues.put  (         POLE_LONG            ,  content35      );
        contentValues.put  (         HOME_LAT           ,  content36      );
        contentValues.put  (         HOME_LONG            ,  content37      );
        contentValues.put  (         IMAGE            ,  content38      );
        contentValues.put  (         IMAGE_NAME            ,  content39      );
        contentValues.put  (         PANCODE            ,  content40     );
        contentValues.put  (         PIN_NO            ,  content41     );
        contentValues.put  (         PIN_NO_BILLED            ,  content42     );
        return sqLiteDatabase.insert(TBL_MASTERTABLE, null, contentValues);}



    public long insert_update_consumer(String   content1  ,
                                             String   content2  ,
                                             String   content3  ,
                                             String   content4  ,
                                             String   content5  ,
                                             String   content6  ,
                                             String   content7  ,
                                             String   content8  ,
                                             String   content9  ,
                                             String   content10 ,
                                             String   content11 ,
                                             String   content12 ,
                                             String   content13 ,
                                             String   content14 ,
                                             String   content15 ,
                                             String   content16 ,
                                             String   content17 ,
                                             String   content18 ,
                                             String   content19 ,
                                             String   content20 ,
                                             String   content21 ,
                                             String   content22 ,
                                             String   content23 ,
                                             String   content24 ,
                                             String   content25 ,
                                             String   content26 ,
                                             String   content27 ,
                                             String   content28 ,
                                             String   content29 ,
                                             String   content30 ,
                                             String   content31 ,
                                             String   content32 ,
                                             String   content33 ,
                                             String   content34 ,
                                             String   content35 ,
                                             String   content36,
                                             String   content37,
                                             String   content38,
                                             String   content39,
                                             String   content40,
                                             String   content41,
                                             String   content42,
                                             String   content43)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         DIV_CODE                   ,  content1     );
        contentValues.put  (         SUB_DIV_CODE               ,  content2      );
        contentValues.put  (         SEC_CODE                   ,  content3      );
        contentValues.put  (         SEC_NAME                   ,  content4      );
        contentValues.put  (         NEWFIRSTNAME               ,  content5      );
        contentValues.put  (         FATHERORHUSBAND_NAME       ,  content6      );
        contentValues.put  (         NAME_OF_ORGANISATION       ,  content7      );
        contentValues.put  (         TYPE_OF_ORGANISATION       ,  content8      );
        contentValues.put  (         SIGNATORY_NAME             ,  content9      );
        contentValues.put  (         BLOCK                      ,  content10      );
        contentValues.put  (         BUILDING                   ,  content11      );
        contentValues.put  (         CITY                       ,  content12      );
        contentValues.put  (         DISTRICT                   ,  content13      );
        contentValues.put  (         GP                         ,  content14      );
        contentValues.put  (         PLOT                       ,  content15      );
        contentValues.put  (         STREET                     ,  content16      );
        contentValues.put  (         TEHSIL                     ,  content17      );
        contentValues.put  (         VILLAGE                    ,  content18      );
        contentValues.put  (         BLOCK1                     ,  content19      );
        contentValues.put  (         CITY1                      ,  content20      );
        contentValues.put  (         DISTRICT1                  ,  content21      );
        contentValues.put  (         GP1                        ,  content22      );
        contentValues.put  (         PLOT1                      ,  content23      );
        contentValues.put  (         STREET1                    ,  content24      );
        contentValues.put  (         TEHSIL1                    ,  content25      );
        contentValues.put  (         VILLAGE1                   ,  content26      );
        contentValues.put  (         BUILDING1                  ,  content27      );
        contentValues.put  (         MOBILE_NUMBER              ,  content28      );
        contentValues.put  (         EMAIL_ID                   ,  content29      );
        contentValues.put  (         LANDLINE_NUMBER            ,  content30      );
        contentValues.put  (     DESIGNATION_OF_SIGNATORY  ,  content31     );
        contentValues.put  (     LOADREQUIRED  ,  content32     );
        contentValues.put  (     CUSTGROUP  ,  content33     );
        contentValues.put  (         POLE_LAT            ,  content34      );
        contentValues.put  (         POLE_LONG            ,  content35      );
        contentValues.put  (         HOME_LAT           ,  content36      );
        contentValues.put  (         HOME_LONG            ,  content37      );
        contentValues.put  (         IMAGE            ,  content38      );
        contentValues.put  (         IMAGE_NAME            ,  content39      );
        contentValues.put  (         PANCODE            ,  content40     );
        contentValues.put  (         PIN_NO            ,  content41     );
        contentValues.put  (         PIN_NO_BILLED            ,  content42     );
        contentValues.put  (         TICKETNUMBER            ,  content43    );

        return sqLiteDatabase.insert(TBL_MASTERTABLE, null, contentValues);
    }




    public Cursor updated_record_value(){
        String[] columns = new String[]{KEY_MASTERTABLEID,    DIV_CODE,        SUB_DIV_CODE,
                SEC_CODE,             NEWFIRSTNAME,     FATHERORHUSBAND_NAME,
                NAME_OF_ORGANISATION, TYPE_OF_ORGANISATION,    SIGNATORY_NAME,
                BLOCK                      , BUILDING                   ,CITY                       , DISTRICT                   ,
                GP                         , PLOT                       ,STREET                     , TEHSIL                     ,
                VILLAGE                    , BLOCK1                     ,CITY1                      , DISTRICT1                  ,
                GP1                        , PLOT1                      ,STREET1                    , TEHSIL1                    ,
                VILLAGE1                   , BUILDING1                  ,MOBILE_NUMBER              ,EMAIL_ID                     ,
                LANDLINE_NUMBER,             DESIGNATION_OF_SIGNATORY,     LOADREQUIRED             ,CUSTGROUP                   ,
                POLE_LAT,             POLE_LONG,     HOME_LAT             ,HOME_LONG,
                IMAGE, IMAGE_NAME,PANCODE,PIN_NO,PIN_NO_BILLED, TICKETNUMBER };
        Cursor cursor = sqLiteDatabase.query(TBL_MASTERTABLE, columns, null, null, null, null, null);
        return cursor;

    }
    public void delete_updated_byID(int id){
        sqLiteDatabase.delete(TBL_MASTERTABLE, KEY_MASTERTABLEID+"="+id, null);
    }



    //........................feasibility table.................................//




    public static final String TABLE_FEASIBILITY              ="TABLE_FEASIBILITY";
    public static final String TABLE_FEASIBILITY_ID           ="TABLE_FEASIBILITY_ID";
    public static final String FEASIBILITY_TAG                ="FEASIBILITY_TAG";
    public static final String FEASIBILITY_TICKET_NO          ="FEASIBILITY_TICKET_NO";
    public static final String FEASIBILITY_HOME_LAT           ="FEASIBILITY_HOME_LAT";
    public static final String FEASIBILITY_HOME_LONG          ="FEASIBILITY_HOME_LONG";
    public static final String FEASIBILITY_POLE_LAT           ="FEASIBILITY_POLE_LAT";
    public static final String FEASIBILITY_POLE_LONG          ="FEASIBILITY_POLE_LONG";
    public static final String FEASIBILITY_ROUTE              ="FEASIBILITY_ROUTE";
    public static final String FEASIBILITY_VALUE              ="FEASIBILITY_VALUE";
    public static final String FEASIBILITY_MANUAL              ="FEASIBILITY_MANUAL";

    private static final String CREATE_TABLE_FEASIBILITY="CREATE TABLE "+TABLE_FEASIBILITY +
            "(\n" +
            "  TABLE_FEASIBILITY_ID             INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL,\n" +
            "  FEASIBILITY_TAG              VARCHAR2(20),\n" +
            "  FEASIBILITY_TICKET_NO        VARCHAR2(20),\n" +
            "  FEASIBILITY_HOME_LAT         VARCHAR2(20),\n" +
            "  FEASIBILITY_HOME_LONG        VARCHAR2(20),\n" +
            "  FEASIBILITY_POLE_LAT         VARCHAR2(20),\n" +
            "  FEASIBILITY_POLE_LONG        VARCHAR2(20),\n" +
            "  FEASIBILITY_ROUTE           VARCHAR2(20),\n" +
            "  FEASIBILITY_VALUE          VARCHAR2(20),\n" +
            "  FEASIBILITY_MANUAL            VARCHAR2(20)\n" +
            ");";

    public long insert_feasibility(String   content1  ,
                                 String   content2  ,
                                 String   content3  ,
                                 String   content4  ,
                                 String   content5  ,
                                 String   content6   ,
                                 String   content7  ,
                                   String   content8 ,
                                 String   content9
    )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         FEASIBILITY_TAG               ,  content1      );
        contentValues.put  (         FEASIBILITY_TICKET_NO         ,  content2      );
        contentValues.put  (         FEASIBILITY_HOME_LAT          ,  content3      );
        contentValues.put  (         FEASIBILITY_HOME_LONG         ,  content4      );
        contentValues.put  (         FEASIBILITY_POLE_LAT          ,  content5      );
        contentValues.put  (         FEASIBILITY_POLE_LONG         ,  content6      );
        contentValues.put  (         FEASIBILITY_ROUTE         ,  content7      );
        contentValues.put  (         FEASIBILITY_VALUE             ,  content8      );
        contentValues.put  (         FEASIBILITY_MANUAL             ,  content9     );



        return sqLiteDatabase.insert(TABLE_FEASIBILITY, null, contentValues);
    }

    public Cursor feasibility_value(){
        String[] columns = new String[]{TABLE_FEASIBILITY_ID,
                FEASIBILITY_TAG,
                FEASIBILITY_TICKET_NO,
                FEASIBILITY_HOME_LAT,
                FEASIBILITY_HOME_LONG,
                FEASIBILITY_POLE_LAT,
                FEASIBILITY_POLE_LONG,
                FEASIBILITY_ROUTE,
                FEASIBILITY_VALUE,
                FEASIBILITY_MANUAL
        };
        Cursor cursor = sqLiteDatabase.query(TABLE_FEASIBILITY, columns, null, null, null, null, null);
        return cursor;

    }
    public void delete_byID_feasibility(int id){
        sqLiteDatabase.delete(TABLE_FEASIBILITY, TABLE_FEASIBILITY_ID+"="+id, null);
    }

    //........................new meter table.................................//




    public static final String TABLE_NEW_METER                  ="TABLE_NEW_METER";
    public static final String TABLE_NEW_METER_ID               ="TABLE_NEW_METER_ID";
    public static final String NEW_METER_TAG                    ="NEW_METER_TAG";
    public static final String NEW_METER_TICKETNUMBER           ="NEW_METER_TICKETNUMBER";
    public static final String NEW_METER_NO                     ="NEW_METER_NO";
    public static final String NEW_METER_MANUFACTURER_CODE      ="NEW_METER_MANUFACTURER_CODE";
    public static final String NEW_METER_TYPE                   ="NEW_METER_TYPE";
    public static final String NEW_METER_PHASE                  ="NEW_METER_PHASE";
    public static final String NEW_METER_INSTALLED_DATE         ="NEW_METER_INSTALLED_DATE";
    public static final String NEW_METER_INITIAL_READING        ="NEW_METER_INITIAL_READING";
    public static final String NEW_METER_BILL_BASIS             ="NEW_METER_BILL_BASIS";
    public static final String NEW_METER_METER_DIGIT            ="NEW_METER_METER_DIGIT";
    public static final String NEW_METER_OWNERSHIP              ="NEW_METER_OWNERSHIP";
    public static final String NEW_METER_METERED                ="NEW_METER_METERED";


    private static final String NEW_METER="CREATE TABLE "+TABLE_NEW_METER +
            "(\n" +
            "  TABLE_NEW_METER_ID             INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL,\n" +
            "  NEW_METER_TAG                  VARCHAR2(25),\n" +
            "  NEW_METER_TICKETNUMBER         VARCHAR2(50),\n" +
            "  NEW_METER_NO                   VARCHAR2(25),\n" +
            "  NEW_METER_MANUFACTURER_CODE    VARCHAR2(25),\n" +
            "  NEW_METER_TYPE                 VARCHAR2(25),\n" +
            "  NEW_METER_PHASE                 VARCHAR2(30),\n" +
            "  NEW_METER_INSTALLED_DATE                 VARCHAR2(20),\n" +
            "  NEW_METER_INITIAL_READING                 VARCHAR2(20),\n" +
            "  NEW_METER_BILL_BASIS                 VARCHAR2(20),\n" +
            "  NEW_METER_METER_DIGIT                 VARCHAR2(20),\n" +
            "  NEW_METER_OWNERSHIP                 VARCHAR2(20),\n" +
            "  NEW_METER_METERED                 VARCHAR2(20)\n" +
            ");";

    public long insert_new_meter(String   content1  ,
                                             String   content2  ,
                                             String   content3  ,
                                             String   content4  ,
                                             String   content5  ,
                                             String   content6  ,
                                             String   content7  ,
                                             String   content8  ,
                                             String   content9  ,
                                             String   content10 ,
                                             String   content11 ,
                                             String   content12 )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put  (         NEW_METER_TAG                     ,  content1      );
        contentValues.put  (         NEW_METER_TICKETNUMBER            ,  content2      );
        contentValues.put  (         NEW_METER_NO                      ,  content3      );
        contentValues.put  (         NEW_METER_MANUFACTURER_CODE       ,  content4      );
        contentValues.put  (         NEW_METER_TYPE                    ,  content5      );
        contentValues.put  (         NEW_METER_PHASE                   ,  content6      );
        contentValues.put  (         NEW_METER_INSTALLED_DATE          ,  content7      );
        contentValues.put  (         NEW_METER_INITIAL_READING         ,  content8      );
        contentValues.put  (         NEW_METER_BILL_BASIS              ,  content9      );
        contentValues.put  (         NEW_METER_METER_DIGIT             ,  content10      );
        contentValues.put  (         NEW_METER_OWNERSHIP               ,  content11      );
        contentValues.put  (         NEW_METER_METERED                 ,  content12     );
        //Log.e("content value new meter :->", ""+contentValues);
        return sqLiteDatabase.insert(TABLE_NEW_METER, null, contentValues);
    }


    public Cursor new_meter_value(){
        String[] columns = new String[]{TABLE_NEW_METER_ID,
                NEW_METER_TAG,
                NEW_METER_TICKETNUMBER,
                NEW_METER_NO,
                NEW_METER_MANUFACTURER_CODE,
                NEW_METER_TYPE,
                NEW_METER_PHASE,
                NEW_METER_INSTALLED_DATE,
                NEW_METER_INITIAL_READING,
                NEW_METER_BILL_BASIS,
                NEW_METER_METER_DIGIT,
                NEW_METER_OWNERSHIP ,
                NEW_METER_METERED
                };
        Cursor cursor = sqLiteDatabase.query(TABLE_NEW_METER, columns, null, null, null, null, null);
        return cursor;

    }
    public void delete_byID_new_meter(int id){
        sqLiteDatabase.delete(TABLE_NEW_METER, TABLE_NEW_METER_ID+"="+id, null);
    }

    //    public  static final String  SUPPLY_TYPE                          ="SUPPLY_TYPE";
//#############################################   TABLE FINISH   #########################################
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    public SQLiteMasterTableAdapter(Context c)
    {
        context = c;
    }
    public SQLiteMasterTableAdapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }
    public SQLiteMasterTableAdapter openToWrite() throws android.database.SQLException {
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
          //  Log.e("MASTERTABLE","start");
            db.execSQL(MASTERTABLE);
            db.execSQL(NEW_METER);
            db.execSQL(CREATE_TABLE_FEASIBILITY);

           // Log.e("MASTERTABLE","end");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TBL_MASTERTABLE);
            onCreate(db);
        }
    }
}
