Date.getDaysOfMonth = function(dt) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	var	y = dt.getFullYear();
	var	Mm = dt.getMonth();
	var Feb = (y % 4 == 0) ? 29 : 28;
	var aM = [31, Feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	return aM[Mm];
};

Date.getDateOfPreMonth = function(dt) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	var y = (dt.getMonth() == 0) ? (dt.getFullYear() - 1) : dt.getFullYear();
	var m = (dt.getMonth() == 0) ? 11 : dt.getMonth() - 1;
	var preM = Date.getDaysOfMonth(new Date(y, m, 1));
	var d = (preM < dt.getDate()) ? preM : dt.getDate();
	return new Date(y, m, d);
};

Date.getDateOfNextMonth = function(dt) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	var y = (dt.getMonth() == 11) ? (dt.getFullYear() + 1) : dt.getFullYear();
	var m = (dt.getMonth() == 11) ? 0 : dt.getMonth() + 1;
	var preM = Date.getDaysOfMonth(new Date(y, m, 1));
	var d = (preM < dt.getDate()) ? preM : dt.getDate();
	return new Date(y, m, d);
};

Date.getFirstDayOfMonth = function(dt) {
	return Date.getDayOfMonth(dt, 1);
};

Date.getDayOfMonth = function(dt, date) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	var y = dt.getFullYear();
	var m = dt.getMonth();
	var d = date;
	return new Date(y, m, d);
};

Date.getDateString = function(dt, date) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	var thisDate = dt.getDate();
	if(date) {
		thisDate = date;
	}
	return dt.getFullYear() + "-" + PrefixInteger((dt.getMonth() + 1),2) + "-" + PrefixInteger(thisDate,2);
}

Date.getTimeString = function(dt) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	return dt.getFullYear() + "-" + PrefixInteger((dt.getMonth() + 1),2) + "-" + PrefixInteger(dt.getDate(),2) 
			+ " " + PrefixInteger(dt.getHours(),2) + ":" + PrefixInteger(dt.getMinutes(),2) + ":" + PrefixInteger(dt.getSeconds(),2);
}

Date.getMonthTitle = function(dt) {
	if(typeof dt == 'undefined') {
		dt = (new Date());
	}
	return dt.getFullYear() + "年" + (dt.getMonth()+1) + "月";
};

Date.string2Date = function(strDate) {
	return new Date(strDate.replace("-", "/").replace("-", "/"));
}

Date.string2TimeString = function(strDate) {
	var dt = Date.string2Date(strDate);
	return Date.getTimeString(dt);
}

Date.long2Date = function(ld) {
	return new Date(ld);
}

Date.long2String = function(ld) {
	if(typeof ld == 'undefined') {
		return "无";
	}
	return Date.getTimeString(new Date(ld));
}

function PrefixInteger(num, length) {  
 	return (Array(length).join('0') + num).slice(-length);  
} 

Date.welcome = function() {
	var now = new Date(), hour = now.getHours();
	if(hour < 6) {
		return "凌晨好";
	} else if(hour < 9) {
		return "早上好";
	} else if(hour < 12) {
		return "上午好";
	} else if(hour < 14) {
		return "中午好";
	} else if(hour < 18) {
		return "下午好";
	} else if(hour < 24) {
		return "晚上好";
	} else {
		return "欢迎你";
	}
}
