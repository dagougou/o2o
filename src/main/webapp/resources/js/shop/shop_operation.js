//发送ajax请求获取店铺分类和区域信息
//获取店铺Id
var shopId = getQueryString('shopId');
//判断店铺id是否存在，存在则是修改店铺的操作，不存在则是注册店铺的操作
var isEdit = shopId ? true : false;

var initUrl = '/o2o/shopadmin/getshopinitinfo';
var regiserShopUrl = '/o2o/shopadmin/registershop';
var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
var editShopUrl = '/o2o/shopadmin/updateshop';

if (isEdit) {
    getInfo();
} else {
    getShopInitInfo();
}
setBtnText();

function setBtnText() {
    $("#shop-submit").text((isEdit ? "更新" : "注册"));
    $("title").text('店铺' + (isEdit ? "更新" : "注册"));
}

function getInfo(shopId) {
    $.showPreloader();
    $.getJSON(shopInfoUrl, function (data) {
        if (data.success) {
            var shop = data.shop;
            $('#shop-name').val(shop.shopName);
            $('#shop-addr').val(shop.shopAddr);
            $('#shop-phone').val(shop.phone);
            $('#shop-desc').val(shop.shopDesc);
            var shopCategory = '<option data-id="'
                + shop.shopCategory.shopCategoryId + '" selected>'
                + shop.shopCategory.shopCategoryName + '</option>';
            var tempAreaHtml = '';
            data.areaList.map(function (item, index) {
                tempAreaHtml += '<option data-id="' + item.areaId + '">'
                    + item.areaName + '</option>';
            });
            // $('#shop-name').attr("readonly", "readonly").attr("style", "background:#CCCCCC");
            $('#shop-category').html(shopCategory);
            $('#shop-category').attr('disabled', 'disabled');
            $('#area').html(tempAreaHtml);
            $('#area').attr('data-id', shop.areaId);
        }
        $.hidePreloader();
    });
}

function getShopInitInfo() {
    $.showPreloader();
    $.getJSON(initUrl, function (data) {
        if (data.success) {
            var tempHtml = '';
            var tempAreaHtml = '';
            data.shopCategoryList.map(function (item, index) {
                tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
            });

            data.areaList.map(function (item, index) {
                tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
            });

            $('#shop-category').html(tempHtml);
            $('#area').html(tempAreaHtml);
        }
        $.hidePreloader();
    });
}

//给返回按钮绑定点击事件
$('#regression').click(function () {
    var url = "/o2o/shopadmin/shopmanagement?shopId=" + shopId;
    window.location.href = url;
});


//给注册按钮绑定点击事件
$(document).on('click', '#shop-submit', function () {
    var shop = {};
    shop.shopName = $('#shop-name').val();
    shop.shopAddr = $('#shop-addr').val();
    shop.phone = $('#shop-phone').val().toString();
    shop.shopDesc = $('#shop-desc').val();
    shop.shopCategory = {
        shopCategoryId: $('#shop-category').find('option').not(function () {
            return !this.selected;
        }).data('id')
    };

    shop.area = {
        areaId: $('#area').find('option').not(function () {
            return !this.selected;
        }).data('id')
    };
    if (isEdit) {
        shop.shopId = shopId;
    }
    var shopImg = $('#shop-img')[0].files[0];
    var formData = new FormData();
    var verifyCodeActual = $('#j_captcha').val();
    //进行效验
    if (shop.shopName == '') {
        $.toast('请输入店铺名称');
        return;
    }
    if (shop.shopAddr == '') {
        $.toast('请输入详细地址信息');
        return;
    }
    if (!(/^1[34578]\d{9}$/.test(shop.phone))) {
        $.toast('请输入正确的11位手机号码');
        return;
    }
    if (shopImg == null) {
        $.toast('请上传一张店铺图片');
        return;
    }
    if (!verifyCodeActual) {
        $.toast('请输入验证码');
        return;
    }
    //加载动画
    $.showPreloader('正在' + (isEdit ? '更新' : '注册') + '店铺信息');

    formData.append('shopImg', shopImg);
    formData.append('shopStr', JSON.stringify(shop));
    formData.append('verifyCodeActual', verifyCodeActual);
    $.ajax({
        url: (isEdit ? editShopUrl : regiserShopUrl),
        type: 'POST',
        data: formData,
        cache: false,                      // 不缓存
        processData: false,                // jQuery不要去处理发送的数据
        contentType: false,
        success: function (data) {
            if (data.success) {
                $.hidePreloader();
                $.toast('店铺' + (isEdit ? '更新' : '注册') + '成功!');
                $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
            } else {
                $.hidePreloader();
                $.toast('店铺' + (isEdit ? '更新' : '注册') + '失败!' + data.errMsg);
                $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
            }
        },
        error: function () {
            $.hidePreloader();
            $.toast('服务器连接超时，请重试');
        }
    });
});