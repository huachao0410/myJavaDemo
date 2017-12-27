package cn.com.bmsoft.base.common.service;

/**
 *
 * @author work
 */
public interface AsyncService {

    void call(Runnable runnable);
}
