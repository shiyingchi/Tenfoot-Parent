var prefix = "/Tenfoot-Web";
var menuIds;
$().ready(function() {
	getMenuTreeData();
	validateRule();
});
function getAllSelectNodes() {
    var ref = $('#menuTree').jstree(true); // 获得整个树

    menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组

    $("#menuTree").find(".jstree-undetermined").each(function(i, element) {
        menuIds.push($(element).closest('.jstree-node').attr("id"));
    });
}

$.validator.setDefaults({
	submitHandler : function() {
        getAllSelectNodes();
		save();
	}
});
function save() {
    $('#menuIds').val(menuIds);
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/system/sysDept/save",
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

function getMenuTreeData() {
    $.ajax({
        type : "GET",
        url : prefix + "/sys/menu/tree",
        success : function(menuTree) {
            loadMenuTree(menuTree);
        }
    });
}
function loadMenuTree(menuTree) {
    $('#menuTree').jstree({
        'core' : {
            'data' : menuTree
        },
        "checkbox" : {
            "three_state" : true,
        },
        "plugins" : [ "wholerow", "checkbox" ]
    });
    //$('#menuTree').jstree("open_all");

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}