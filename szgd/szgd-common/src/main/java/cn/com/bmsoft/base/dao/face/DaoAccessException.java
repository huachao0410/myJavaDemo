package cn.com.bmsoft.base.dao.face;

/**
 * Unchecked的Dao异常，所有Dao默认抛出此异常
 *
 * @author huangdiwen
 */
public class DaoAccessException extends RuntimeException {

    public DaoAccessException() {
    }

    public DaoAccessException(String string) {
        super(string);
    }

    public DaoAccessException(Throwable thrwbl) {
        super(thrwbl);
    }

    public DaoAccessException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
}
