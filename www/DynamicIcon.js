var exec = require('cordova/exec');

exports.changeIcon = function (iconName, success, error) {
    exec(success, error, "DynamicIcon", "changeIcon", [iconName]);
};
