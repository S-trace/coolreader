package compat.virxkane;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Perms {
    public static final int PERM_REQUEST_STORAGE_CODE = 1;
    public static final int PERM_REQUEST_READ_PHONE_STATE_CODE = 2;

    private final Activity mActivity;
    private static final String TAG = Perms.class.getSimpleName();

    public Perms(Activity activity) {
        mActivity = activity;
    }

    @SuppressLint("ObsoleteSdkInt")
    public void requestStoragePermissions() {
        // check or request permission for storage
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        ArrayList<String> needPerms = new ArrayList<>();

        if (mActivity.checkSelfPermission(READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            needPerms.add(READ_EXTERNAL_STORAGE);
        } else {
            Log.i(TAG, "READ_EXTERNAL_STORAGE permission already granted.");
        }

        if (mActivity.checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            needPerms.add(WRITE_EXTERNAL_STORAGE);
        } else {
            Log.i(TAG, "WRITE_EXTERNAL_STORAGE permission already granted.");
        }

        if (needPerms.isEmpty()) {
            return;
        }

        // TODO: Show an explanation to the user
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        String[] templ = new String[0];
        Log.i(TAG, "Some permissions DENIED, " +
                "requesting from user these permissions: " + needPerms);
        // request permission from user
        mActivity.requestPermissions(needPerms.toArray(templ), PERM_REQUEST_STORAGE_CODE);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void requestReadPhoneStatePermissions() {
        // check or request permission for storage
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (mActivity.checkSelfPermission(READ_PHONE_STATE) == PERMISSION_GRANTED) {
            Log.i(TAG, "READ_PHONE_STATE permission already granted.");
            return;
        }

        Log.i(TAG, "READ_PHONE_STATE permission DENIED, requesting from user");
        // TODO: Show an explanation to the user
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        // request permission from user
        String[] permissions = new String[]{READ_PHONE_STATE};
        mActivity.requestPermissions(permissions, PERM_REQUEST_READ_PHONE_STATE_CODE);
    }

    @SuppressLint("ObsoleteSdkInt")
    public boolean isReadPhoneStateAvailable() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return mActivity.checkSelfPermission(READ_PHONE_STATE) == PERMISSION_GRANTED;
    }

}
