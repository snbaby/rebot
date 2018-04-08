package com.conan.crawler.server.pre.mapper;

import java.util.List;

import com.conan.crawler.server.pre.entity.GoodsTb;

public interface GoodsTbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    int insert(GoodsTb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    int insertSelective(GoodsTb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    GoodsTb selectByPrimaryKey(String id);
    
    List<GoodsTb> selectByStatus(String status);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    int updateByPrimaryKeySelective(GoodsTb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_tb
     *
     * @mbg.generated Sun Apr 01 12:17:12 CST 2018
     */
    int updateByPrimaryKey(GoodsTb record);
}