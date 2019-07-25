$(function () {
   $("#add_brand").click(function () {
       $("#table_addItem").css({
           display: "block"
       })
   });
   $("#quit").click(function () {
       $("#table_addItem").css({
           display: "none"
       })
       return false;
   });
   $("#submit_addBrand").click(function () {
        var brandId = $("#brand_id").val();
        var brandName = $("#brand_name").val();
        var brandImage = $("#brand_image").val();

        if (brandId == "" || isNaN(brandId)){
            alert("编号输入为空或输入的不是数字");
            return false;
        }else if( brandName == ""){
            alert("名称输入为空");
            return false;
        }else if (brandImage == ""){
            alert("品牌图片为空");
            return false;
        }
   });
});