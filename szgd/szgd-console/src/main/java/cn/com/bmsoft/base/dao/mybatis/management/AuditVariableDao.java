package cn.com.bmsoft.base.dao.mybatis.management;

import cn.com.bmsoft.base.dao.face.management.IAuditVariableDao;
import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.base.domain.management.AuditVariable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author work
 */
@Repository
public class AuditVariableDao extends Dao<AuditVariable> implements IAuditVariableDao {
}
