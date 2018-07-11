var prefix = "/Tenfoot-Web";
$().ready(function() {
	validateRule();

    layui.use('upload', function () {
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#Image', //绑定元素
            url: prefix + '/sys/user/uploadImg?flag=2', //上传接口
            size: 1000,
            accept: 'images',//images（图片）、file（所有文件）、video（视频）、audio（音频）
            done: function (r) {
                if(r.code == 1){
                    $("#Image").attr("src",r.fileName);
                    $("#productImage").val(r.fileName);
                }
                layer.msg(r.msg);
            },
            error: function (r) {
                layer.msg(r.msg);
            }
        });
    });


});

$.validator.setDefaults({
	submitHandler : function() {
	    var productCategoryMiddle = [];

        $('input[name="productCategoryMiddle"]:checked').each(function(){
            productCategoryMiddle.push($(this).val());
        });
		saveSpec();//保存规格
		if($("#productImage").val() == ''){
            layer.msg("商品图片必须上传");
			return ;
		}

        if(productCategoryMiddle.length == 0){
            layer.msg("商品分类必须选择一项");
            return ;
        }

		var data = eval("("+$("#spec").val()+")");
		if(data.smallData.length==0){
            layer.msg("商品规格必须勾选一项");
			return ;
		}
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/product/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 1) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            Image : {
                required: true
			},
            productName: {
                required: true
            },
            price: {
                required: true
            }
        },
		messages : {
            Image : {
                required: icon + "商品名称必须输入"
            },
            productName: {
                required: icon + "商品名称必须输入"
            },
            price: {
                required: icon + "商品价格必须输入"
            }
		}
	})
}

//增加规格
$("#add_spec").on('click',function () {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '400px', '270px' ],
		content : prefix+ '/product/addSpec?flag=1' // iframe的url
	});
})

//增加属性
function addAttribute(obj){
    if($(obj).prev().val()!='' && $(obj).prev().prev().val() != ''){
    	var html ='<label class="checkbox-inline"><input type="checkbox" value="'+$(obj).prev().val()+'">'+$(obj).prev().prev().val()+'/'+$(obj).prev().val()+'</label>'
		$(obj).parent().prev().children(".col-sm-9").append(html);
		$(obj).prev().val(0);
		$(obj).prev().prev().val('');
    }
}



function saveSpec() {
    var object = new Object();
    var arr = $("#selectedData").children("div");
    var specArrWeb = new Array();
    var specArrSmall = new Array();
    $.each(arr,function (i,v) {
        var specObjWeb = new Object();
        var specObjSmall = new Object();
        specObjWeb.specName = $(v).find(".control-label").text().replace("：","");
        specObjSmall.specName = $(v).find(".control-label").text().replace("：","");
        specObjWeb.specChecked = $(v).find(".control-label").children().prop("checked");

        var listArrWeb = new Array();
        var listArrSmall = new Array();
        $.each($(v).find(".col-sm-9>label"),function (j,k) {
            var listObjWeb = new Object();
            var listObjSmall = new Object();
            listObjWeb.listName = $(k).text().trim();
            listObjWeb.listPrice = $(k).children().val();
            listObjWeb.listChecked = $(k).children().prop("checked");

            listObjSmall.listName = $(k).text().split("/")[0];
            listObjSmall.listPrice = $(k).children().val();

            listArrWeb.push(listObjWeb);
            if($(k).children().prop("checked")){
                listArrSmall.push(listObjSmall);
            }
        })
        specObjWeb.specList = listArrWeb;
        specObjSmall.specList = listArrSmall;
        specArrWeb.push(specObjWeb);

        if($(v).find(".control-label").children().prop("checked")){
            if(listArrSmall.length>0){
                specArrSmall.push(specObjSmall);
            }
        }
    })

    object.webData = specArrWeb;//后台调用数据
    object.smallData = specArrSmall;//小程序调用数据

    $("#spec").val($.toJSON(object));

}