$('#log-out').click(function () {
    $.ajax({
        url: "/o2o/local/logout",
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                var usertype = $('#log-out').attr('usertype');
                //返回到登陆页面
                window.location.href = '/o2o/local/login?usertype=' + usertype;
            }else{
                $.alert("error");
            }
        },
        error: function (data, error) {
            $.alert(error);
        }
    });
    return false;
});
