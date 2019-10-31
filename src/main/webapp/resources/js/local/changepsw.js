$(function () {
    //修改密码页面的url
    var changeUrl = '/o2o/local/changelocalpwd';
    //从地址栏获取usertype
    var usertype = getQueryString('usertype');
    //账号
    var userName;
    //密码
    var password;
    //新密码
    var newPassword;
    //确认密码
    var connfirmPassword;
    //验证码
    var verifyCodeActual;
    //效验
    $('#userName').change(function () {
        userName = $('#userName').val();
        if (userName == '') {
            $.toast('登陆账号不能为空');
            $('#userName').focus();
        }
    });

    $('#password').change(function () {
        password = $('#password').val();
        if (password == '') {
            $.toast('原密码不能为空');
            $('#password').focus();
        }
    });

    $('#newPassword').change(function () {
        newPassword = $('#newPassword').val();
        if (newPassword == '') {
            $.toast('新密码不能为空');
            $('#newPassword').focus();
        }
    });

    $('#psw').change(function () {
        connfirmPassword = $('#psw').val();
        if (connfirmPassword == '') {
            $.toast('请输入再次输入新密码');
            $('#psw').focus();
        } else {
            if (connfirmPassword != newPassword) {
                console.log(connfirmPassword);
                console.log(newPassword);
                $.toast('两次输入密码不相等,请检查');
                $('#psw').focus();
            }
        }
    });

    //提交
    $('#submit').click(function () {
        //获取账号
        userName = $('#userName').val();
        //获取密码
        password = $('#password').val();
        //获取新密码
        newPassword = $('#newPassword').val();
        //获取确认密码
        connfirmPassword = $('#psw').val();
        //获取验证码
        verifyCodeActual = $('#j_captcha').val();

        //提交前效验
        if (userName == '') {
            $.toast('登陆账号不能为空');
            $('#userName').focus();
            return;
        }
        if (password == '') {
            $.toast('原密码不能为空');
            $('#password').focus();
            return;
        }
        if (newPassword == '') {
            $.toast('新密码不能为空');
            $('#newPassword').focus();
            return;
        } else {
            var uPattern = /^(?!\d+$)[\da-zA-Z]{8,16}$/;
            if (!uPattern.test(newPassword)) {
                $.alert("密码长度8-16位，可以由字母，数字组合(不能为纯数字)", "提示");
                $('#newPassword').focus();
                return;
            }
        }
        if (connfirmPassword != newPassword) {
            $.toast('两次输入密码不相等,请检查');
            $('#psw').focus();
            return;
        }
        if (!verifyCodeActual) {
            $.toast('请输入验证码');
            $('#verifyPart').focus();
            return;
        }
        //添加表单数据
        // var formData = new FormData();
        // formData.append("userName", userName);
        // formData.append("password", password);
        // formData.append("newPassword", newPassword);
        // formData.append("verifyCodeActual", verifyCodeActual);
        //发起ajax请求
        var flag;
        $.ajax({
            url: changeUrl,
            async: false,
            cache: false,
            type: 'post',
            dataType: 'json',
            data: {
                userName: userName,
                password: password,
                newPassword: newPassword,
                verifyCodeActual: verifyCodeActual
            },
            success: function (data) {
                if (data.success) {
                    $.toast('密码修改成功,即将跳转登陆页面', 1000);
                    flag = true;
                } else {
                    $.toast('密码修改失败:' + data.errMsg);
                    $('#captcha_img').attr("src", "../Kaptcha?" + Math.floor(Math.random() * 100));
                }
            }
        });
        if (flag) {
            setTimeout(function () {
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
                        } else {
                            $.alert("error");
                        }
                    },
                    error: function (data, error) {
                        $.alert(error);
                    }
                });
                return false;
            }, 1200);
        }
        return false;
    });

    $('#backLogin').click(function () {
        window.location.href = '/o2o/local/login?usertype=' + usertype;
    });

    var count = 1;
    $('#showPassword').click(function () {
        if (count % 2 == 0) {
            count++;
            //不显示密码
            $('#password').attr("type", "password");
            $('#newPassword').attr("type", "password");
            $('#psw').attr("type", "password");

        } else {
            count++;
            //显示密码
            $('#password').attr("type", "text");
            $('#newPassword').attr("type", "text");
            $('#psw').attr("type", "text");
        }
    });

});