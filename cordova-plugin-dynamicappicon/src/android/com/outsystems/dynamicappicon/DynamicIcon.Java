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
            String icon = args.optString(0);
            switchIcon(icon, callbackContext);
            return true;
        }
        return false;
    }
 
    private void switchIcon(String icon, CallbackContext callback) {
        try {
            Context context = cordova.getActivity().getApplicationContext();
            PackageManager pm = context.getPackageManager();
            String pkg = context.getPackageName();
 
            String normalAlias  = pkg + ".IconNormal";
            String premiumAlias = pkg + ".IconPremium";
            String privateAlias = pkg + ".IconPrivate";
 
            // disable all
            setState(pm, pkg, normalAlias, false);
            setState(pm, pkg, premiumAlias, false);
            setState(pm, pkg, privateAlias, false);
 
            // enable selected alias
            String enabledAlias;
            if ("premium".equalsIgnoreCase(icon)) {
                enabledAlias = premiumAlias;
            } else if ("private".equalsIgnoreCase(icon)) {
                enabledAlias = privateAlias;
            } else {
                enabledAlias = normalAlias;
            }
 
            setState(pm, pkg, enabledAlias, true);
 
            callback.success("Enabled alias: " + enabledAlias);
 
        } catch (Exception e) {
            callback.error("Error: " + e.getMessage());
        }
    }
 
    private void setState(PackageManager pm, String pkg, String fullAliasName, boolean enabled) throws Exception {
        try {
            ComponentName cn = new ComponentName(pkg, fullAliasName);
 
            pm.setComponentEnabledSetting(
                cn,
                enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            );
        } catch (Exception e) {
            throw new Exception("Failed for alias " + fullAliasName + ": " + e.getMessage());
        }
    }
}
