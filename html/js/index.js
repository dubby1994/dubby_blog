/**
 * Created by teeyoung on 17/12/5.
 */
var LIMIT_NUMBER = 10;
var TAG_NUMBER = 20;

/**
 * 查询最新10篇文章
 */
function freshBlogList(offset) {
    $.ajax({
        type: 'get',
        url: "blog/list",
        cache: false,
        data: {
            offset: offset,
            limit: LIMIT_NUMBER
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

function freshTagList() {
    $.ajax({
        type: 'get',
        url: "tag/top",
        cache: false,
        data: {
            limit: TAG_NUMBER
        },
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                $("#tagList").append("<li class=\"list-group-item\"> <span class=\"badge\">" +
                    item.count +
                    "</span><a href=\"/list.html?tagId=" +
                    item.tagId +
                    "\"> " +
                    item.tagName +
                    "</a></li>"
                )
                ;
            });
        },
        error: function () {
            return;
        }
    });
}


function freshPV() {
    $.ajax({
        type: 'get',
        url: "hit/count",
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("#pv").append(data);
        },
        error: function () {
            return;
        }
    });
}

function getPageMessage(offset) {
    $.ajax({
        type: 'get',
        url: "blog/page",
        cache: false,
        data: {
            offset: offset,
            limit: LIMIT_NUMBER
        },
        dataType: 'json',
        success: function (data) {
            if (data.hasPre) {
                $("#preBtn").removeClass("disabled");
                $("#preLink").attr('href', '/index.html?offset=' + (offset - LIMIT_NUMBER));
            } else {
                $("#preBtn").addClass("disabled");
            }


            if (data.hasNext) {
                $("#nextBtn").removeClass("disabled");
                $("#nextLink").attr('href', '/index.html?offset=' + (offset + LIMIT_NUMBER));
            } else {
                $("#nextBtn").addClass("disabled");
            }

        },
        error: function () {
            return;
        }
    });
}

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
            $("#blogList").html("");
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
            $("#preBtn").addClass("disabled");
            $("#nextBtn").addClass("disabled");
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
    var offset = getUrlParam("offset");
    if (offset != null && offset != undefined) {
        offset = parseInt(offset);
    } else {
        offset = 0;
    }

    freshBlogList(offset);
    getPageMessage(offset);
    // freshTagList();
    freshPV();

});
