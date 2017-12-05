//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function getUrl() {
    return window.location.href;
}

function getCookie(name) {
    var cookieList = document.cookie.split(";");

    for (var i = 0; i < cookieList.length; ++i) {
        var item = cookieList[i];
        var cookiePair = item.split("=");

        if (name.trim() == cookiePair[0].trim()) {
            return cookiePair[1];
        }
    }
}