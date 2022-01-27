package com.lagou.dao;

import com.lagou.domain.Test;

import java.util.List;

public interface TestMapper {

    /**
     * 对Test表进行查询操作
     */
    public List<Test> findAllTest();
}
