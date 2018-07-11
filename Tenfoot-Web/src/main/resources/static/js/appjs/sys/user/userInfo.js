var prefix = "/Tenfoot-Web";
$(function () {
    validateRule();


    $("#distpicker").distpicker({
        province: province == null ? "广东省" : province,
        city: city == null ? "广州市" : city,
        district: region == null ? "天河区" : region
    });


    layui.use('upload', function () {
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#headImg', //绑定元素
            url: prefix + '/sys/user/uploadImg?flag=1', //上传接口
            size: 1000,
            accept: 'images',//images（图片）、file（所有文件）、video（视频）、audio（音频）
            done: function (r) {
                if (r.code == 1) {
                    $("#headImg").attr("src", r.fileName);
                    $(window.parent.document).find("#headImg").attr("src", r.fileName);
                }
                layer.msg(r.msg);
            },
            error: function (r) {
                layer.msg(r.msg);
            }
        });
    });


    $('.form_time').datetimepicker({
        language: 'fr',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
    });

    init();

})


$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});
function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: prefix + "/sys/user/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            if (data.code == 1) {
                layer.msg(data.msg);
            } else {
                layer.msg(data.msg);
            }

        }
    });

}


function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {

            name: {
                required: icon + "请输入姓名"
            }
        }
    })
}

//初始化地图函数  自定义函数名init
var geocoder, map, marker = null;
var init = function () {

    if (lat == '') {
        lat = 23.08331, lng = 113.3172;
    }
    var center = new qq.maps.LatLng(lat, lng);
    map = new qq.maps.Map(document.getElementById('container'), {
        center: center,
        zoom: 18
    });

    //添加标注
    var marker = new qq.maps.Marker({
        map: map,
        position: center
    });

    qq.maps.event.addListener(map,'click',function(event) {
            // console.log('您点击的位置为:[' + event.latLng.getLng() + ',' + event.latLng.getLat() + ']');

            $("#lat").val(event.latLng.getLat());
            $("#lng").val(event.latLng.getLng());

            marker.setPosition(event.latLng);
        }
    );

    //调用地址解析类
    geocoder = new qq.maps.Geocoder({
        complete: function (result) {
            map.setCenter(result.detail.location);

            $("#lat").val(result.detail.location.lat);
            $("#lng").val(result.detail.location.lng);

            marker.setPosition(result.detail.location);

        }
    });

}


function codeAddress() {
    //通过getLocation();方法获取位置信息值
    geocoder.getLocation($("#province").val() + $("#city").val() + $("#region").val() + $("#address").val());
}