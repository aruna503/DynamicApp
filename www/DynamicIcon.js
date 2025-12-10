var exec = require('cordova/exec');
 
var DynamicIcon = {
    changeIcon: function(iconName, success, error) {
 
        exec(
            function(result) {
                if (success) success(result);   // send success message back
            },
            function(err) {
                if (error) error(err);          // send error message back
            },
            "DynamicIcon",    // must match <feature name="DynamicIcon">
            "changeIcon",     // must match Java action
            [iconName]
        );
    }
};
 
module.exports = DynamicIcon;
