package com.outsystems.dynamicappicon;
 
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.ComponentName;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
 
import org.json.JSONArray;
 
public class DynamicIcon extends CordovaPlugin {
 
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if ("changeIcon".equals(action)) {
            String icon = args.optString(0, "normal");
            switchIcon(icon, callbackContext);
            return true;
        }
        return false;
    }
 
    private void switchIcon(String icon, CallbackContext callback) {
        Context context = cordova.getActivity().getApplicationContext();
        PackageManager pm = context.getPackageManager();
        String pkg = context.getPackageName();
 
        String normalAlias = pkg + ".IconNormal";
        String premiumAlias = pkg + ".IconPremium";
        String privateAlias = pkg + ".IconPrivate";
 
        // Disable all
        pm.setComponentEnabledSetting(
                new ComponentName(pkg, normalAlias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
        pm.setComponentEnabledSetting(
                new ComponentName(pkg, premiumAlias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
        pm.setComponentEnabledSetting(
                new ComponentName(pkg, privateAlias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
 
        // Enable selected
        String selectedAlias = normalAlias;
        if ("premium".equalsIgnoreCase(icon)) selectedAlias = premiumAlias;
        if ("private".equalsIgnoreCase(icon)) selectedAlias = privateAlias;
 
        pm.setComponentEnabledSetting(
                new ComponentName(pkg, selectedAlias),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
 
        callback.success("Changed to: " + icon);
    }
}
 
