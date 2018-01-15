/**
 * Created by teeyoung on 17/12/6.
 */
function freshDetail(blogId) {
    $.ajax({
        type: 'get',
        url: "blog/" + blogId,
        cache: false,
        dataType: 'json',
        success: function (data) {
            var date = new Date();
            date.setTime(data.createTime);
            $("#title").text(data.title);
            $("#pageTitle").text(data.title);

            $("#createTime").text("发表时间：" + date.toLocaleDateString());

            $("[name='editormd-markdown-doc']").text(data.content);

            var editormdView = editormd.markdownToHTML("editormd-view", {
                markdown: data.content,//+ "\r\n" + $("#append-test").text(),
                //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                //toc             : false,
                tocm: true,    // Using [TOCM]
                //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
                //gfm             : false,
                //tocDropdown     : true,
                // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true  // 默认不解析
            });
        },
        error: function () {
            return;
        }
    });
}

function getPageMessage(blogId) {
    $.ajax({
        type: 'get',
        url: "blog/detail/page",
        cache: false,
        data: {
            id: blogId
        },
        dataType: 'json',
        success: function (data) {
            if (data.hasPre) {
                $("#preBtn").removeClass("disabled");
                $("#preLink").attr('href', '/detail.html?id=' + data.pre);
                $("#preLink").append(data.preTitle + "<span aria-hidden=\"true\">&rarr;</span> ");
            } else {
                $("#preBtn").addClass("disabled");
                $("#preLink").append("没了");
            }


            if (data.hasNext) {
                $("#nextBtn").removeClass("disabled");
                $("#nextLink").attr('href', '/detail.html?id=' + data.next);
                $("#nextLink").append(" <span aria-hidden=\"true\">&larr;</span>" + data.nextTitle);
            } else {
                $("#nextBtn").addClass("disabled");
                $("#nextLink").append("没了");
            }

        },
        error: function () {
            return;
        }
    });
}

function freshCommentList(id) {
    $.ajax({
        type: 'get',
        url: "comment/list",
        cache: false,
        data: {
            blogId: id
        },
        dataType: 'json',
        success: function (data) {
            $("#commentList").html("");
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.createTime);

                $("#commentList").append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <p class=\"text-primary\">" +
                    date.toLocaleDateString() +
                    "</p> <p class=\"text-muted\">" +
                    item.content +
                    "</p> </div> </div>");
            });


        },
        error: function () {
            return;
        }
    });
}

function commit() {
    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);

        var content = $("#comment").val();

        if (content == undefined || content == null || content == "") {
            alert("内容不能为空");
            return;
        }

        $.ajax({
            type: 'post',
            url: "comment/save",
            cache: false,
            dataType: 'json',
            data: {
                blogId: id,
                content: content
            },
            success: function (data) {
                if (data.code == 0) {
                    // alert("评论成功");
                    freshCommentList(id);

                    $("#comment").val("");
                } else {
                    alert(data.message);
                }
            },
            error: function () {
                return;
            }
        });
    }

    return false;
}

$(document).ready(function () {
    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);
    } else {
        window.location = "/index.html"
    }

    freshDetail(id);
    getPageMessage(id);
    freshCommentList(id);
});