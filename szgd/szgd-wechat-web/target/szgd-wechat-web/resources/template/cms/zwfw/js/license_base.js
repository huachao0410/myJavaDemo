/*获取数据*/
function getData(url,data,callback,err){
	var waitting = layer.load(1);
	$(".weui-loading").removeClass("hideCls");
	$.ajax({
		type:"get",
		url:url,
		async:true,
		data:data,
		success:function(data){
			layer.close(waitting);
			if('200'==data.code ){
				callback(data.datas);
			}else{
				err&&err();
			}
			$(".weui-loading").addClass("hideCls");
		},
		error : function(e) {
			layer.close(waitting);
			if(err){
				err(e);
			}else{
				alert("服务异常，服务出错了!");
			}
		}
	});
}

function getUserCardStatus(url,data,callback,err){
    var waitting = layer.load(1);
    $(".weui-loading").removeClass("hideCls");
    $.ajax({
        type:"get",
        url:url,
        async:true,
        data:data,
        success:function(data){
            layer.close(waitting);
            if('200'==data.code ){
                callback(data);
            }else{
                err&&err();
            }
            $(".weui-loading").addClass("hideCls");
        },
        error : function(e) {
            layer.close(waitting);
            if(err){
                err(e);
            }else{
                alert("服务异常，服务出错了!");
            }
        }
    });
}

//cookie
function setItemData(key, value) {
	$.cookie(key, value, {  path: '/' });
}

function getItemData(key) {
	if($.cookie(key)) {
		return $.cookie(key);
	} else {
		return null;
	}
}

function deleteCookies(key) {
	$.removeCookie(key, {  path: '/' });
}