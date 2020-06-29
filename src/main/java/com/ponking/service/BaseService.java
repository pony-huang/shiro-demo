package com.ponking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/26--20:20
 **/
public interface BaseService<T,S> extends IService<S> {
    /**
     * {x}->{x}Dto
     * @param source
     * @return
     */
    T convertTo(@NonNull S source);

    /**
     * {x}List->{x}DtoList
     * @param sources
     * @return
     */
    List<T> convertTo(@NonNull List<S> sources);
}
