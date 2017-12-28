/*组件 js*/


/*
 * 单选框组件 -html
 * options 对象数组
 * 	对象包含：
 * 	 name  单选name组
 *   value 单选val值
 *   chineseName 中文名
 *   checked 判断是否首选
 * radioTitle 单选按钮标题
 * radioTips 单选按钮提示
 */
function radioHtml(options,radioTitle,radioTips){
	var html='<div class="weui-cells" id="'+options[0].name+'">';
	//TODO 可加上标题html
	if(null != radioTitle){
		html+='<div class="weui-cell switch-head">'+radioTitle+'</div>';
	}
	html+='<div class="weui-cell">';
	html+='<div class="weui-cell__bd weui-cells_checkbox">';
	
	//根据按钮数量设置每个按钮百分比，最大分成3份
	var fm=options.length>3?3:options.length;
	var width=parseFloat(100/fm);
	
	for(var i=0;i<options.length;i++){
		var check=options[i].checked !=null && options[i].checked =='Y'?'checked="checked"':'';
		html+='<label class="weui-cell weui-check__label radio-css" style="width:'+width+'%" for="'+options[i].name+options[i].value+'">';
		html+='<div class="weui-cell__hd">';
		html+='<input type="radio" name="'+options[i].name+'" class="weui-check" '+check+' id="'+options[i].name+options[i].value+'" value="'+options[i].value+'">';
		html+='<i class="weui-icon-checked"> </i>';
		html+='</div>';
		html+='<div class="weui-cell__bd">';
		html+=' <p>'+options[i].chineseName+'</p>';
		html+='</div>';
		html+='</label>';
	}
	html+='</div>';
	html+='</div>';
	
	//按钮提示
	if(null != radioTips){
		html+='<div class="weui-cell" id="'+options[0].name+'Tips">';
		html+=radioTips;
		html+='</div>';
	}
	
	html+='</div>';
	return html;
}