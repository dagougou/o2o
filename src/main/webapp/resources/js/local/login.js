$(function () {
    //登陆验证的controller URL
    var loginUrl = '/o2o/local/logincheck';
    //从地址栏的URL中获取usertype
    var usertype = getQueryString('usertype');
    //记录进行登陆的次数，超过三次要求输入验证码登陆
    var loginCount = 0;

    $("#login").click(function () {
        //获取输入的账号
        var userName = $('#username').val();
        //获取密码
        var password = $('#psw').val();
        //获取验证码信息
        var verifyCodeActual = $('#j_captcha').val();
        //是否需要验证的标识
        var needVerify = false;
        //数据判空
        if (userName == '') {
            $.toast("请输入用户名");
            $('#username').focus();
            return;
        }
        if (password == '') {
            $.toast("请输入密码");
            $('#pws').focus();
            return;
        }
        //如果三次登陆失败，需要输入验证码
        if (loginCount >= 3) {
            //验证校验码
            if (!verifyCodeActual) {
                $.toast("请输入验证码");
                $('#j_captcha').focus();
                return;
            } else {
                //将验证标识符设置为true，表示后台需要进行验证码效验
                needVerify = true;
            }
        }
        //访问后台进行登陆
        $.ajax({
            url: loginUrl,
            async: false,
            cache: false,
            type: 'post',
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                //是否需要做验证码校验
                needVerify: needVerify,
                verifyCodeActual: verifyCodeActual
            },
            success: function (data) {
                if (data.success) {
                    $.toast('登陆成功!');
                    if (usertype == 1) {
                        //返回前端主页面
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        //返回店铺列表页面
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast("登陆失败:" + data.errMsg);
                    loginCount++;
                    //超过三次显示验证码输入框
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                    $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
                }
            }
        });
        return false;
    });
});