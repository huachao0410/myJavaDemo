package cn.com.bmsoft.weixin.service;

/**
 * Created by gzh on 2017/8/22.
 */
public interface IOrderService {

	/**
	 * 统一下单
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String unifiedOrder(String params) throws Exception;

	/**
	 * 订单查询
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String orderQuery(String params) throws Exception;

	/**
	 * 订单关闭
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String closeOrder(String params) throws Exception;


	/**
	 * 申请退款
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String payRefund(String params) throws Exception;

	/**
	 * 查询退款
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String payRefundquery(String params) throws Exception;

	/**
	 * 拉取订单评价数据
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String batchQueryComment(String params) throws Exception;
}
