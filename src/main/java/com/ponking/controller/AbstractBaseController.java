package com.ponking.controller;


import com.ponking.model.params.BaseQuery;
import com.ponking.model.result.Result;

import java.util.List;

/**
 * @author Peng
 * @date 2020/6/27--9:54
 **/
public interface AbstractBaseController {

    Result fetchList();

    default Result fetchList(BaseQuery query) {
        return Result.success();
    }

    Result getById(String id);

    <T> Result createBy(T param);

    <T> Result updateBy(T param);

    Result deleteById(String id);

    Result deleteByIds(List<String> ids);
}
