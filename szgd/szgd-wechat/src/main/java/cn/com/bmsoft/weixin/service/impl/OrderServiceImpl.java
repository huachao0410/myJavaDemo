package cn.com.bmsoft.weixin.service.impl;

import cn.com.bmsoft.weixin.service.IOrderService;
import cn.com.bmsoft.weixin.util.HttpKit;
import org.springframework.stereotype.Service;

/**
 * Created by gzh on 2017/8/22.
 * 微信订单服务接口
 */
@Service
public class OrderServiceImpl implements IOrderService {
	private static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	private static final String ORDER_CLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	private static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static final String PAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	private static final String PAY_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	private static final String BATCH_COMMENT_URL = "https://api.mch.weixin.qq.com/billcommentsp/batchquerycomment";

	/**
	 * 统一下单
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String unifiedOrder(String params) throws Exception {

		String result = HttpKit.post(UNIFIEDORDER_URL, params);
		return result;
	}

	/**
	 * 订单查询
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String orderQuery(String params) throws Exception {
		String result = HttpKit.post(ORDER_QUERY_URL, params);
		return result;
	}

	/**
	 * 订单关闭
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String closeOrder(String params) throws Exception {
		String result = HttpKit.post(ORDER_CLOSE_URL, params);
		return result;
	}

	/**
	 * 申请退款
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String payRefund(String params) throws Exception {
		String result = HttpKit.post(PAY_REFUND_URL, params);
		return result;
	}

	/**
	 * 查询退款
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String payRefundquery(String params) throws Exception {
		String result = HttpKit.post(PAY_REFUND_QUERY_URL, params);
		return result;
	}

	/**
	 * 拉取订单评价数据
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String batchQueryComment(String params) throws Exception {
		String result = HttpKit.post(BATCH_COMMENT_URL, params);
		return result;
	}
}
