/*信息发布*/

//var defaultService='http://http://gzh.thstp.com/cms/rest';
var defaultService='http://10.194.186.18:8080/cms/rest';
/*菜单栏*/
var _channel=defaultService+'/channel/list?';

/*注册*/
var _register=defaultService+'/user/register';

/*登录*/
var _login=defaultService+'/user/login';

/*发送验证码*/
var _sendCode=defaultService+'/message/sms/send?';

/*校验验证码*/
var _validateCode=defaultService+'/message/sms/validate?';

/*绑定手机号*/
var _bindPhone=defaultService+'/user/bind/phone';

/*绑定身份证*/
var _bindIdcard=defaultService+'/user/bind/idcard';

/*修改密码*/
var _bindPassword=defaultService+'/user/modify/password';

/*新闻列表*/
var _newsList=defaultService+'/content/page?';

/*新闻详情*/
var _newsDetail=defaultService+'/content/load/';

/*新闻详情点赞*/
var _newsDetailUps=defaultService+'/content/ups';

/*新闻详情收藏*/
var _newsDetailCollection=defaultService+'/content/collection';

/*评论点赞*/
var _ups=defaultService+'/comment/ups';

/*评论踩*/
var _downs=defaultService+'/comment/downs';

/*评论踩和赞总数*/
var _comment=defaultService+'/comment/page?';

/*发布评论*/
var _publish=defaultService+'/comment/publish?';

/*查询个人收藏*/
var _collection=defaultService+'/content/collection/page?';

/*文件上传地址*/
var _fileUrl = 'http://msjwwx.szga.gov.cn:9094';
/*文件上传*/
//var _upLoad = _fileUrl + '/portalProvider/services/file/upload?belong=yjjb';
/*下载附件*/
var _downloadattachmentUrl= _fileUrl + '/portalProvider/services/file/download/';

//zhengmian
var _upLoad0="http://bmscms.qtkids.cn:10066/cms/rest/tengxun/youtu/idcareocr/0"

//fanmian
var _upLoad1=defaultService+"/youtu/IdCardOcr/1"