$(function () {
    var shopId = 1;
    var listUrl = '/o2o/shopadmin/getproductcategorylist';
    var addUrl = '/o2o/shopadmin/addproductcategorys';
    var deleteUrl = '/o2o/shopadmin/removeproductcateory';
    getList();

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var tempHtml = '';
                dataList
                    .map(function (item, index) {
                        tempHtml += ''
                            + '<div class="row row-product-category now">'
                            + '<div class="col-40 product-category-name">'
                            + item.productCategoryName
                            + '</div>'
                            + '<div class="col-40">'
                            + item.priority
                            + '</div>'
                            + '<div class="col-20"><a href="#" class="button delete" data-id="'
                            + item.productCategoryId
                            + '">删除</a></div>'
                            + '</div>';
                    });
                $('.category-wrap').append(tempHtml);
            }
        });
    }

    $('#new').click(function () {
        var tempHtml = '<div class="row row-product-category temp">'
            + '<div class="col-40"><input class="category-input category" type="text" placeholder="商品分类名"></div>'
            + '<div class="col-40"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-20"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);
    });

    $('#submit').click(function () {
        var tempArr = $('.temp');
        var productCategoryList = [];
        var tempObj = {};
        tempArr.map(function (index, item) {
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        //数据效验
        if (tempObj.productCategoryName == '' || tempObj.priority == '') {
            $.toast('输入不能为空！');
            return;
        }
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(productCategoryList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                } else {
                    $.toast('提交失败,请重新提交！');
                }
            }
        });
    });
    $('.category-wrap').on('click', '.row-product-category.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('是否删除该商品分类？', function () {
                $.ajax({
                    url: deleteUrl,
                    type: 'POST',
                    data: {
                        productCategoryId: target.dataset.id,
                        shopId: shopId
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.toast('删除成功！');
                            getList();
                        } else {
                            $.toast('删除失败！'+data.errMsg);
                        }
                    }
                });
            });
        });

    $('.category-wrap').on('click', '.row-product-category.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();
        });
});