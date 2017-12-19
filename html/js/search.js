/**
 * Created by teeyoung on 17/12/5.
 */

function searchBlogList(keyword) {
    $.ajax({
        type: 'post',
        url: "blog/search",
        cache: false,
        data: {
            keyword: keyword
        },
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.createTime);

                if (item.description == null || item.description == undefined) {
                    item.description = "暂无简介";
                }

                $("#blogList").append("<div class=\"panel panel-default\">\n" +
                    "                <div class=\"panel-body\">\n" +
                    "                    <p class=\"text-primary\"><h4><a href='" +
                    "/detail.html?id=" + item.id +
                    "'>" +
                    item.title +
                    "</a></h4></p>\n" +
                    "\n" +
                    "                    <p class=\"text-muted\">" +
                    item.description +
                    "</p>\n" +
                    "\n" +
                    "                    <p class=\"text-info\">" +
                    date.toLocaleDateString() +
                    "</p>\n" +
                    "                </div>\n" +
                    "            </div>");
            });
        },
        error: function () {
            return;
        }
    });
}

function search() {
    var key = $("#key").val();
    if (key == undefined || key == null) {
        alert("请输入关键词");
        return;
    }

    searchBlogList(key);
    return false;
}

$(document).ready(function () {

});
