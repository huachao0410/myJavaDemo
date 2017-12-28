// 邮政编码验证   
jQuery.validator.addMethod("isZipCode", function(value, element) {   
    var tel = /^[0-9]{6}$/;
    return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");

//身份证校验
jQuery.validator.addMethod("idCardNo", function(value, element) { 
//    return this.optional(element) || isIdCardNo(value);
	//身份证后台校验
	var deferred = $.Deferred();//创建一个延迟对象
	$.ajax({
		type:"get",
		url:"http://139.199.223.78:8098/govnetProvider/services/validate/IdCardValidate/"+value,
		async:false,
		data:{},
		success:function(data){
			if(data.code == '200' && data.datas.length >0){
				if(data.datas[0]=='true' || data.datas[0]== true){
					deferred.resolve();
				}else{
					deferred.reject();
				}
			}else{
				deferred.reject();
			}
			
		},
		error : function(e) {
			deferred.reject();
		}
	});
	 //deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
    return deferred.state() == "resolved" ? true : false;
}, "输入不正确");

//日期校验
jQuery.validator.addMethod("isEnoughDate", function(value, element,params) {
	var srcDate=value.split("-");
	var today=new Date();
	var year=today.getFullYear();
	if(year-srcDate[0]>params[0]){
		return true;
	}else if(year-srcDate[0]==params[0]){
		//年份刚好相差2年判断天数是否大于等于0
		srcDate=new Date(year+'-'+srcDate[1]+'-'+srcDate[2]);
		var days=parseFloat((today-srcDate)/1000/60/60/24);
		if(days>=0){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}, "结婚时间需满两年");

//中文校验校验
jQuery.validator.addMethod("vdChinese", function(value, element,params) {
	 return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);
}, "请正确填写信息");


//身份证号码的验证规则
function isIdCardNo(num){
	var len =num.length;
	var re=null;
	if(len==15){
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
	}else if (len == 18){
	 	re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
	}else {
		//alert("输入的数字位数不对。"); 
		return false;
	}
	var a = num.match(re); 
	if(a){
		var B=false,D;
		if(len==15){
			D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
			B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
		}else{
			D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
			B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}
		
		if(!B){
			//alert("输入的身份证号 "+ a[0] +" 里出生日期不对。"); 
			return false;
		}
	}
	
	if(!re.test(num)){
		//alert("身份证最后一位只能是数字和字母。");
		return false;
	}
	
	return true;
}
