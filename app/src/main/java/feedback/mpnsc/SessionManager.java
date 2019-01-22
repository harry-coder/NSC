package feedback.mpnsc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint({ "NewApi", "CommitPrefEdits" })
public class SessionManager {
	SharedPreferences session_pref;
	Editor editor;
	Context _context;
	int PRIVATE_MODE = 0;
	private static final String SESSSION_PREF = "session_pref";
	private static final String IS_LOGIN = "IsLoggedIn";
    private static final String SESSSION_URL = "session_url";
    private static final String DOWNLOAD = "download";
	public SessionManager(Context context){
	this._context = context;
	session_pref = _context.getSharedPreferences(SESSSION_PREF, PRIVATE_MODE);
	editor = session_pref.edit();
	}
    public void createLoginSession(){
		editor.putBoolean(IS_LOGIN, true);
		editor.commit();
	}
	public void logoutUser(){
		editor.clear();
		editor.commit();
	}
	public boolean isLoggedIn(){
		return session_pref.getBoolean(IS_LOGIN, false);	
	}


    public void change_url(String url)
    {
        editor.putString(SESSSION_URL, url);
        editor.commit();
    }
    public String GET_URL(){
        return session_pref.getString(SESSSION_URL,"http://103.192.172.18:8083/mpnsc.php");
    }


    public void download_completed(){
        editor.putBoolean(DOWNLOAD, true);
        editor.commit();
    }

    public boolean getdownload_completed(){

        return session_pref.getBoolean(DOWNLOAD, false);
    }




}