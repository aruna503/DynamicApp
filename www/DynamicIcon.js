var exec = require('cordova/exec');
 
var DynamicIcon = {
    changeIcon: function (iconName, success, error) {
        exec(
            function (msg) {
                if (success) success(msg);
            },
            function (err) {
                if (error) error(err);
            },
            "DynamicIcon",
            "changeIcon",
            [iconName]
        );
    }
};
 
module.exports = DynamicIcon;
