$.showPreloader('页面加载中');
getlist();

function getlist(e) {
    $.ajax({
        url: "/o2o/shopadmin/getshops",
        type: "get",
        dataType: "json",
        success: function (data) {
            if (data.success) {
                handleList(data.shopList);
                handleUser(data.user);
            }
            $.hidePreloader();
        }
    });
}

function handleUser(user) {
    $("#user-name").text(user.name);
}

function handleList(data) {
    var html = '';
    data.map(function (item, index) {
        html += '<div class="row row-shop"><div class="col-40" style="text-overflow:ellipsis;overflow: hidden">'
            + item.shopName + '</div><div class="col-40" style="align-content: center">'
            + shopStatus(item.enableStatus) + '</div><div class="col-20 pull-center">'
            + goShop(item.enableStatus, item.shopId) + '</div></div>';
    });
    $('.shop-wrap').html(html);
}

function goShop(status, id) {
    if (status != 0 && status != -1) {
        return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
    } else {
        return '—';
    }
}

function shopStatus(status) {
    if (status == 0) {
        return '<span style="color: #0894ec">审核中</span>';
    } else if (status == -1) {
        return '<span style="color: #f6383a">店铺非法</span>';
    } else {
        return '<span style="color: #4cd964">审核通过</span>';
    }
}


