$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
    var html = "";
        html +='<div class="form-group" >';
        html +='    <label class="col-sm-3 control-label"></label>';
        html +='    <div class="col-sm-8">';
        html +='        <div class="form-group">';
        html +='            <label class="col-sm-2 control-label checkbox-inline"><input type="checkbox">'+$("#specName").val()+'：</label>';
        html +='            <div class="col-sm-9">';
        html +='                <label class="checkbox-inline"><input type="checkbox" value="0">常规</label>';
        html +='            </div>';
        html +='        </div>';
        html +='        <div class="col-sm-7 col-sm-offset-2" style="overflow: hidden;">';
        html +='             <input class="form-control" type="text" style="width: 60px;float: left" placeholder="名称">';
        html +='             <input class="form-control" type="text" style="width: 60px;float: left" value="0" placeholder="价格">';
        html +='             <button class="btn btn-success" type="button" onclick="addAttribute(this)" style="float: left">增加</button>';
        html +='        </div>';
        html +='    </div>';
        html +='</div>';
        $(window.parent.document).find("#selectedData").append(html);
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
            Name : {
				required : true
			}
		},
		messages : {
            Name : {
				required : icon + "请输入名称"
			}
		}
	})
}


