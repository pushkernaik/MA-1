package utils;

import android.app.Activity;
import android.content.pm.PackageInfo;

/**
 * Created by radhaprasadborkar on 06/01/16.
 */
public class Keys {
    public static String ENCRYPTION_KEY = "PICCENCRYPTIONDESFIREEV1";

    public static String getApplicationVersion(Activity context){
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            return version;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
