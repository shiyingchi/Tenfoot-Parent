var prefix = "/Tenfoot-Web/order"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                clickToSelect: true,                //是否启用点击选中行
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset
                        // name:$('#searchName').val(),
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'id',
                        title: '订单号码',
                        align: 'center',
                    },
                    {
                        field: 'userName',
                        title: '用户昵称',
                        align: 'center',
                    },
                    {
                        field: 'phone',
                        title: '用户手机号',
                        align: 'center',
                    },
                    {
                        field: 'createTime',
                        title: '下单时间',
                        align: 'center',
                    },
                    {
                        field: 'payCreateTime',
                        title: '支付时间',
                        align: 'center',
                    },
                    {
                        field: 'distributionTime',
                        title: '到店时间',
                        align: 'center',
                    },
                    {
                        field: 'payAmount',
                        title: '支付金额',
                        align: 'center',
                    },
                    {
                        field: 'status',
                        title: '订单状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            //状态：0待支付，1已支付，2失败，3已配送，4已完成，5已删除 6退款
                            value = value == 0 ? "<font color='red'>待支付</font>" : value == 1 ? "已支付" : value == 2 ? "<font color='red'>失败</font>" : value == 3 ? "已配送" : value == 4 ? "已完成" : value == 5 ? "已删除": value == 6 ?"申请退款":value == 7?"退款成功":value==8?"退款失败":""
                            return value;
                        }
                    },
                    {
                        field: 'state',
                        title: '取餐方式',
                        align: 'center',
                        formatter : function (value, row, index) {
                            //0外卖 1到店
                            value = value == 0?"外卖":value == 1 ? "到店":"";
                            return value;
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="详情" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return e + d;
                        }
                    }]
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}
function edit(id) {
    window.location.href = prefix + '/edit/' + id;
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 1) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
}
function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 1) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}