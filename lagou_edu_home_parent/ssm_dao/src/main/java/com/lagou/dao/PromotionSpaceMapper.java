package com.lagou.dao;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceMapper {

    /**
     *  获取所有广告位的方法
     */
    public List<PromotionSpace> findAllPromotionSpace();

    /*
        添加广告位
     */
    public void savePromotionSpace(PromotionSpace promotionSpace);

    /*
        回显广告位信息，根据id查找广告位信息
     */
    public PromotionSpace findPromotionSpaceById(Integer id);

    /*
        跟新广告位
     */
    public void updatePromotionSpace(PromotionSpace promotionSpace);

}
