
var WritingPad = function () {

    var current = null;

    $(function () {

        initSignature();
       
        $(document).on("click", "#myClose,.close", null, function () {
            $('#mymodal').modal('hide');
            $("#mymodal").remove();

        });
       
        $(document).on("click", "#mySave", null, function () {
            
            var myImg = $('#myImg').empty();
            var dataUrl = $('.js-signature').jqSignature('getDataURL');
            var img = $('<img>').attr('src', dataUrl);
            //$(myImg).append($('<p>').text("图片保存在这里"));
            $(myImg).append(img);
        });

        $(document).on("click", "#myEmpty", null, function () {
            $('.js-signature').jqSignature('clearCanvas');

        });

        $(document).on("click", "#myBackColor", null, function () {

            $('#colorpanel').css('left', '95px').css('top', '45px').css("display", "block").fadeIn();
            //$("canvas").css("background", "#EEEEEE");
            $("#btnSave").data("sender", "#myBackColor");
        });

        $(document).on("click", "#myColor", null, function () {
            $('#colorpanel').css('left', '205px').css('top', '45px').css("display", "block").fadeIn();
            $("#btnSave").data("sender", "#myColor");
        });

        $(document).on("mouseover", "#myTable", null, function () {

            if ((event.srcElement.tagName == "TD") && (current != event.srcElement)) {
                if (current != null) { current.style.backgroundColor = current._background }
                event.srcElement._background = event.srcElement.style.backgroundColor;
                current = event.srcElement;
            }

        });

        $(document).on("mouseout", "#myTable", null, function () {
            if (current != null) current.style.backgroundColor = current._background
        });

        $(document).on("click", "#myTable", null, function () {

            if (event.srcElement.tagName == "TD") {
                var color = event.srcElement._background;
                if (color) {
                    $("input[name=DisColor]").css("background-color", color);
                    var strArr = color.substring(4, color.length - 1).split(',');
                    var num = showRGB(strArr);
                    $("input[name=HexColor]").val(num);
                }
            }

        });

        $(document).on("click", "#btnSave", null, function () {

            $('#colorpanel').css("display", "none");
            var typeData = $("#btnSave").data("sender");
            var HexColor = $("input[name=HexColor]").val();
            var data = $(".js-signature").data();
            if (typeData == "#myColor") {
                data["plugin_jqSignature"]["settings"]["lineColor"] = HexColor;
                $('.js-signature').jqSignature('reLoadData');
            }
            if (typeData == "#myBackColor") {

                data["plugin_jqSignature"]["settings"]["background"] = HexColor;
                $('.js-signature').jqSignature('reLoadData');
            }
        });

        $("#mymodal").on('hide.bs.modal', function () {
            $("#colorpanel").remove();
            $("#mymodal").remove();
            $("#myTable").remove();
        });

    });

    function initHtml() {

        var html = '<div class="modal" id="mymodal">' +
            '<div class="modal-dialog">' +
                '<div class="modal-content">' +
                    '<div class="modal-header">' +
                        ''  +
                    '</div>' +
                    '<div class="modal-body">' +
                        '<div class="js-signature" id="mySignature">' +
                         '</div>' +
                         '<div>' +
                         '<button type="button" class="btn btn-default" id="myEmpty">清空面板</button>' +
                         '<button type="button" class="btn btn-default" id="myBackColor">设置背景颜色</button>' +
                         //'<div style="position:absolute;relative">' +
                         '<button type="button" class="btn btn-default" id="myColor">设置字体颜色</button>' +
                         '<div id="colorpanel" style="position:absolute;z-index:99;display:none"></div>' +
                         //'</div>'+
                         '</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>';

        $('body').append(html);
    }

    

    function initSignature() {
        //debugger;
        if (window.requestAnimFrame) {
            var signature = $("#mySignature");
            signature.jqSignature({ width: 300, height: 200, border: '0px solid #eeeeee', background: '#ffffff', lineColor: '#000000', lineWidth: 2, autoFit: true });
        } else {
            alert("请加载jq-signature.js");
            return;
        }
    }

    function showRGB(arr) {
        hexcode = "#";
        for (x = 0; x < 3; x++) {
            var n = arr[x];
            if (n == "") n = "0";
            if (parseInt(n) != n)
                return alert("RGB颜色值不是数字！");
            if (n > 255)
                return alert("RGB颜色数字必须在0-255之间！");
            var c = "0123456789ABCDEF", b = "", a = n % 16;
            b = c.substr(a, 1); a = (n - a) / 16;
            hexcode += c.substr(a, 1) + b
        }
        return hexcode;
    }

    function init() {


    }

    return {
        init: function () {
            init();
        }
    };
}