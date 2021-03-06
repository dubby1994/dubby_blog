/**
 * Created by teeyoung on 17/12/12.
 */
var LIMIT_NUMBER = 10;

/**
 * 查询最新10篇文章
 */
function freshBlogList(offset) {
    $.ajax({
        type: 'get',
        url: "draft/list",
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

                $("#blogList").append("<div class=\"panel panel-default\">\n" +
                    "<div class=\"panel-body\">\n" +
                    "<p class=\"text-primary\"><h4><a href='" +
                    "/write.html?id=" + item.id +
                    "'>" +
                    item.title +
                    "</a></h4></p>\n" +
                    "\n" +
                    "<p class=\"text-muted\">" +
                    item.description+
                    "</p>\n" +
                    "\n" +
                    "<p class=\"text-info\">" +
                    date.toLocaleDateString() +
                    "</p>\n" +
                    "</div>\n" +
                    "</div>");
            });
        },
        error: function () {
            return;
        }
    });
}

function getPageMessage(offset) {
    $.ajax({
        type: 'get',
        url: "draft/page",
        cache: false,
        data: {
            offset: offset,
            limit: LIMIT_NUMBER
        },
        dataType: 'json',
        success: function (data) {
            if (data.hasPre) {
                $("#preBtn").removeClass("disabled");
                $("#preLink").attr('href', '/dubby.html?offset=' + (offset - LIMIT_NUMBER));
            } else {
                $("#preBtn").addClass("disabled");
            }


            if (data.hasNext) {
                $("#nextBtn").removeClass("disabled");
                $("#nextLink").attr('href', '/dubby.html?offset=' + (offset + LIMIT_NUMBER));
            } else {
                $("#nextBtn").addClass("disabled");
            }

        },
        error: function () {
            return;
        }
    });
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

});