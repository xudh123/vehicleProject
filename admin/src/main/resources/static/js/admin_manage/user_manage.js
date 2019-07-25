$(function () {
    $("#add_user").click(function () {
        $("#table_addItem").css({
            display: "block"
        })
    });
    $("#quit").click(function () {
        $("#table_addItem").css({
            display: "none"
        });
        return false;
    });
    $("#submit_addUser").click(function () {
        var userId = $("#user_id").val();
        var userName = $("#user_name").val();
        var password = $("#password").val();

        if (userId == "" || isNaN(userId)){
            alert("编号输入为空或输入的不是数字");
            return false;
        }else if( userName == ""){
            alert("用户名输入为空");
            return false;
        }else if (password == ""){
            alert("密码为空");
            return false;
        }
    });
});