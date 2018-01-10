/**
 * Created by teeyoung on 2018/1/10.
 */
function convertContentToQRCode() {
    var content = $("#content").val();

    if (content == undefined || content == null || content == "[TOC]") {
        alert("内容不能为空");
        return;
    }

    $.ajax({
        type: 'post',
        url: "file/qrcode",
        data: {
            content: content
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("#qrcode").attr('src',data.url);
        },
        error: function () {
            return;
        }
    });

    return false;
}