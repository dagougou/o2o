$(function () {
    $.showPreloader('页面加载中');
    var url = '/o2o/frontend/listmainpageinfo';
    $.getJSON(url, function (data) {
        if (data.success) {
            var headLineList = data.headLineList;
            headLineList.map(function (item, index) {
                var swiperHtml = $('<div></div>').addClass("swiper-slide").addClass("img-wrap")
                    .append($('<img/>').addClass("banner-img").attr("src", item.lineImg).attr("alt", item.lineName));
                $('.swiper-wrapper').append(swiperHtml);
            });
            $(".swiper-container").swiper({
                autoplay: 3000,
                autoplayDisableOnInteraction: false
            });
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item, index) {
                var categoryHtml = $('<div></div>').addClass("col-50").addClass("shop-classify").attr("data-category", item.shopCategoryId)
                    .append($('<div></div>').addClass("word")
                        .append($('<p></p>').addClass("shop-title").text(item.shopCategoryName))
                        .append($('<p></p>').addClass("shop-desc").text(item.shopCategoryDesc)))
                    .append($('<div></div>').addClass("shop-classify-img-warp")
                        .append($('<img/>').addClass("shop-img").attr("src", item.shopCategoryImg)));
                $('.row').append(categoryHtml);
            });
        }
        $.hidePreloader();
    });

    $('#me').click(function () {
        $.openPanel('#panel');
    });

    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });
    $.init();
});
