var prefix = "/Tenfoot-Web/order";
$().ready(function() {
	
});

//订单配送
$("#orderDistribution").on('click',function () {
    layer.confirm("确认订单配送吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        $.ajax({
            type: 'POST',
            data: {
                "id": $("#id").val()
            },
            url: prefix + '/orderDistribution',
            success: function (r) {
                if (r.code == 1) {
                    location.reload();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
});

//订单完成
$("#orderComplete").on('click',function () {
    layer.confirm("确认订单完成吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        $.ajax({
            type: 'POST',
            data: {
                "id": $("#id").val()
            },
            url: prefix + '/orderComplete',
            success: function (r) {
                if (r.code == 1) {
                    location.reload();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
});

$("#orderRefund").on('click',function () {
    layer.confirm("确认申请退款吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        $.ajax({
            type: 'POST',
            data: {
                "id": $("#id").val()
            },
            url: prefix + '/orderRefund',
            success: function (r) {
                if (r.code == 1) {
                    location.reload();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
})

//返回
$("#back").on('click',function () {
    window.history.back();
});