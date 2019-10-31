$(function () {
    var loading = false;
    //分页的最大值，默认值999，后面会根据后台返回的条数改变
    var maxItems = 20;
    //一页返回的最大条数
    var pageSize = 3;
    //获取店铺列表的url
    var listUrl = '/o2o/frontend/listshops';
    //获取店铺类别列表和区域信息的Url
    var searchDivUrl = '/o2o/frontend/listshopspageinfo';
    //页码
    var pageNum = 1;
    //尝试从URL中获取parentId
    var parentId = getQueryString('parentId');
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';
    //渲染出店铺类别列表和区域列表以供搜索
    getSearchDivData();
    //预先加载10条数据
    addItems(pageSize, pageNum);

    function getSearchDivData() {
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $.getJSON(url, function (data) {
            if (data.success) {
                var shopCategoryList = data.shopCategoryList;
                var html = $("<a></a>").addClass("button").attr("href", "#").attr("data-category-id", "").text("全部类别");
                $('#shoplist-search-div').append(html);
                shopCategoryList.map(function (item, index) {
                    var a = $("<a></a>").addClass("button").attr("href", "#").attr("data-category-id", item.shopCategoryId).text(item.shopCategoryName);
                    $('#shoplist-search-div').append(a);
                });
                var selectOption = $("<option></option>").attr("value", "").text("全部街道");
                $('#area-search').append(selectOption);
                var areaList = data.areaList;
                areaList.map(function (item, index) {
                    var selectOptions = $("<option></option>").attr("value", item.areaId).text(item.areaName);
                    $('#area-search').append(selectOptions);
                });
            }
        });
    }


    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                data.shopList.map(function (item, index) {
                    var card = $('<div></div>')
                        .addClass("card").attr("data-shop-id", item.shopId)
                        .append($("<div></div>").addClass("card-header").text(item.shopName))
                        .append($('<div></div>').addClass("card-content")
                            .append($('<div></div>').addClass("list-block").addClass("media-list")
                                .append($("<ul></ul>")
                                    .append($("<li></li>").addClass("item-content")
                                        .append($('<div></div>').addClass("item-media")
                                            .append($('<img/>').attr('src', item.shopImg).attr("width", 44)))
                                        .append($('<div></div>').addClass("item-inner")
                                            .append($('<div></div>').addClass("item-subtitle").text(item.shopDesc)))))))
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

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('.shop-list').on('click', '.card', function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
    });

    $('#shoplist-search-div').on('click', '.button', function (e) {
        if (parentId) {// 如果传递过来的是一个父类下的子类
            shopCategoryId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                shopCategoryId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
        } else {// 如果传递过来的父类为空，则按照父类查询
            parentId = e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')) {
                $(e.target).removeClass('button-fill');
                parentId = '';
            } else {
                $(e.target).addClass('button-fill').siblings()
                    .removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum = 1;
            addItems(pageSize, pageNum);
            parentId = '';
        }

    });

    $('#search').on('input', function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#area-search').on('change', function () {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel');
    });

    $.init();
});
