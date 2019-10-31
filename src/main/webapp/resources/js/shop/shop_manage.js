$(function () {
    var shopId = getQueryString("shopId");
    console.log(shopId);
    var shopInfoUrl = '/o2o/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            var local = function () {
                window.location.href = data.url;
            }
            $(document).dialog({
                type: 'notice',
                infoText: '您没有权限访问该页面',
                autoClose: 1200,
                position: 'center', // center: 居中; bottom: 底部
                onClosed: function () {
                    local();
                }
            });

        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $("#shopInfo").attr("href", "/o2o/shopadmin/shopoperation?shopId=" + shopId);
        }
    });
});