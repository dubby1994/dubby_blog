/**
 * Created by teeyoung on 17/12/5.
 */
/**
 * 查询最新10篇文章
 */
function freshTop10Blog() {
    $.ajax({
        type: 'get',
        url: "blog/list",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.createTime);

                $("#blogList").append("");
            });
        },
        error: function () {
            return;
        }
    });
}
