$(function () {
    var bindUrl = '/o2o/local/bindlocalauth';
    var usertype = getQueryString('usertype');
    $('#submit').click(function () {
        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        //空值效验
        if (userName == "") {
            $.toast("用户名不能为空");
            $('#username').focus();
            return;
        } else {
            //正则验证用户名：长度4-16位，（字母，数字）
            var uPattern = /^[a-zA-Z0-9]{4,16}$/;
            if (!uPattern.test(userName)) {
                $.alert("用户名长度4-16位，可以由字母，数字组合", "提示");
                return;
            }
        }
        if (password == "") {

            $.toast("密码不能为空");
            $('#psw').focus();
            return;
        } else {
            var uPattern = /^(?!\d+$)[\da-zA-Z]{8,16}$/;
            if (!uPattern.test(password)) {
                $.alert("密码长度8-16位，可以由字母，数字组合(不能为纯数字)", "提示");
                return;
            }
        }
        if (!verifyCodeActual) {
            $.toast("请输入验证码");
            $('#j_captcha').focus();
            return;
        }

        var flag;
        $.ajax({
            url: bindUrl,
            async: false,
            cache: false,
            type: "post",
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                verifyCodeActual: verifyCodeActual
            },
            success: function (data) {
                if (data.success) {
                    $.toast("账号绑定成功", 1000);
                    flag = true;
                    // if (usertype == 1) {
                    //     window.location.href = "/o2o/frontend/index";
                    // } else {
                    //     window.location.href = "/o2o/shopadmin/shoplist";
                    // }
                } else {
                    $.toast(data.errMsg);
                    $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
                }
            }
        });
        if (flag) {
            setTimeout(function () {
                history.back();
            }, 1200)
        }
        return false;
    });

    $(document).on("click", "#help", function () {
        $.openPanel("#panel");
    });
});