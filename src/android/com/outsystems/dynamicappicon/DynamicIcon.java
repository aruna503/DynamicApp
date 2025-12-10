package com.outsystems.dynamicappicon;
 
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.ComponentName;
import android.widget.Toast;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
 
import org.json.JSONArray;
 
public class DynamicIcon extends CordovaPlugin {
 
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
 
        Toast.makeText(cordova.getContext(), "Plugin Called: " + action, Toast.LENGTH_SHORT).show();
 
        if ("changeIcon".equals(action)) {
            String icon = args.optString(0, "normal");
 
            Toast.makeText(cordova.getContext(),
                    "Received icon: " + icon,
                    Toast.LENGTH_SHORT).show();
 
            switchIcon(icon, callbackContext);
            return true;
        }
 
        Toast.makeText(cordova.getContext(), "Unknown Action", Toast.LENGTH_SHORT).show();
        return false;
    }
 
    private void switchIcon(String icon, CallbackContext callback) {
 
        Toast.makeText(cordova.getContext(),
                "Switching to: " + icon,
                Toast.LENGTH_SHORT).show();
 
        Context context = cordova.getActivity().getApplicationContext();
        PackageManager pm = context.getPackageManager();
        String pkg = context.getPackageName();
 
        String normalAlias = ".IconNormal";
        String premiumAlias = ".IconPremium";
        String privateAlias = ".IconPrivate";
 
        // disable all
        setState(pm, pkg, normalAlias, false);
        setState(pm, pkg, premiumAlias, false);
        setState(pm, pkg, privateAlias, false);
 
        // enable selected
        if ("premium".equalsIgnoreCase(icon)) {
            setState(pm, pkg, premiumAlias, true);
        } else if ("private".equalsIgnoreCase(icon)) {
            setState(pm, pkg, privateAlias, true);
        } else {
            setState(pm, pkg, normalAlias, true);
        }
 
        Toast.makeText(cordova.getContext(),
                "Icon changed!",
                Toast.LENGTH_LONG).show();
 
        callback.success("OK");
    }
 
    private void setState(PackageManager pm, String pkg, String alias, boolean enabled) {
 
        Toast.makeText(
                cordova.getContext(),
                "Setting " + alias + " → " + (enabled ? "ENABLED" : "DISABLED"),
                Toast.LENGTH_SHORT
        ).show();
 
        ComponentName cn = new ComponentName(pkg, pkg + alias);
 
        pm.setComponentEnabledSetting(
                cn,
                enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
    }
}
 
