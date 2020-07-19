package com.ponking.model.base;

/**
 * @author Peng
 * @date 2020/6/26--20:46
 **/
public interface Converter<T> {
    /**
     * Params(this) -> Entity
     * @return
     */
    T convertTo();
}
