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
        Context context = cordova.getActivity().getApplicationContext();
        PackageManager pm = context.getPackageManager();
 
        String pkg = context.getPackageName();
 
        // The alias names MUST match plugin.xml
        String normalAlias  = pkg + ".IconNormal";
        String premiumAlias = pkg + ".IconPremium";
        String privateAlias = pkg + ".IconPrivate";
 
        // Disable all
        setState(pm, normalAlias, false);
        setState(pm, premiumAlias, false);
        setState(pm, privateAlias, false);
 
        // Enable selected icon
        if ("premium".equalsIgnoreCase(icon)) {
            setState(pm, premiumAlias, true);
        } else if ("private".equalsIgnoreCase(icon)) {
            setState(pm, privateAlias, true);
        } else {
            setState(pm, normalAlias, true);
        }
 
        callback.success("Icon changed to: " + icon);
    }
 
    private void setState(PackageManager pm, String alias, boolean enabled) {
        ComponentName cn = new ComponentName(alias.substring(0, alias.lastIndexOf(".")), alias);
        pm.setComponentEnabledSetting(
            cn,
            enabled ?
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        );
    }
}
 
