var _WXACCOUNT = 3;
//var _OPENID = "oJWLgjqhjLmU2w42XXj_iGJBTtbA";
/*提示*/
function myAlert(data) {
	alert("网络异常");
}

/*截取url参数*/
function getQueryString(key) {
	var search = decodeURIComponent(location.search);
	var reg = new RegExp(".*" + key + "\\=" + "([^&]*)(&?.*|)", "g");
	return search.replace(reg, "$1");
}

/*cookie*/
$.removeCookie = function(key, options) {
	if($.cookie(key) === undefined) {
		return false;
	}

	// Must not alter options, thus extending a fresh object...
	$.cookie(key, '', $.extend({}, options, { expires: -1 }));
	return !$.cookie(key);
};

function setItemData(key, value) {
	$.cookie(key, value, {
		path: '/'
	});
}

function getItemData(key) {
	if($.cookie(key)) {
		return $.cookie(key);
	} else {
		return null;
	}
}

function deleteCookies(key) {
	$.removeCookie(key, {
		path: '/'
	});
}

/**
 * 获取地址参数，用法getSearchObj().userId。
 */
function getSearchObj() {
	var obj = {};
	var searchStr = window.location.search;
	var arr = searchStr.substring(searchStr.indexOf('?') + 1).split('&');
	for(var i = 0; i < arr.length; i++) {
		var str = arr[i];
		var index = str.indexOf('=');
		var key = str.substring(0, index);
		var val = str.substring(index + 1);
		obj[key] = val;
	}
	return obj;
};

/**
 * 设置缓存数据
 * @param {Object} key
 * @param {Object} value
 */
function setCacheData(key, value) {
	if(value != null && value.length > 0) {
		$.cookie(key, value, { path: '/' });
		localStorage.setItem(key, value);
	}
}

/**
 * 获取缓存数据
 * @param {Object} key
 */
function getCacheData(key) {
	if($.cookie(key)) {
		return $.cookie(key);
	} else if(localStorage.getItem(key)) {
		return localStorage.getItem(key);
	} else {
		return null;
	}
}

/**
 * 删除缓存数据
 * @param {Object} key
 */
function deleteCacheData(key) {
	$.removeCookie(key, { path: '/' });
	localStorage.removeItem(key);
}

/*记录日志*/
//function journal(id,url){
//	$.ajax({
//		type:"post",
//		url:_setJournalUrl,
//		async:true,
//		data:{
//			:      //openid
//			:      //页面标识
//		}
//	});
//}