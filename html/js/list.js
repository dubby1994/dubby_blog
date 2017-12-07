/**
 * Created by teeyoung on 17/12/7.
 */
function freshTagList() {
    $.ajax({
        type: 'get',
        url: "tag/all",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                $("#tagList").append("<div class=\"col-md-2 col-sm-2 col-xs-2 col-lg-2\"> <div class=\"checkbox\"> <label> <input name=\"tagId\" type=\"checkbox\" value=\"" +
                    item.id +
                    "\">" +
                    item.name +
                    "</label> </div> </div>"
                )
                ;
            });
        },
        error: function () {
            return;
        }
    });
}

function search() {
    var params = "";
    $("input[name='tagId']:checkbox:checked").each(function () {
        console.log($(this).val());
        params += "&tagId=";
        params += $(this).val();
    });

    params = params.substr(1, params.length);
    getBlogByTagId(params);
}

function getBlogByTagId(params) {
    $.ajax({
        type: 'get',
        url: "blog/list/tag?" + params,
        cache: false,
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
        },
        error: function () {
            return;
        }
    });
}

$(document).ready(function () {
    var tagId = getUrlParam("tagId");
    if (tagId != null && tagId != undefined) {
        tagId = parseInt(tagId);
    } else {
        tagId = 1;
    }

    getBlogByTagId("tagId=" + tagId);

    freshTagList();

});