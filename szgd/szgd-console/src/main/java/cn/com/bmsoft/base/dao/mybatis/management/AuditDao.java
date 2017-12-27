package cn.com.bmsoft.base.dao.mybatis.management;

import org.springframework.stereotype.Repository;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.base.dao.face.management.IAuditDao;
import cn.com.bmsoft.base.domain.management.Audit;

/**
 * T_AUDIT( ) DAO实现
 *
 * @作者 黄迪文
 * @创建时间 2012-12-11 14:58:32
 */
@Repository
public class AuditDao extends Dao<Audit> implements IAuditDao {
}