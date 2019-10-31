// $.showPreloader();
var productId = getQueryString('productId');
//通过id去获取商品的信息的url
var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;

//获取当前店铺的商品类别信息url
var categoryUrl = '/o2o/shopadmin/getproductcategorylist'

var productPostUrl = '/o2o/shopadmin/updateproduct';
var isEdit = false;
var info = '添加';
if (productId) {
    getInfo(productId);
    info = '更新';
    isEdit = true;
} else {
    getCategory();
    productPostUrl = '/o2o/shopadmin/addproduct';
}

function getInfo(id) {
    $.getJSON(infoUrl, function (data) {
        if (data.success) {
            var product = data.product;
            $('#product-name').val(product.productName);
            $('#product-desc').val(product.productDesc);
            $('#priority').val(product.priority);
            $('#normal-price').val(product.normalPrice);
            $('#promotion-price').val(product.promotionPrice);
            $('#point').val(product.point);
            var optionHtml = '';
            var optionSelected = product.productCategory.productCategoryId;
            data.productCategoryList.map(
                function (item, index) {
                    var isSelect = optionSelected === item.productCategoryId ? 'selected'
                        : '';
                    optionHtml += '<option data-value="'
                        + item.productCategoryId
                        + '"'
                        + isSelect
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                }
            );
            $('#category').html(optionHtml);
        }
        $.hidePreloader();
    });
}

function getCategory() {
    $.getJSON(categoryUrl, function (data) {
        if (data.success) {
            var optionHtml = '';
            data.data.map(function (item, index) {
                optionHtml += '<option data-value="'
                    + item.productCategoryId + '">'
                    + item.productCategoryName + '</option>';
            });
            $('#category').html(optionHtml);
        }
        $.hidePreloader();
    });
}

$('.detail-img-div').on('change', '.detail-img:last-child', function () {
    if ($('.detail-img').length < 6) {
        $('#detail-img').append('<input type="file" class="detail-img">');
        if ($('.detail-img').length == 6) {
            $.toast("详情图片最多上传6张");
        }
    }
});

$('#submit').click(function () {
    var product = {};
    product.productName = $('#product-name').val();
    product.productDesc = $('#product-desc').val();
    product.priority = $('#priority').val();
    product.normalPrice = $('#normal-price').val();
    product.promotionPrice = $('#promotion-price').val();
    product.point = $('#point').val();
    product.productCategory = {
        productCategoryId: $('#category').find('option').not(
            function () {
                return !this.selected;
            }).data('value')
    };
    product.productId = productId;
    var thumbnail = $('#small-img')[0].files[0];

    var verifyCodeActual = $('#j_captcha').val();

    if (product.productName == '') {
        $.toast('请输入商品名称');
        $('#product-name').focus();
        return;
    }

    if (product.priority == '') {
        $.toast('请输入商品展示优先级');
        $('#priority').focus();
        return;
    }

    if (!isEdit && thumbnail == null) {
        $.toast('请选择一张商品缩略图');
        return;
    }

    if (!isEdit && $('.detail-img')[0].files[0] == null) {
        $.toast('请至少选择一张商品详情图');
        return;
    }

    if (!verifyCodeActual) {
        $.toast('请输入验证码');
        $('#j_captcha').focus();
        return;
    }

    console.log(thumbnail);
    var formData = new FormData();
    formData.append('thumbnail', thumbnail);
    $('.detail-img').map(
        function (index, item) {
            if ($('.detail-img')[index].files.length > 0) {
                formData.append('productImg' + index,
                    $('.detail-img')[index].files[0]);
            }
        });

    formData.append('productStr', JSON.stringify(product));
    formData.append("verifyCodeActual", verifyCodeActual);
    $.showPreloader("正在提交数据");
    $.ajax({
        url: productPostUrl,
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        cache: false,
        success: function (data) {
            $.hidePreloader();
            if (data.success) {
                $.toast('商品信息' + info + '成功!');
                $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
            } else {
                $.toast('商品信息' + info + '失败!');
                $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
            }
        }
    });
});