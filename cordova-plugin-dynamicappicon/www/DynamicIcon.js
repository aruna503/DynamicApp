var DynamicIcon = {
    changeIcon: function(iconName, success, error) {
 
        if (!cordova || !cordova.exec) {
            error && error("Cordova not ready");
            return;
        }
 
        cordova.exec(
            function (msg) {
                success && success(msg);
            },
            function (err) {
                error && error(err);
            },
            "DynamicIcon",
            "changeIcon",
            [iconName]
        );
    }
};
 
module.exports = DynamicIcon;
