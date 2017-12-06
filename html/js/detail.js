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
                $("#preLink").append("<span aria-hidden=\"true\">&larr;</span> " + data.preTitle);
            } else {
                $("#preBtn").addClass("disabled");
                $("#preLink").append("没了");
            }


            if (data.hasNext) {
                $("#nextBtn").removeClass("disabled");
                $("#nextLink").attr('href', '/detail.html?id=' + data.next);
                $("#nextLink").append(data.nextTitle + " <span aria-hidden=\"true\">&rarr;</span>");
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

$(document).ready(function () {
    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);
    } else {
        window.location = "/index.html"
    }

    freshDetail(id);
    getPageMessage(id);
});