var exec = require('cordova/exec');

var DynamicIcon = {
    changeIcon: function(iconName, successCallback, errorCallback) {
        exec(
            successCallback,
            errorCallback,
            "DynamicIcon",      // must match the <feature name="DynamicIcon">
            "changeIcon",       // must match the action in Java
            [iconName]
        );
    }
};

module.exports = DynamicIcon;
