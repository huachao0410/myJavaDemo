/**
 * 单个图片上传实现，比较粗糙.
 *
 * 例子：
 *
 <div class="row cl">
 <label class="form-label col-xs-3">标题图：</label>
 <div class="formControls col-xs-8">
 <input type="text" class="input-text" value="" placeholder="" name="titleImg" id="titleImg">
 </div>
 </div>
 <div class="row cl">
 <label class="form-label col-xs-3"></label>
 <div class="formControls col-xs-8">
 <div class="uploader-thum-container">
 <div id="fileList" class="uploader-list"></div>
 <div id="filePicker">选择上传的图片</div>
 </div>
 </div>
 </div>
 *
 */

//---------------------- 文件上传 开始 -----------------------

var progress={timer:null,cur:0,one:0};

/**
 * 初始化上传组件
 */
function initUploadSingleImg(target, contextPath) {
    $list = $("#fileList"),
        $btn = $("#btn-star"),
        state = "pending",
        uploader;

    var uploader = WebUploader.create({
        auto: true,
        swf: contextPath + '/lib/webuploader/0.1.5/Uploader.swf',
        // 文件接收服务端。
        server: contextPath + '/rest/file/upload',
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',
        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });
    uploader.on( 'fileQueued', function( file ) {
        var $li = $(
            '<div id="' + file.id + '" class="item">' +
            '<div class="info">' + file.name + '</div>' +
            '<p class="state">等待上传...</p>'+
            '</div>'
        );
        $list.append( $li );
    });
    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress-box .sr-only');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress-box"><span class="progress-bar "><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
        }
        $li.find(".state").text("上传中");

        if(!progress.one){
            startTimer(file.size);
        }
        //$percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file,result ) {
        if(result.code == '200' ) {
            var url=result.datas[0];
            var index=url.lastIndexOf('/');
            var id=url.substring(index+1);

            $('#' + target).val(url);
            $('#' + target).attr('data-id',id);
//                $('#'+file.id).addClass('upload-state-success').find(".state").text("已上传");
            $('#'+file.id).addClass('upload-state-success').find(".state").html("<img src='" + url + "' class='upload-img'>");
        } else {
            $( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传失败");
        }
    });

    // 文件上传失败，显示上传出错。
    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).addClass('upload-state-error').find(".state").text("上传出错");
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        clearTimer();
        $( '#'+file.id ).find('.progress-box').fadeOut();
    });
    uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on('click', function () {
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
}

function startTimer(fileSize){
    progress.one=parseInt(100*1024*3/fileSize*1000);
    progress.timer=setInterval(function(){
        progress.cur=progress.cur*1+progress.one;
        var $el=$('.progress-box').find('.sr-only');
        if(progress.cur>=100000){
            $el.css( 'width','99%' );
            clearInterval(progress.timer);
        }else{
            $el.css( 'width',parseInt(progress.cur/1000)+'%' );
        }
    },20);
}

function clearTimer(){
    progress.one=0;
}

//---------------------- 文件上传 结束 -----------------------


