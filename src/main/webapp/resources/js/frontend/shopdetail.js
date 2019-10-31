$(function () {
    var loading = false;
    var maxItems = 20;
    var pageSize = 3;

    var listUrl = '/o2o/frontend/listproductsbyshop';

    var pageNum = 1;
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';

    var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;

    function getSearchDivData() {
        var url = searchDivUrl;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-cover-pic').attr('src', shop.shopImg);
                $('#shop-update-time').text(new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
                $('#shop-name').text(shop.shopName);
                $('#shop-desc').text(shop.shopDesc);
                $('#shop-addr').text(shop.shopAddr);
                $('#shop-phone').text(shop.phone);
                var productCategoryList = data.productCategoryList;
                productCategoryList.map(function (item, index) {
                    var a = $('<a></a>').addClass("button").attr("href", "#").attr("data-product-search-id", item.productCategoryId).text(item.productCategoryName);
                    $('#shopdetail-button-div').append(a);
                });
            }
        });
    }

    //渲染页面
    getSearchDivData();

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                data.productList.map(function (item, index) {
                    var card = $('<div></div>')
                        .addClass("card").attr("data-product-id", item.productId)
                        .append($("<div></div>").addClass("card-header").text(item.productName))
                        .append($('<div></div>').addClass("card-content")
                            .append($('<div></div>').addClass("list-block").addClass("media-list")
                                .append($("<ul></ul>")
                                    .append($("<li></li>").addClass("item-content")
                                        .append($('<div></div>').addClass("item-media")
                                            .append($('<img/>').attr('src', item.imgAddr).attr("width", 44)))
                                        .append($('<div></div>').addClass("item-inner")
                                            .append($('<div></div>').addClass("item-subtitle").text(item.productDesc)))))))
                        .append($('<div></div>').addClass("card-footer")
                            .append($('<p></p>').addClass("color-gray").text(new Date(item.lastEditTime).Format("yyyy-MM-dd") + "更新"))
                            .append($('<span></span>').text("点击查看")));
                    $('.list-div').append(card);
                });
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }

    //预加载10条数据
    addItems(pageSize, pageNum);

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('#shopdetail-button-div').on('click', '.button', function (e) {
        productCategoryId = e.target.dataset.productSearchId;
        if (productCategoryId) {
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                productCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
        }
    });

    //点击进入商品详情信息
    $('.list-div').on('click', '.card', function (e) {
        var productId = e.currentTarget.dataset.productId;
        window.location.href = '/o2o/frontend/productdetail?productId=' + productId;
    });

    $('#search').on('input', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel');
    });
    $.init();
});
