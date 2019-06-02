/**
 * Created by teeyoung on 17/12/12.
 */
function freshDetail(blogId) {
    $.ajax({
        type: 'get',
        url: "draft/" + blogId,
        cache: false,
        dataType: 'json',
        success: function (data) {
            var date = new Date();
            date.setTime(data.createTime);
            $("#page_title").text(data.title);
            $("#title").val(data.title);
            $("#description").val(data.description);
            $("#pageTitle").text(data.title);

            $("[name='editormd-markdown-doc']").text(data.content);
        },
        error: function () {
            return;
        }
    });
}

function save() {
    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);
    } else {
        id = null
    }

    var title = $("#title").val();
    var content = $("[name='editormd-markdown-doc']").val();
    var description = $("#description").val();

    if (title == undefined || title == null) {
        alert("标题不能为空");
        return;
    }

    if (description == undefined || description == null || description == "") {
        alert("描述不能为空");
        return;
    }

    if (content == undefined || content == null || content == "[TOC]") {
        alert("内容不能为空");
        return;
    }
    $.ajax({
        type: 'post',
        url: "draft/save",
        data: {
            id: id,
            title: title,
            description: description,
            content: content
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.code == 0) {
                alert("保存成功");
            } else {
                alert(data.message);
            }
        },
        error: function () {
            return;
        }
    });
}

function post() {
    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);

        $.ajax({
            type: 'get',
            url: "draft/post?id=" + id,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    alert("发布成功");
                    window.location = "/detail.html?id=" + data.data.blogId;
                } else {
                    alert(data.message);
                }
            },
            error: function () {
                return;
            }
        });
    }
}

function getPageMessage(blogId) {
    $.ajax({
        type: 'get',
        url: "draft/detail/page",
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
    editormd("editormd", {
        path: "editormd/lib/",// Autoload modules mode, codemirror, marked... dependents libs path
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp", "svg"],
        imageUploadURL: "file/upload",
        sequenceDiagram : true
    });

    var id = getUrlParam("id");
    if (id != null && id != undefined) {
        id = parseInt(id);
        freshDetail(id);
    }
    //getPageMessage(id);
});