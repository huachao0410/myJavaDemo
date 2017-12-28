function imgToBase64(input_file, get_data) {  
    /*input_file：文件按钮对象*/  
    /*get_data: 转换成功后执行的方法*/  
    if (typeof (FileReader) === 'undefined') {  
        alert("抱歉，你的浏览器不支持 FileReader，不能将图片转换为Base64，请使用现代浏览器操作！");  
    } else {  
        try {  
            /*图片转Base64 核心代码*/  
            var file = input_file;  
            //这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件  
            if (!/image\/\w+/.test(file.type)) {  
                alert("请确保文件为图像类型");  
                return false;  
            }  
            var reader = new FileReader();  
            reader.onload = function () {
            	//uploadToService(this.result,get_data(this.result));
            	
                get_data(this.result);  
            };  
            reader.readAsDataURL(file);  
        } catch (e){  
            alert('图片转Base64出错啦！' + e.toString());  
        } 
    }  
}

/**
 * 全局变量
 */
var fileid; // 确定所选文件

//上传组件
function initUpload(options,callback){
	var params={
		gallery:options['gallery']||'#gallery',
		galleryImg:options['galleryImg']||'#galleryImg',
		uploaderInput:options['uploaderInput']||'#uploaderInput0',
		uploaderFiles:options['uploaderFiles']||'#uploaderFiles0',
		isMultiUpload:options['isMultiUpload']||false,   //判断是否允许多图同时上传
		permitNum:options['permitNum']||1,//设置允许上传多少张图片
		imgId:options['imgId']||0,
	};
	
	var $gallery = $(params.gallery),
	$galleryImg = $(params.galleryImg),
	$uploaderInput = $(params.uploaderInput),
	$uploaderFiles = $(params.uploaderFiles);
	
	$uploaderInput.on("change", function(e) {
		var files = e.target.files;
		if(params.permitNum>1){
			var len=$uploaderFiles.children('li').length; //目前已上传图片数量
			var canLen=params.permitNum-len;  //还可上传图片数量
			//允许多图片上传
			for(var i = 0; i <canLen&&i<files.length; ++i) {
				//同一材料，多图片id设置,由材料ID+'.'+第几个图片
				var id=params.imgId+'.'+(len+i);
				uploadImg(id,files[i],function(data){
					var item=document.getElementById(data.id);
					var inputFile=document.getElementById(params.uploaderInput.substr(1));
					var obj={
						id:data.datas[0].attachmentId,
						attachName:data.datas[0] .attachmentName,
						attachType:data.datas[0].contentType,
						attachPath:data.datas[0].relativePath,
						title:inputFile.dataset.title,
						materialNo:inputFile.dataset.materialno,
						orderId:params.imgId,
						liId:data.id
					};
					callback&&callback(obj);
				});
			}
			
		}else{
			uploadImg(params.imgId,files[0],function(data){
				var inputFile=document.getElementById(params.uploaderInput.substr(1));
				inputFile.dataset.id=data.datas[0].attachmentId;
				inputFile.dataset.attachName=data.datas[0].attachmentName;
				inputFile.dataset.attachType=data.datas[0].contentType;
				inputFile.dataset.attachPath=data.datas[0].relativePath;//相对路径
				
				inputFile.dataset.state="new"; //判断图片是否有更新
				 				
				var obj={
					id:data.datas[0].attachmentId,
					attachName:data.datas[0] .attachmentName,
					attachType:data.datas[0].contentType,
					attachPath:data.datas[0].relativePath,
					title:inputFile.dataset.title,
					materialNo:inputFile.dataset.materialno,
					orderId:params.imgId,
					liId:data.id
				};
				callback&&callback(obj);
			});
			
		}
		
	});
	
	//图片预览
	$uploaderFiles.on("click", "li", function() {
		fileid = this.id;
		$galleryImg.html($(this).html());
		//$galleryImg.attr("style", this.getAttribute("style"));
		$gallery.fadeIn(100);
	});
	
	
	var uploadImg=function(imgId,file,callback){
		var src='';
		var url = window.URL || window.webkitURL || window.mozURL;
		if(url) {
			src = url.createObjectURL(file);
		} else {
			src = e.target.result;
		}
		//  文件上传到服务器
		uploadImgToService('file'+imgId,file,function(data){
			if('200'==data.code){
				
				/*var tmpl = '<li class="weui-uploader__file" style="background-image:url(#url#)" id="' + imgId + '"></li>';*/
				var tmpl = '<li class="weui-uploader__file" id="' + imgId + '">';
				tmpl+='<img src="#url#"/>';
				tmpl+='</li>';
				var len_old=$uploaderFiles.children('li').length; //目前已上传图片数量
				var canLen_old=params.permitNum-len_old;  //还可上传图片数量
				if(canLen_old>0){
					//展示上传图片到页面
					$uploaderFiles.append($(tmpl.replace('#url#', src)));
					
					//判断是否隐藏上传按钮
					if(params.permitNum<2){
						$uploaderInput.parent().css('display','none');
					}else{
						var len=$uploaderFiles.children('li').length; //目前已上传图片数量
						var canLen=params.permitNum-len;  //还可上传图片数量
						if(canLen<1){
							$uploaderInput.parent().css('display','none');
						}else{
							$uploaderInput.val("");//作为必须上传多份材料的校验标准 为空则校验必填不通过
						}
					}
					
					//回调
					data.id=imgId;
					callback(data);
				}
			}else{
				if(params.permitNum<2){
					//清空文件的值
					$uploaderFiles.val("");
					//显示上传按钮
					$uploaderInput.parent().css('display','');
				}else{
				}
			}
		},function(e){
			if(params.permitNum<2){
				//清空文件的值
				$uploaderFiles.val("");
				//显示上传按钮
				$uploaderInput.parent().css('display','');
			}else{
			}
		});
	};
	/*//上传图片到服务器
	var uploadImgToService=function(filename,file,callback,err){
		var formData = new FormData();
		formData.append("file", file);
		formData.append("name", filename);
		$.ajax({
			url: _upLoad,
			type: 'post',
			data: formData,
			contentType: false,
			processData: false,
			success: function(data) {
				callback(data);
			},
			error: function(error) {
				if(err){
					err(e);
				}else{
					console.log(JSON.stringify(error));
				}
			}
		});
	};*/
}

