 /**
  * 获取地址参数，用法getSearchObj().userId。
  */
function getSearchObj() {
	var obj={};
	var searchStr=window.location.search;
	var arr=searchStr.substring(searchStr.indexOf('?')+1).split('&');
	for(var i=0;i<arr.length;i++){
		var str=arr[i];
		var index=str.indexOf('=');
		var key=str.substring(0,index);
		var val=str.substring(index+1);
		obj[key]=val;
	}
	return obj;
};

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

/*空数据处理*/
function nullChange(obj) {
	var result = "";
	if(!obj) {
		result = "无";
	} else {
		result = obj;
	}
	return result;
}

/*空数据处理*/
function nullNumber(obj) {
	var result = 0;
	if(obj) {
		result = obj;
	}
	return result;
}