/*** dynamically load jQuery ***/
var JQUERY_URL = 'http://code.jquery.com/jquery-latest.min.js';
if (typeof jQuery == 'undefined') {
    var script=document.createElement('script');
    script.src = JQUERY_URL;
    var head = document.getElementsByTagName('head')[0];
    var done = false;
    script.onload = script.onreadystatechange = (function() {
        if (!done && (!this.readyState
                || this.readyState == 'loaded'
                || this.readyState == 'complete')) {
            done = true;
            script.onload = script.onreadystatechange = null;
            head.removeChild(script);
        }
    });
    head.appendChild(script);
}
