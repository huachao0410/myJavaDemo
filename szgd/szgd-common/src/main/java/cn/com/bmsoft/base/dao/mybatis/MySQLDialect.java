package cn.com.bmsoft.base.dao.mybatis;

import org.springframework.stereotype.Service;

/**
 * @author work
 */
@Service
public class MySQLDialect implements Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
        pagingSelect.append("SELECT * FROM (");
        pagingSelect.append(sql);
        pagingSelect.append(" ) ROW_ ");
        pagingSelect.append(" LIMIT ").append(offset).append(" , ").append(limit);
        return pagingSelect.toString();
    }
}
