//上传图片转base64
function imgToBase64(input_file, get_data) {  
    /*input_file：文件按钮对象*/  
    /*get_data: 转换成功后执行的方法*/  
    if (typeof (FileReader) === 'undefined') {  
        alert("抱歉，你的浏览器不支持 FileReader，不能将图片转换为Base64，请使用现代浏览器操作！");  
    } else {  
        try {  
            /*图片转Base64 核心代码*/  
            var file = input_file.target.files[0];  
            //这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件  
            if (!/image\/\w+/.test(file.type)) {  
                alert("请确保文件为图像类型");  
                return false;  
            }  
            var reader = new FileReader();  
            reader.onload = function () {
            	//uploadToService(this.result,get_data(this.result));
            	
                get_data(this.result);  
            }  
            reader.readAsDataURL(file);  
        } catch (e){  
            alert('图片转Base64出错啦！' + e.toString())  
        } 
    }  
}

/**
 * 全局变量
 */
var a = []; //用于保存上传文件ID
var fileid; // 确定所选文件

//上传组件
function initUpload(options){
	var params={
		gallery:options['gallery']||'#gallery',
		galleryImg:options['galleryImg']||'#galleryImg',
		uploaderInput:options['uploaderInput']||'#uploaderInput0',
		uploaderFiles:options['uploaderFiles']||'#uploaderFiles0',
		isMultiUpload:options['isMultiUpload']||false,   //判断是否允许多图同时上传
		imgId:options['imgId']||1,
	}
	
	var $gallery = $(params.gallery),
	$galleryImg = $(params.galleryImg),
	$uploaderInput = $(params.uploaderInput),
	$uploaderFiles = $(params.uploaderFiles);
	
	$uploaderInput.on("change", function(e) {
		var url = window.URL || window.webkitURL || window.mozURL,
			files = e.target.files;
		
		if(params.isMultiUpload){
			//允许多图片上传
			for(var i = 0, len = files.length; i < len; ++i) {
				uploadImg(params.imgId,url,files[i],function(data){
					chooseFile(data.datas["0"].attachmentId);
				});
				params.imgId++;
			}
		}else{
			uploadImg(params.imgId,url,files[0],function(data){
				var inputFile=document.getElementById(params.uploaderInput.substr(1));
				inputFile.dataset.id=data.datas[0].attachmentId;
				inputFile.dataset.attachName=data.datas[0].attachmentName;
				inputFile.dataset.attachType=data.datas[0].contentType;
				inputFile.dataset.attachPath=data.datas[0].relativePath;//相对路径
			});	
		}
		
	});
	
	//图片预览
	$uploaderFiles.on("click", "li", function() {
		fileid = this.id;
		$galleryImg.attr("style", this.getAttribute("style"));
		$gallery.fadeIn(100);
	});
	
	
	var uploadImg=function(imgId,url,file,callback){
		var src='';
		
		if(url) {
			src = url.createObjectURL(file);
		} else {
			src = e.target.result;
		}
		
		if(!params.isMultiUpload){
			//隐藏上传按钮
			$uploaderInput.parent().css('display','none');
		}
		
		 $.showLoading();
		
		//  文件上传到服务器
		uploadImgToService('file'+imgId,file,function(data){
			if('200'==data.code){
				console.log(JSON.stringify(data));
				var tmpl = '<li class="weui-uploader__file" style="background-image:url(#url#)" id="' + params.imgId + '"></li>';
				$.hideLoading();
				//展示上传图片到页面
				$uploaderInput.blur();
				$uploaderFiles.append($(tmpl.replace('#url#', src)));
				callback(data);
			}else{
				if(!params.isMultiUpload){
					//清空文件的值
					$uploaderFiles.val("");
					//显示上传按钮
					$uploaderInput.parent().css('display','');
				}else{
					a.splice(fileid, 1); //TODO 上传文件id数组修改
				}
			}
		},function(e){
			if(!params.isMultiUpload){
				//清空文件的值
				$uploaderFiles.val("");
				//显示上传按钮
				$uploaderInput.parent().css('display','');
			}else{
				a.splice(fileid, 1); //TODO 上传文件id数组修改
			}
		});
	}
	
	//上传图片到服务器
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
	}
	
	//选择要上传的文件
	var chooseFile=function(data) {
		a.push(data);
	}
}

function getArrays(){
	return a;
}

//图片操作事件初始化
function operateImgInit(options){
	var params={
		gallery:options['gallery']||'#gallery',
		galleryImg:options['galleryImg']||'#galleryImg',
		uploaderInput:options['uploaderInput']||'#uploaderInput',
		uploaderFiles:options['uploaderFiles']||'#uploaderFiles',
		isMultiUpload:options['isMultiUpload']||false,   //判断是否允许多图同时上传
	}
	
	var $gallery = $(params.gallery),
		$galleryImg = $(params.galleryImg);
	
	//图片预览关闭
	$gallery.on("click", function() {
		$gallery.fadeOut(100);
	});
	//图片删除	
	$(".weui-gallery__del").on('click', function() {
		
		if(!params.isMultiUpload){
			//清空文件的值
			$(params.uploaderInput+fileid).val("");
			//显示上传按钮
			$(params.uploaderInput+fileid).parent().css('display','');
		}else{
			//console.log(JSON.stringify(a));
			var li=$('#uploaderFiles0 li');
			for(var i=0;i<li.length;i++){
				if(fileid==li[i].id){
					a.splice(i, 1); //TODO 上传文件id数组修改
					break;
				}
			}
			
			
			console.log(JSON.stringify(a));
		}
		$("li[id=" + fileid + "]").remove();
		$gallery.fadeOut(100);
	});
}
