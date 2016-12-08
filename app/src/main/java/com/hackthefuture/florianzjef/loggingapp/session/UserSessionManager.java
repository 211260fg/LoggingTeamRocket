package com.hackthefuture.florianzjef.loggingapp.session;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.hackthefuture.florianzjef.loggingapp.activities.AuthActivity;
import com.hackthefuture.florianzjef.loggingapp.models.Researcher;

import java.io.Serializable;
import java.util.HashMap;


public class UserSessionManager implements Parcelable, Serializable {

    //Secured Preferences reference
    private static SecurePreferences secpref;

    private Gson gson;

    // Context
    static Context _context;

    // Sharedpref file name
    private static final String PREFER_NAME = "HTFSharedpref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";


    private static final String KEY_CURRENT_USER = "currentuser";


    // Constructor
    public UserSessionManager(Context context) {
        _context = context;
        secpref = new SecurePreferences(context, PREFER_NAME, "secret", true);
    }


    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<UserSessionManager> CREATOR = new Parcelable.Creator<UserSessionManager>() {
        public UserSessionManager createFromParcel(Parcel in) {
            return new UserSessionManager(_context);
        }

        public UserSessionManager[] newArray(int size) {
            return new UserSessionManager[size];
        }
    };



    //Create login session
    //TODO off main thread
    public void createUserLoginSession(String name, String email) {
        // Storing login value as TRUE
        secpref.put(IS_USER_LOGIN, true);

        // Storing name in pref
        secpref.put(KEY_NAME, name);

        // Storing icon_email in pref
        secpref.put(KEY_PASSWORD, email);
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AuthActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra("SESSION", (Parcelable) this);

            // Starting Login Activity
            _context.startActivity(i);

            return false;
        }
        return true;
    }


    /**
     * Get stored session data
     */
    public static HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(KEY_NAME, secpref.getString(KEY_NAME));


        // user icon_email id
        user.put(KEY_PASSWORD, secpref.getString(KEY_PASSWORD));

        // return user
        return user;
    }

    /**
     * Clear session details
     */

    //TODO off main thread
    public static void logoutUser() {

        // Clearing all user data from Shared Preferences
        secpref.clear();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, AuthActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Logout from facebook
        /*LoginManager.getInstance().logOut();*/

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return secpref.getBoolean(IS_USER_LOGIN);
        //return false;
    }


    public void saveCurrentUser(Researcher user){
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        secpref.put(KEY_CURRENT_USER, json);
    }

    public static Researcher getCurrentUser(){
        Gson gson = new Gson();
        String json = secpref.getString(KEY_CURRENT_USER);
        Researcher currentuser = gson.fromJson(json, Researcher.class);

        return currentuser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flags);
    }
}
