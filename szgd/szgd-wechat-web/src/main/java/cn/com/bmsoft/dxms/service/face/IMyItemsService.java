package cn.com.bmsoft.dxms.service.face;

import java.util.List;
import java.util.Map;
import cn.com.bmsoft.dxms.domain.Myitems;

/**
 * T_MYITEMS(我的事项) 服务接口
 */
public interface IMyItemsService {
    
    /**
     * 模糊统计符合查询条件的记录总数
     *
     * @param queryParams 查询参数
     * @return 记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     *
     * @param start 记录起始位置
     * @param limit 记录条数
     * @param queryParams 查询参数
     * @return 记录列表
     */
    List<Myitems> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     *
     * @param queryParams 查询参数
     * @return 记录列表
     */
    List<Myitems> find(Map<String, Object> queryParams);

    /**
     * 精确生成将要插入的记录的序号
     *
     * @param queryParams 查询参数
     * @return 序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    Myitems load(int id);

    /**
     * 插入记录
     *
     * @param domainInstance Log实例
     * @return 被插入的记录标识
     */
    int insert(Myitems domainInstance);

    /**
     * 修改记录
     *
     * @param domainInstance Log实例
     * @return 被修改的记录数
     */
    int update(Myitems domainInstance);

	/**
     * 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录数
     */
    int deletes(int[] ids);
}
