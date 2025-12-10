var exec = require('cordova/exec');
 
var DynamicIcon = {
    changeIcon: function(iconName, success, error) {
        exec(success, error, "DynamicIcon", "changeIcon", [iconName]);
    }
};
 
module.exports = DynamicIcon;
 
