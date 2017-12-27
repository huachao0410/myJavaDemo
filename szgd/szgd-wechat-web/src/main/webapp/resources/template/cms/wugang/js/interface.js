/*证照库*/
var _cardCenterurl = "http://119.29.55.145:8080/resource_cardProvider/services/cardCenter";
/*统计有效、即将过期和无效证照个数*/
var _countValidCards = _cardCenterurl + "/countValidCards";
/*证照列表*/
var _listCard = _cardCenterurl + "/listCard";
/*证照详情*/
var _detailCard = _cardCenterurl + "/detailCard";
/*手机短信验证码验证*/
var messageServices = "http://139.199.203.136:8056/smsProvider/services/smsCenter";
/*短信验证码发送*/
var _sendMessage = messageServices + "/smsSender";
/*验证码验证*/
var _verifyCode = messageServices + "/validateCaptcha";
/*判断是否需要短信验证码*/
var _isSkipCaptcha = messageServices + "/skipCaptcha";
var sfz_cardid="pIH7UwxmCVO98gH__2l04TJAcXxo";
var hkb_cardid="pIH7Uw7QuW_QYFEgEim8-EFcIeQo";
/*查询卡券状态*/
var _queryCardStatus = "http://msjwt.szga.gov.cn/bmswx/mobile/member/getUserCardStatus"
/*提交卡券状态*/
var _sbmitCardStatus = "http://msjwt.szga.gov.cn/bmswx/mobile/member/addUserCard";

/*服务器地址*/
var _serviceUrl = 'http://msjwwx.szga.gov.cn';
/*文件上传地址*/
var _fileUrl = 'http://msjwwx.szga.gov.cn:9094';
/*短信发送及验证地址*/
var _smsUrl = 'http://msjwwx.szga.gov.cn:9090';

/*录入线索*/
var _createClue = _serviceUrl + '/bmswx/mobile/yb/createClue';
/*举报查询*/
var _getByPreNumberUrl = _serviceUrl + '/bmswx/mobile/yb/getByPreNumber';
/*判断资料是否齐全*/
var _getRepterByOpenidUrl = _serviceUrl + "/bmswx/mobile/yb/getRepterByOpenid";
/*通过openid补充资料*/
var _supplyInfoUrl = _serviceUrl + "/bmswx/mobile/yb/batchReptRegister";
/*资料补录*/
var _createReporterUrl = _serviceUrl + '/bmswx/mobile/yb/createReporter';
/*放弃奖金*/
var _dropBountyUrl = _serviceUrl + '/bmswx/mobile/yb/dropBounty';
/*举报指引*/
var _guideUrl = _serviceUrl + "/bmswx/mobile/ybguide/guidelist";
/*获取手机验证码*/
var _getMobilecodeUrl = _smsUrl + "/szgasms/v2/singleSender";
/*验证手机验证码*/
var _identifyCodeUrl = _smsUrl + "/szgasms/v2/identifyCode";
/*文件上传*/
var _upLoad = _fileUrl + '/portalProvider/services/file/upload?belong=yjjb';
/*下载附件*/
var _downloadattachmentUrl= _fileUrl + '/portalProvider/services/file/download/';
/*获取openid*/
var _getOpenidUrl = _serviceUrl + "/bmswx/mobile/common/openid";
/*获取举报区域*/
var _getReportareaUrl = _serviceUrl + "/bmswx/mobile/yb/combobox/SZ_AREA";
/*获取与被举报人关系*/
var _getReprelationUrl = _serviceUrl + "/bmswx/mobile/yb/combobox/YB_REPRELATION";
/*流水号找回*/
var _resendBack = _serviceUrl + '/bmswx/mobile/yb/resendBack';



