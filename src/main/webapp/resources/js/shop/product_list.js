$(function () {
    var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=9999';

    var deleteUrl = '/o2o/shopadmin/updateproduct';

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                productList.map(function (item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-33">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-25">'
                        + item.point
                        + '</div>'
                        + '<div class="col-45">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    getList();

    function deleteItem(id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        var info = enableStatus == 1 ? '上架' : '下架';
        $.confirm('确定' + info + '该商品吗?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    //标识为上下架操作，不需要效验验证码
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('商品已' + info + '成功！');
                        getList();
                    } else {
                        $.toast('本次操作失败！');
                    }
                }
            });
        });
    }

    $('.product-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/o2o/shopadmin/productoperation?productId='
                + e.currentTarget.dataset.id;
        } else if (target.hasClass('delete')) {
            deleteItem(e.currentTarget.dataset.id,
                e.currentTarget.dataset.status);
        } else if (target.hasClass('preview')) {
            window.location.href = '/o2o/frontend/productdetail?productId='
                + e.currentTarget.dataset.id;
        }
    });

    $('#new').click(function () {
        window.location.href = '/o2o/shopadmin/productoperation';
    });
});