//图片操作事件初始化
function operateImgInit(options,callback){
	var params={
		gallery:options['gallery']||'#gallery',
		galleryImg:options['galleryImg']||'#galleryImg',
		uploaderInput:options['uploaderInput']||'#uploaderInput',
		uploaderFiles:options['uploaderFiles']||'#uploaderFiles',
		isMultiUpload:options['isMultiUpload']||false,   //判断是否允许多图同时上传
	};
	
	var $gallery = $(params.gallery),
		$galleryImg = $(params.galleryImg);
	
	//图片预览关闭
	$gallery.on("click", function() {
		$gallery.fadeOut(100);
	});
	//图片删除	
	$(".weui-gallery__del").on('click', function() {
		if(params.permitNum<2){
			var idObject = document.getElementById(fileid); 
			if (idObject != null){
				var materialId=idObject.dataset.fileid;
				callback&&callback(materialId,function(){
					$("li[id=" + fileid + "]").remove();
					//清空文件的值
					$(params.uploaderInput+fileid).val("");
					//显示上传按钮
					$(params.uploaderInput+fileid).parent().css('display','');
				});
			}else if(callback==null){
				$("li[id=" + fileid + "]").remove();
				//清空文件的值
				$(params.uploaderInput+fileid).val("");
				//显示上传按钮
				$(params.uploaderInput+fileid).parent().css('display','');
			}
		}else{
			//删除对应ul列表中的li项,并重置id
			var idObject = document.getElementById(fileid); 
			if (idObject != null){
				var parentObject=idObject.parentNode;
				var materialId=idObject.dataset.fileid;
				callback&&callback(materialId,function(){
					//移除对应li
			    	parentObject.removeChild(idObject); 
			    	//并重置每个li的id
			    	for(var i=0;i<parentObject.childNodes.length;i++){
				    	var id=parentObject.childNodes[i].id;
				    	parentObject.childNodes[i].id=id.split(".")[0]+"."+i;
				    }
			    	$(params.uploaderInput+fileid.split('.')[0]).val("");
					//显示上传按钮
					$(params.uploaderInput+fileid.split('.')[0]).parent().css('display','');
				});
			}
		}
		$gallery.fadeOut(100);
	});
}


//上传图片到服务器
function uploadImgToService(filename,file,callback,err){
	var formData = new FormData();
	formData.append("file", file);
	formData.append("name", filename);
	$.ajax({
		url: _upLoad,
		type: 'post',
		data: formData,
		contentType: false,
		processData: false,
		success: function(data) {
			callback(data);
		},
		error: function(error) {
			if(err){
				err(e);
			}else{
				console.log(JSON.stringify(error));
			}
		}
	});
}

/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData){
    
    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte
    
    //处理异常,将ascii码小于0的转换为大于0
    var ab = new ArrayBuffer(bytes.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < bytes.length; i++) {
        ia[i] = bytes.charCodeAt(i);
    }

    return new Blob( [ab] , {type : 'image/png'});
}