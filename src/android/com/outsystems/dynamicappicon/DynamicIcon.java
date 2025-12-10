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

        // alias names from AndroidManifest
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
            // default = normal
            setState(pm, pkg, normalAlias, true);
        }

        callback.success("Icon changed");
    }

    private void setState(PackageManager pm, String pkg, String alias, boolean enabled) {
        ComponentName cn = new ComponentName(pkg, pkg + alias);
        pm.setComponentEnabledSetting(
            cn,
            enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        );
    }
}
