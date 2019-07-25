$(function(){
    $(".li_manage").mouseover(function () {
        $(this).css({
            background: "#1581CC"
        });
    });
    $(".li_manage").mouseleave(function () {
        $(this).css({
            background: "skyblue"
        });
    });

    $(".li_manage").click(function () {
        var id = this.id;
        console.log(id);
        var target_div = "/admin_manage/" + id;
        $("#manage_div").load(target_div);
    });

    var replace = $("#input_replace").val();
    console.log(replace);
    $("#manage_div").load(replace);
});