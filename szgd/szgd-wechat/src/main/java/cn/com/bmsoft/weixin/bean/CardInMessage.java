package cn.com.bmsoft.weixin.bean;

import cn.com.bmsoft.weixin.inf.MsgTypes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡券输入消息
 */
public class CardInMessage extends InMessage {

	//卡券
	private String CardId;//卡券ID
	private String RefuseReason;//审核不通过原因
	private Integer IsReturnBack;//是否转赠退回，0代表不是，1代表是。
	private Integer IsChatRoom;//是否是群转赠
	private Integer IsGiveByFriend;//是否为转赠领取，1代表是，0代表否。
	private String UserCardCode;
	private String FriendUserName;//当IsGiveByFriend为1时填入的字段，表示发起转赠用户的openid
	private Long OuterId;
	private String OldUserCardCode;//为保证安全，微信会在转赠发生后变更该卡券的code号，该字段表示转赠前的code。
	private String OuterStr;//开发者发起核销时传入的自定义参数，用于进行核销渠道统计
	private Integer IsRestoreMemberCard;
	private Integer IsRecommendByFriend;
	private String ConsumeSource;//核销来源。支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
	private String LocationName;
	private String StaffOpenId;
	private String VerifyCode;//自助核销时，用户输入的验证码
	private String RemarkAmount;//自助核销时，用户输入的备注金额
	private String TransId;//微信支付交易订单号（只有使用买单功能核销的卡券才会出现）
	private Long LocationId;//门店ID，当前卡券核销的门店ID（只有通过卡券商户助手和买单核销时才会出现）
	private String Fee;//实付金额，单位为分
	private String OriginalFee;//应付金额，单位为分
	private Long ModifyBonus;//变动的积分值
	private Long ModifyBalance;//变动的余额值
	private String Detail;//报警详细信息

	public String getCardId() {
		return CardId;
	}

	public void setCardId(String cardId) {
		CardId = cardId;
	}

	public String getConsumeSource() {
		return ConsumeSource;
	}

	public void setConsumeSource(String consumeSource) {
		ConsumeSource = consumeSource;
	}

	public String getDetail() {
		return Detail;
	}

	public void setDetail(String detail) {
		Detail = detail;
	}

	public String getFee() {
		return Fee;
	}

	public void setFee(String fee) {
		Fee = fee;
	}

	public String getFriendUserName() {
		return FriendUserName;
	}

	public void setFriendUserName(String friendUserName) {
		FriendUserName = friendUserName;
	}

	public Integer getIsChatRoom() {
		return IsChatRoom;
	}

	public void setIsChatRoom(Integer isChatRoom) {
		IsChatRoom = isChatRoom;
	}

	public Integer getIsGiveByFriend() {
		return IsGiveByFriend;
	}

	public void setIsGiveByFriend(Integer isGiveByFriend) {
		IsGiveByFriend = isGiveByFriend;
	}

	public Integer getIsRecommendByFriend() {
		return IsRecommendByFriend;
	}

	public void setIsRecommendByFriend(Integer isRecommendByFriend) {
		IsRecommendByFriend = isRecommendByFriend;
	}

	public Integer getIsRestoreMemberCard() {
		return IsRestoreMemberCard;
	}

	public void setIsRestoreMemberCard(Integer isRestoreMemberCard) {
		IsRestoreMemberCard = isRestoreMemberCard;
	}

	public Integer getIsReturnBack() {
		return IsReturnBack;
	}

	public void setIsReturnBack(Integer isReturnBack) {
		IsReturnBack = isReturnBack;
	}

	public Long getLocationId() {
		return LocationId;
	}

	public void setLocationId(Long locationId) {
		LocationId = locationId;
	}

	public String getLocationName() {
		return LocationName;
	}

	public void setLocationName(String locationName) {
		LocationName = locationName;
	}

	public Long getModifyBalance() {
		return ModifyBalance;
	}

	public void setModifyBalance(Long modifyBalance) {
		ModifyBalance = modifyBalance;
	}

	public Long getModifyBonus() {
		return ModifyBonus;
	}

	public void setModifyBonus(Long modifyBonus) {
		ModifyBonus = modifyBonus;
	}

	public String getOldUserCardCode() {
		return OldUserCardCode;
	}

	public void setOldUserCardCode(String oldUserCardCode) {
		OldUserCardCode = oldUserCardCode;
	}

	public String getOriginalFee() {
		return OriginalFee;
	}

	public void setOriginalFee(String originalFee) {
		OriginalFee = originalFee;
	}

	public Long getOuterId() {
		return OuterId;
	}

	public void setOuterId(Long outerId) {
		OuterId = outerId;
	}

	public String getOuterStr() {
		return OuterStr;
	}

	public void setOuterStr(String outerStr) {
		OuterStr = outerStr;
	}

	public String getRefuseReason() {
		return RefuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		RefuseReason = refuseReason;
	}

	public String getRemarkAmount() {
		return RemarkAmount;
	}

	public void setRemarkAmount(String remarkAmount) {
		RemarkAmount = remarkAmount;
	}

	public String getStaffOpenId() {
		return StaffOpenId;
	}

	public void setStaffOpenId(String staffOpenId) {
		StaffOpenId = staffOpenId;
	}

	public String getTransId() {
		return TransId;
	}

	public void setTransId(String transId) {
		TransId = transId;
	}

	public String getUserCardCode() {
		return UserCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		UserCardCode = userCardCode;
	}

	public String getVerifyCode() {
		return VerifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		VerifyCode = verifyCode;
	}
}
