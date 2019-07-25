$(function () {
   $("#button_quit").click(function () {
       if (false == confirm("确定退出吗?")){
           return false;
       }
       $("#quitForm").submit();
   });
});