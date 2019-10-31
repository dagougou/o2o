$(function () {
    var productId = getQueryString('productId');
    var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId=' + productId;

    $.getJSON(productUrl, function (data) {
        if (data.success) {
            var product = data.product;
            $('#product-img').attr('src', product.imgAddr);
            $('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
            $('#product-name').text(product.productName);
            $('#product-desc').text(product.productDesc);
            if (product.point != undefined) {
                $('#product-point').text('购买可得' + product.point + '个积分');
            }
            //商品价格逻辑
            if (product.normalPrice != undefined && product.promotionPrice != undefined) {
                if (product.normalPrice != 0 && product.promotionPrice == 0) {
                    $('#price').show();
                    $('#normalPrice').attr('style', 'font-size:20px').text('￥' + product.normalPrice);
                } else if (product.normalPrice != 0 && product.promotionPrice != 0) {
                    $('#price').show();
                    $('#normalPrice').append($('<del></del>').text('￥' + product.normalPrice));
                    $('#promotionPrice').text('￥' + product.promotionPrice);
                } else if (product.normalPrice == 0 && product.promotionPrice == 0) {
                    $('#price').show();
                    $('#price').append($('<span></span>').attr('style', 'font-size:20px;color:red').text('商品价格暂未公布')).show();
                }
            }
            var imgListHtml = '';
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="'
                    + item.imgAddr + '"/></div>';
            });
            // // 生成购买商品的二维码供商家扫描
            // imgListHtml += '<div> <img src="/myo2o/frontend/generateqrcode4product?productId='
            // 		+ product.productId + '"/></div>';
            // $('#imgList').html(imgListHtml);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel');
    });
    $.init();
});
