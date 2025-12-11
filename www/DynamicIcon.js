var exec = require('cordova/exec');
 
var DynamicIcon = {
  changeIcon: function(iconName, success, error) {
    exec(
      function(result) {
        if (success) success(result);
      },
      function(err) {
        if (error) error(err);
      },
      "DynamicIcon",   // must match feature name in plugin.xml
      "changeIcon",    // must match action handled by Java plugin
      [iconName]
    );
  }
};
 
module.exports = DynamicIcon;
