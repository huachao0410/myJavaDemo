var bms = {};
jQuery.extend({
	format: function() {
        if (arguments.length == 0)
            return null;

        var result = arguments[0];
        for (var i = 1; i < arguments.length; i++) {
            var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
            result = result.replace(re, arguments[i]);
        }
        return result;
    },
    decodeURIComponent: function(obj) {
        if (!obj) {
            return null;
        }
        if (obj.length) {
            var builder = [];
            for (var i = 0; i < obj.length; i++) {
                builder.push($.decodeURIComponent(obj[i]));
            }
            return builder;
        }
        else {
            var builder = {};
            for (var key in obj) {
                var t = typeof (obj[key]);
                if (t == "number" || t == "boolean" || t == "function") {
                    builder[key] = obj[key];
                }
                else if (t == "string") {
                    builder[key] = decodeURIComponent(obj[key].toString());
                }
                else {
                    builder[key] = $.decodeURIComponent(obj[key]);
                }
            }
            return builder;
        }
    },
    serialize: function(obj) {
        var ransferCharForJavascript = function(s) {
            var newStr = s.replace(
                            /[\x26\x27\x3C\x3E\x0D\x0A\x22\x2C\x5C\x00]/g,
                            function(c) {
                                ascii = c.charCodeAt(0);
                                return '\\u00' + (ascii < 16 ? '0' + ascii.toString(16) : ascii.toString(16));
                            }
                        );
            return newStr;
        };
        if (obj == null) {
            return null;
        }
        else if (obj.constructor == Array) {
            var builder = [];
            builder.push("[");
            for (var index in obj) {
                if (typeof obj[index] == "function") {
                	continue;
                }
                if (index > 0) {
                	builder.push(",");
                }
                builder.push($.serialize(obj[index]));
            }
            builder.push("]");
            return builder.join("");
        }
        else if (obj.constructor == Object) {
            var builder = [];
            builder.push("{");
            var index = 0;
            for (var key in obj) {
                if (typeof obj[key] == "function") {
                	continue;
                }
                if (index > 0) {
                    builder.push(",");
                }
                builder.push($.format("\"{0}\":{1}", key, $.serialize(obj[key])));
                index++;
            }
            builder.push("}");
            return builder.join("");
        }
        else if (obj.constructor == Boolean) {
            return obj.toString();
        }
        else if (obj.constructor == Number) {
            return obj.toString();
        }
        else if (obj.constructor == String) {
            return $.format('"{0}"', ransferCharForJavascript(obj));
        }
        else if (obj.constructor == Date) {
            return $.format('{"__DataType":"Date","__thisue":{0}}', obj.getTime() - (new Date(1970, 0, 1, 0, 0, 0)).getTime());
        }
        else if (this.toString != undefined) {
            return $.serialize(obj);
        }
    },
    splitValueAndText: function(data, flag) {
		if(data == null) {
			return null;
		}
		if($.isArray(data)) {
			var builder = [];
			for (var i = 0; i < data.length; i++) {
                builder.push($.splitValueAndText(data[i], flag));
            }
			return builder;
		}
		else if($.isPlainObject(data)) {
			var builder = {};
            for (var key in data) {
            	builder[key] = $.splitValueAndText(data[key], flag);
            }
            return builder;
		}
		else if(typeof(data) == "string") {
			var reg = new RegExp("/Text\\((.*)\\)/$");
    		var mch = data.match(reg);
    		if(mch) {
				if(flag == 1) {
					data = data.replace(reg, "");
				}
				else if(flag == 2) {
					data = mch[1];
				}
			}
    		return data;
		}
		else {
			return data;
		}
	},
    date: {
    	characters: ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'],
        toUpperCase: function(value, literal) {
            if (literal) {
                return (String(value)).replace(/([0-9])/g, function($0) {
                    return $.date.characters[$0];
                });
            }
            else {
                value = Number(value);
                if (value < 10) {
                    return $.date.characters[value];
                }
                else if (value < 20) {
                    return '十' + (value % 10 == 0 ? '' : $.date.characters[value % 10]);
                }
                else if (value < 100) {
                    return $.date.characters[Math.floor(value / 10)] + '十' + (value % 10 == 0 ? '' : $.date.characters[value % 10]);
                }
                else if (value < 1000) {
                    return $.date.characters[Math.floor(value / 100)] + '百' + (value % 100 >= 10 ? $.date.characters[Math.floor((value % 100) / 10)] + '十' : (value % 10 > 0 ? '零' : '')) + (value % 10 > 0 ? $.date.characters[value % 10] : '');
                }
                else {
                    return value;
                }
            }
        },
    	format: function(date, pattern, upperCase) {
    		if(!date) {
    			return "";
    		}   		
            var zeroize = function(value, length) {
                if (!length) {
                    length = 2;
                }
                value = String(value);
                for (var i = 0, zeros = ''; i < (length - value.length); i++) {
                    zeros += '0';
                }
                return zeros + value;
            };
             
            return pattern.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
                switch ($0) {
                    case 'd':
                        return !upperCase ? date.getDate() : $.date.toUpperCase(date.getDate());
                    case 'dd':
                        return !upperCase ? zeroize(date.getDate()) : $.date.toUpperCase(date.getDate());
                    case 'ddd':
                        return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
                    case 'dddd':
                        return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
                    case 'M':
                        return !upperCase ? (date.getMonth() + 1) : $.date.toUpperCase(date.getMonth() + 1);
                    case 'MM':
                        return !upperCase ? zeroize(date.getMonth() + 1) : $.date.toUpperCase(date.getMonth() + 1);
                    case 'MMM':
                        return ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'][date.getMonth()];
                    case 'MMMM':
                        return ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'][date.getMonth()];
                    case 'yy':
                        return !upperCase ? zeroize(date.getFullYear() % 100) : $.date.toUpperCase(String(date.getFullYear()).substr(2), true);
                    case 'yyyy':
                        return !upperCase ? zeroize(date.getFullYear(), 4) : $.date.toUpperCase(date.getFullYear(), true);
                    case 'h':
                        return !upperCase ? (date.getHours() % 12 || 12) : $.date.toUpperCase(date.getHours() % 12 || 12);
                    case 'hh':
                        return !upperCase ? zeroize(date.getHours() % 12 || 12) : $.date.toUpperCase(date.getHours() % 12 || 12);
                    case 'H':
                        return !upperCase ? date.getHours() : $.date.toUpperCase(date.getHours());
                    case 'HH':
                        return !upperCase ? zeroize(date.getHours()) : $.date.toUpperCase(date.getHours());
                    case 'm':
                        return !upperCase ? date.getMinutes() : $.date.toUpperCase(date.getMinutes());
                    case 'mm':
                        return !upperCase ? zeroize(date.getMinutes()) : $.date.toUpperCase(date.getMinutes());
                    case 's':
                        return !upperCase ? date.getSeconds() : $.date.toUpperCase(date.getSeconds());
                    case 'ss':
                        return !upperCase ? zeroize(date.getSeconds()) : $.date.toUpperCase(date.getSeconds());
                    case 'l':
                        return !upperCase ? zeroize(date.getMilliseconds(), 3) : $.date.toUpperCase(date.getMilliseconds());  //, date.getMilliseconds() > 100
                    case 'L':
                        var m = date.getMilliseconds();
                        if (m > 99) {
                            m = Math.round(m / 10);
                        }
                        return zeroize(m);
                    case 'tt':
                        return date.getHours() < 12 ? 'am' : 'pm';
                    case 'TT':
                        return date.getHours() < 12 ? 'AM' : 'PM';
                    case 'Z':
                        return date.toUTCString().match(/[A-Z]+$/);
                    default:                       
                        return $0.substr(1, $0.length - 2);

                }
            });
        },
        parse: function(value, pattern) {
        	var matchs1 = pattern.match(/"[^"]*"|'[^']*'|\b(?:d{1,2}|M{1,2}|yy(?:yy)?|([Hms])\1?|[l])\b/g);
            var matchs2 = value.match(/(\d+)/g);
            if (matchs1 && matchs2 && (matchs1.length == matchs2.length)) {
                var date = new Date(1970, 0, 1);
                for (var i = 0; i < matchs1.length; i++) {
                	var field = matchs1[i];
                	var value = matchs2[i];
                    if(value.length != field.length) {
                    	return null;
                    }
                    value = parseInt(value, 10);
                    switch (field.charAt(0)) {
	                    case 'y': 
	                    	date.setFullYear(value); 
	                    	break;
	                    case 'M': 
	                    	date.setMonth(value - 1); 
	                    	break;
	                    case 'd': 
	                    	date.setDate(value); 
	                    	break;
	                    case 'H': 
	                    	date.setHours(value); 
	                    	break;
	                    case 'm': 
	                    	date.setMinutes(value); 
	                    	break;
	                    case 's': 
	                    	date.setSeconds(value); 
	                    	break;
	                    case 'l': 
	                    	date.setMilliseconds(value); 
	                    	break;
                    }
                }
                return date;
            }
            return null;
        }
    },
    query: {
        get: function(key, url) {
            var reg = new RegExp("\\?(?:.+&)?" + key + "=(.*?)(?:&.*)?$");
            var mch = (url || window.location.href).match(reg);
            return mch ? mch[1] : "";
        },
        getId : function(){
            var id = window.location.pathname.match(new RegExp("\/([0-9]+)$"))[1];
            return id;
        }
    },
    modifiers: {
    	formatNumber: function(number, precision) {
            return (parseFloat(number, 10) >= 0 ? parseFloat(number, 10).toFixed(precision ? precision : 0) : '');
        },
        formatDatetime: function(input, pattern, upperCase) {
        	if(!input) {
        		return "";
        	}
        	var date = $.date.parse(input, "yyyy-MM-dd HH:mm:ss");
        	if(date) {
        		if(!pattern) {
        			pattern = "yyyy-MM-dd";
        		}
        		return $.date.format(date, pattern, upperCase);
        	}
        	return input;
        },
        formatDate: function(date, pattern, upperCase) {
            if (typeof (date) == "undefined") {
                return "";
            }
            var regExp = new RegExp("^/Date\\((-?\\d+)\\)/$");
            var matchs = date.match(regExp);
            if (matchs) {
                var ms = parseInt(matchs[1], 10);
                if (ms == -2209017600000) {
                    return "";
                }
                if (ms % 100000 == 0 && !pattern) {
                    pattern = 'yyyy-MM-dd';
                }
                return $.date.format(new Date(ms), pattern || 'yyyy-MM-dd HH:mm', upperCase);
            }
            else {
                return date;
            }
            return "";
        }
    }
});
bms.formMethods = {
	'collect' : function(el) {
		var collection = {};
		var t = $(el);
		t.find(":input[name]").not(":button,:submit,:reset,:image,:radio").each(
				function() {					
					var box = $(this);					
					var key = $.trim(box.attr("name"));
					var val = $.trim(box.val());
					if (key === "") {
						return true;
					}
					if (typeof (collection[key]) == "undefined") {
						collection[key] = val;
					} else {
						if ($.isArray(collection[key])) {
							collection[key].push(val);
						} else {
							collection[key] = [ collection[key], val ];
						}
					}
				});
		return collection;
	},
	'load' : function(el, data) {
		var t = $(el);
		for ( var key in data) {
			if (data[key] == null) {
				data[key] = "";
				continue;
			}
			var box = t.find(":input[name='" + key + "']");
			if (box.size() === 1) {
				box.val(data[key]);
			} 
		}
	},
	'trim':function(el){
		$(el).find(":input[name]").not(":button,:reset,:image,:submit,:radio,:checkbox").each(function(){			
			var box = $(this);
			box.val($.trim(box.val()));							
		});
	}
};
bms.datagridMethods = {	
	'load':function(el, opts){				
		var $el=$(el),idname=el.id;							
		var list_col=opts.columns,len_col=list_col.length;
		//默认的参数
		var defaults={
			type:'get',
			hasCheckbox:true,
			hasPage:true,
			checkboxSize:'multiple',
			idField:'id',
			len:10,
			data:{}
		};
		
		var options = jQuery.extend({}, defaults, opts);
						
		var	names=options.names,
			hasCheckbox=options.hasCheckbox;
		
		var queryParams=options.data,
			checkboxSize=options.checkboxSize,
			idField=options.idField;	
		
		var fieldIndex={},dataCallback={};
		var btns=[];
		
		$el.addClass('table table-hover table-bordered table-bg table-responsive');						
		
		if(options.hasPage){						
			var sHtml='<thead>';
			var hiddens=[];
			if(hasCheckbox){
				sHtml+='<th class="text-left" width="30px"><input class="btn-control" type="checkbox" style="display:none"/></th>';
			}			
			for(var i=0;i<len_col;i++){
				fieldIndex[list_col[i].field]=i;
				if(list_col[i].xshidden){
					sHtml+='<th width="'+list_col[i].width+'" class="hidden-xs">'+list_col[i].title+'</th>';
					hiddens.push(i+hasCheckbox);
				}else{
					sHtml+='<th width="'+list_col[i].width+'">'+list_col[i].title+'</th>';
				}
				
				if(list_col[i].type&&list_col[i].type=='btn'){					
					var oTemp=list_col[i];
					oTemp.btnIndex=i+hasCheckbox;
					btns.push(oTemp);				
				}
			}
			sHtml+='</thead>';			
			$el.html(sHtml);
			$el.dataTable().fnDestroy();
			$el.dataTable( {   		            
	            "aDataSort":false,	
	            "bSort":false,
	            "bFilter": false,
	            "bServerSide": true,	            
	            "iDisplayLength": options.len,
	            "lengthMenu": [ 10, 25, 50 ],
	            "sAjaxSource": options.url, 	        
	            "fnServerData": function(sSource, aoData, fnCallback){
	            	var oTemp={};
	            	showLoading();
	            	if(hasCheckbox&&checkboxSize=='multiple'){
	            		$el.find('.btn-control')[0].checked=false;
	            	}	            	
	            		            		            	
	            	for(var i=0;i<aoData.length;i++){
	            		oTemp[aoData[i].name]=aoData[i].value;
	            	}
	            	
	            	var curpage=parseInt(oTemp.iDisplayStart/oTemp.iDisplayLength)+1;	
	            	$(document.body).data(idname,curpage-1);  
	            	queryParams.page=curpage;
	            	queryParams.rows=oTemp.iDisplayLength;
	    		    $.ajax( {   
	    		        "type": options.type,    		       
	    		        "url": options.url,
	    		        "cache": false,
	    		        "dataType": "json",   
	    		        "data": queryParams, 
	    		        "success": function(data) { 
	    		        	var list=data.datas;
	    		        	var arr=[],	idArr=[];	    		        	
	    		        	for(var i=0;i<list.length;i++){	    		        		
	    		        		var inarr=[],
	    		        			obj=list[i],
	    		        			idTemp=obj[idField];
	    		        		
	    		        		idArr.push(idTemp);	    		        		
	    		        		dataCallback[idTemp]=obj;	 
	    		        		
	    		        		if(hasCheckbox){
	    		        			inarr.push("<input class='datagrid-checkbox' type='checkbox' value='"+idTemp+"' />");
	    		        		}
	    		        		for(var j=0;j<len_col;j++){
	    		        			var inobj=list_col[j];	    		        			    		        			    		        			
	    		        			inarr.push(dataFormat(obj[inobj.field],inobj));
	    		        		}  	    		        			    		        		
	    		        		arr.push(inarr);
	    		        	}
	    		        	
	    		            fnCallback({"draw": oTemp.sEcho,"recordsTotal": data.total,"recordsFiltered": data.total,"aaData": arr});
	    		            
	    		            $(".datagrid-checkbox").each(function() {
			    				$(this.parentNode).addClass("text-left");
			    			});
	    		            
	    		            var $trlist=$el.find('tbody tr');
	    		            
	    		            $trlist.each(function(i){
	    		            	$(this).attr('data-id',idArr[i]);
	    		            });
	    		            
	    		            setHidden(hiddens);
	    		            setClick($trlist);
	    		            names&&setNameData(list);	    		            
	    		            btns.length&&setBtns($trlist);	    		            	    		           
	    		            checkboxSize=='multiple'&&setSelectAll();	    							    					   		            	    		        	   
	    		        	options.onDblclick&&setDblclick($trlist);
	    		        	options.onSuccess&&options.onSuccess(list);	    		        		    		        		    		        	
	    		        	hideLoading();
	    		        },
	    		        error:function(e){
	    		        	hideLoading();
	    		        	showErrorMessage(e);	    		        	
	    		        }
	    		    });   
	            },
	            "fnDrawCallback":function(oSettings){	            	
	            	var oTable = $el.dataTable();
	                $(document.body).on(idname,function(){
	                	var n=parseInt($(document.body).data(idname));
	                	oTable.fnPageChange(n);
	                });
	            }
	        });
		}else{									
			$.extend(queryParams, {page:1,rows:10000});			
			$.ajax( {   
		        "type": options.type,    		       
		        "url": options.url,
		        "cache": false,
		        "dataType": "json",   
		        "data": queryParams, 
		        "success": function(data) {		        						
					var list = data.datas;
					
		        	var sHtml = '<thead><tr class="text-left" >';
					if(hasCheckbox){
						sHtml+='<th class="text-left" width="30px"><input class="btn-control" type="checkbox" style="display:none"/></th>';
					}
					for (var i = 0; i < len_col; i++) {
						fieldIndex[list_col[i].field]=i;
						if(list_col[i].xshidden){
							sHtml += '<th class="text-l hidden-xs" width="'+list_col[i].width+'">' + list_col[i].title + '</th>';
						}else{
							sHtml += '<th class="text-l" width="'+list_col[i].width+'">' + list_col[i].title + '</th>';
						}
						if(list_col[i].type&&list_col[i].type=='btn'){					
							var oTemp=list_col[i];
							oTemp.btnIndex=i+hasCheckbox;
							btns.push(oTemp);				
						}
					}
					sHtml += '</tr></thead><tbody>';	
										
					if(list.length){						
						for (var i = 0; i < list.length; i++) {
							var obj=list[i];
							var idTemp=obj[idField];
							dataCallback[idTemp]=obj;
							
							sHtml+='<tr data-id="'+idTemp+'">';
							
							if(hasCheckbox){
								sHtml +="<td class='text-left'><input class='datagrid-checkbox' type='checkbox' value='"+idTemp+"' /></td>";
							}
							for (var j = 0; j < len_col; j++) {
								var inobj=list_col[j];
								if(!inobj.xshidden){
									sHtml += '<td>' + dataFormat(obj[inobj.field],inobj) + '</td>';
								}else{
									sHtml += '<td class="hidden-xs">' + dataFormat(obj[inobj.field],inobj) + '</td>';
								}
											        			
							}							
						}
					}else{
						sHtml+='<tr><td colspan=100>没有数据</td></tr>';
					}					
					sHtml += '</tbody>';
					$el.html(sHtml);
					
					var $trlist=$el.find('tbody tr');
					
					setClick($trlist);
					names&&setNameData(list);
					btns.length&&setBtns($trlist);	
					checkboxSize=='multiple'&&setSelectAll();															
					options.onDblclick&&setDblclick($trlist);
					options.onSuccess&&options.onSuccess(list);
		        	hideLoading();
		        },
		        error:function(e){
		        	hideLoading();
		        	showErrorMessage(e);
		        }
		    });			
		}
		
		function setHidden(arr){
			var $trlist=$el.find('tbody tr');
			for(var i=0;i<arr.length;i++){
				var indextemp=arr[i];
				$trlist.each(function(){
					$(this).find('td').eq(indextemp).addClass('hidden-xs');
				});
			}
		}
		
		function setNameData(list){
			for(var i=0;i<names.length;i++){
        		var oNames=names[i],
        			fKeyField=oNames.data.fKeyField,     		        		
        			idFieldName=oNames.data.idField,
        			aNames=[];
        		
        		for(var j=0;j<list.length;j++){
        			aNames.push(list[j][fKeyField]);
        		}
        		var dataName={idField:idFieldName,nameField:oNames.data.nameField};          		
        		dataName[idFieldName]=$.unique(arrNotNull(aNames));
        		setDataByField(oNames.url,fKeyField,dataName);	    		            			    		            			    		            		
        	}						                                 
		}
		
		function arrNotNull(arr){
			var notNull=[];
			for(var i=0;i<arr.length;i++){
				if(arr[i]!=null){
					notNull.push(arr[i]);
				}
			}
			return notNull;
		}
		
		function setDataByField(nameUrl,fieldName,dataName){           	
        	$.ajax({
				url:nameUrl,
				data:dataName,
				dataType:"json",
				success:function(data){  					
					var oTemp={};
					for(var i=0;i<data.length;i++){		    									
						oTemp[data[i][dataName.idField]]=data[i];
					}	
					var index=fieldIndex[fieldName]+hasCheckbox;			    								
					$el.find('tbody tr').each(function(i){		    									
						var $target=$(this).children().eq(index);
						var val=$target.html();
						if(val){
							oTemp[val]&&$target.html(oTemp[val][dataName.nameField]);
						}else{
							$target.html('');
						}							
		        	});
				}
			});
        }
		
		function dataFormat(val,obj){
			var types={
					'img':function(val,obj){
						return "<img src='"+val+"' width='100%'>";
					},
					'date':function(val,obj){														
						if(val){
							var a=val.split(/[^0-9]/);
							var d=new Date(a[0],a[1]-1,a[2],a[3],a[4],a[5]);
							return $.date.format(d,obj.dateFormat||'yyyy-MM-dd hh:mm:ss');
						}
						return '';
					},
					'ellipsis':function(val,obj){
						if(val){							
							if(val.length>obj.len){
								return val.substring(0,obj.len)+'...';
							}else{
								return val;
							}							
						}
						return '';
					},
					'btn':function(val,obj){
						var btnList=obj.btns,sHtml='';							
						for(var i=0;i<btnList.length;i++){
							var obj=btnList[i];
							var btncolor=obj.color||"primary";							
							sHtml+='<button class="btn btn-'+btncolor+'">';
							if(obj.iconCls){
								sHtml+='<span class="glyphicon  ' + obj.iconCls+ '"></span>&nbsp;';
							}
							sHtml+=obj.text+'</button>&nbsp;';
						}
						return sHtml;
					}
			};
			if(obj.fnFormat){ 
				return obj.fnFormat(val);    		        				
			}
			if(obj.type){
				return types[obj.type](val,obj);
			}
			if(val==null){
				val='';
			}
			return val;
		}
		
		function setSelectAll(){
			var $control=$el.find('.btn-control');
			$control.show();
			$control.on('click', function() {
    			var b = this.checked;
    			$el.find(".datagrid-checkbox").each(function() {    		    				
    				this.checked = b;
    				var $parent=$(this).parents("tr")
    				if(b){
    					$parent.addClass('tr-active');
    				}else{
    					$parent.removeClass('tr-active');
    				}
    			});    			
    		});
		}
		
		function setClick($list){
			$list.on('click',function(event){ 				
				var $self=$(this);				
				if(hasCheckbox){
        			var checkboxEl=$self.find('.datagrid-checkbox')[0];    		        		
	        		if(event.target.nodeName!=='INPUT'){
	        			checkboxEl.checked=!checkboxEl.checked;
	        		} 
	        		if(checkboxEl.checked){	
	        			$self.addClass('tr-active');	        			
	        			if(checkboxSize=='single'){	        				
	        				$self.siblings('.tr-active').removeClass('tr-active').find('.datagrid-checkbox').prop("checked", false);
	        			}
	        		}else{
	        			$self.removeClass('tr-active');
	        		}
        		}									
				options.onClick&&options.onClick(dataCallback[$self.attr("data-id")]);
        	});
		}
		
		function setDblclick($list){
			$list.dblclick(function(){
				var $self=$(this);
				options.onDblclick(dataCallback[$self.attr("data-id")]);
			});
		}
		
		function setBtns($list){			
			$list.each(function(){
            	for(var j=0;j<btns.length;j++){
            		var btnObj=btns[j];
            		var list=$(this).find('td').eq(btnObj.btnIndex).find('button');
                	for(var i=0;i<list.length;i++){
                		var val=$(list[i]).parents('tr').attr('data-id');
                		$(list[i]).on('click',function(i,val){     					
        					return function(event){
        						event.stopPropagation();    						
        						btnObj.btns[i].handler(dataCallback[val]);
        					};
        				}(i,val));
                	}
            	}
            });
						
		}
	},
	'getSelected' : function(el, options) {		
		var ids = [];
		$(el).find(".datagrid-checkbox:checked").each(function() {
			var box = $(this);
			ids.push(box.val());
		});
		return ids;
	},
	'setData':function(el, options){
		var $list=$(el).find('tbody tr');
		if(options.hasOwnProperty('col')&&options.hasOwnProperty('row')){
			var $td=$list.eq(options.row).find('td').eq(options.col);						
			$td.html(options.handler($td.html()));
		}else if(options.hasOwnProperty('col')){
			$list.each(function(){
				var $td=$(this).find('td').eq(options.col);				
				$td.html(options.handler($td.html()));
			});
		}else if(options.hasOwnProperty('row')){
			$list.eq(options.row).find('td').each(function(){
				var $td=$(this);				
				$td.html(options.handler($td.html()));
			});
		}else{
			alert('请设置row或col');
		}		
	},
	'getData':function(el, options){
		var $list=$(el).find('tbody tr');
		if(options.hasOwnProperty('col')&&options.hasOwnProperty('row')){
			var $td=$list.eq(options.row).find('td').eq(options.col);
			return $td.html();			
		}else if(options.hasOwnProperty('col')){
			var arr=[];
			$list.each(function(){
				var $td=$(this).find('td').eq(options.col);	
				arr.push($td.html());				
			});
			return arr;
		}else if(options.hasOwnProperty('row')){
			var arr=[];
			$list.eq(options.row).find('td').each(function(){
				var $td=$(this);
				arr.push($td.html());				
			});
			return arr;
		}else{
			alert('请设置row或col');
		}
	}
};
bms.buttonMethods = {
	'init' : function(el, options) {
		var $el=$(el);		
		var list_btn = options.toolbar;
		if (list_btn) {			
			$el.addClass('cl pd-5 bg-1 bk-gray mt-20');
			var sHtml = '<span class="l">';
			for (var i = 0; i < list_btn.length; i++) {
				var obj = list_btn[i];
				var btncolor=obj.color||'primary'
				sHtml += '<a class="btn btn-' + btncolor+ ' radius">';	
				if(obj.iconCls){
					sHtml+='<span class="Hui-iconfont ' + obj.iconCls+ '"></span>&nbsp;';
				}
				sHtml+=obj.title + '</a>&nbsp;';
			}
			sHtml += '</span>';
			$el.html(sHtml);
			var btns=$el.find('.btn');
			for(var i=0;i<btns.size();i++){
				$(btns[i]).on('click',function(){
					return list_btn[i].handler;
				}(i));
			}
		}else{
			$el.hide();
		}
	}
};
bms.datagridDoMethods={
		'create':function(options){			
			layer_show(options.title, options.url, options.w||800,options.h||450);
		},
		'edit':function(options){
			var ids=options.ids;
			if(ids){
				if(ids.indexOf(',')<0){						
					var w=options.w||800;
					var h=options.h||450;
					var url=options.url;
					if(url.indexOf("?")>-1){
						var nIndex=url.indexOf('?');					
						layer_show(options.title, url.substring(0,nIndex)+'/'+ids+url.substring(nIndex), w,h);
					}else{
						layer_show(options.title, url+'/'+ids, w,h);
					}
					
				}else{
					layer.msg('只能选择一条记录！', {
						icon: 0,
						time: 1000
					});
				}
			}else{
				layer.msg('你还没有进行选择！', {
					icon: 0,
					time: 1000
				});
			}
		},
		'deletes':function(options){
			var ids=options.ids;
			var arr = ids.split(',');
			if(ids){
				layer.confirm('是否要删除选中的&nbsp;<span style="color:red;font-weight:blod;">'+arr.length+'</span>&nbsp;条记录？', function(index) {
					$.ajax({
						type: options.type||"POST",
						url: options.url,	
						data: $.serialize(arr),						
						contentType: "application/json; charset=utf-8",
						dataType: "json",					
						success: function(data) {
							layer.msg('已删除!', {
								icon: 1,
								time: 1000
							});
							setTimeout(function(){
								if(window.refreshTable){
									refreshTable();
								}else{
									window.location.reload();
								}
							},1000);
						},
						error:function(e){
							showErrorMessage(e);
						}
					});
				});
			}else{
				layer.msg('你还没有进行选择！', {
					icon: 0,
					time: 1000
				});
			}
		}
};
bms.formDoMethods={
		'load':function(options){
			showLoading();			
			$.ajax({
				type: options.type||"get",					
				url: options.url,
				cache: false,
				success: function(data) {
					options.fnCallBack&&options.fnCallBack(data);
					hideLoading();
				},
				error:function(e){				
					hideLoading();
					showErrorMessage(e);
				}
			});
		},
		'save':function(options){
			$.ajax({
				type : options.type||"post",
				data : options.data,
				url : options.url,				
				success : function(data) {
					layer.msg('添加成功!', {
						icon: 1,
						time: 1000
					});
					setTimeout(function(){
						parent.location.reload();						
						layer_close();
					},1000);												
				},
				error : function(e) {
					showErrorMessage(e);
				}
			});
		},
		'create':function(options){
			bms.formDoMethods['save'](options);
		},
		'edit':function(options){
			$.ajax({
				type: options.type||"post",
				data:options.data,
				url: options.url,				
				success: function(data) {											
					layer.msg('修改成功!', {
						icon: 1,
						time: 1000
					});
					setTimeout(function(){
						parent.location.reload();
						layer_close();
					},1000);						
				},
				error:function(e){
					showErrorMessage(e);
				}
			});
		},
		'update':function(options){
			bms.formDoMethods['edit'](options);
		}
};
bms.comboboxMethods={
		'init':function(el,options){
			var $el=$(el);
			$el.html('');
			$.ajax({
				type: options.type||"get",					
				url: options.url,
				cache: false,
				success: function(data) {						
					if(data){
						var sHtml='';
						for(var i=0;i<data.length;i++){
							sHtml+='<option value="'+data[i].value+'">'+data[i].text+'</option>';
						}					
						$el.html(sHtml);
					}
					options.fnCallBack&&options.fnCallBack(data,$el);
				},
				error:function(e){
					showErrorMessage(e);
				}
			});
		},
		'load':function(el,options){
			$.ajax({
				type: options.type||"get",					
				url: options.url,
				cache: false,
				success: function(data) {												
					if(data){
						var sHtml='';
						for(var i=0;i<data.length;i++){
							var obj=data[i];
							if(obj.value==options.val){
								sHtml+='<option value="'+obj.value+'" selected>'+obj.text+'</option>';
							}else{
								sHtml+='<option value="'+obj.value+'">'+obj.text+'</option>';
							}							
						}						
						$(el).html(sHtml);
					}
					options.fnCallBack&&options.fnCallBack(options.val,$(el));
				},
				error:function(e){
					showErrorMessage(e);
				}
			});
		}
};
(function($) {
	$.fn.bmsForm = function(name, options) {
		var el = this[0];
		return bms.formMethods[name](el, options);
	};
	$.fn.bmsDatagrid = function(name, options) {
		var el = this[0];
		return bms.datagridMethods[name](el, options);
	};
	$.fn.bmsButton = function(name, options) {
		var el = this[0];
		return bms.buttonMethods[name](el, options);
	};	
	$.bmsDatagridDo=function(name, options){
		return bms.datagridDoMethods[name](options);
	};
	$.bmsFormDo=function(name, options){
		return bms.formDoMethods[name](options);
	};
	$.fn.bmsCombobox=function(name, options){
		var el = this[0];
		return bms.comboboxMethods[name](el, options);		
	};
})(jQuery);
function layer_show(title,url,w,h){
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	layer.open({
		type: 2,
		area: [w+'px', h +'px'],
		fix: false,
		maxmin: true,
		shade:0.4,
		title: title,
		content: url
	});
}
function layer_close(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
function showLoading(){
	$(document.body).append('<div id="myLoading"></div>');
};
function hideLoading(){
	$('#myLoading').remove();
};
function showErrorMessage(e){
	alert("系统出现问题，请联系超级管理员");
};