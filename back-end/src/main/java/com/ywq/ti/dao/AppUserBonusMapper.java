package com.ywq.ti.dao;

import com.ywq.ti.entity.AppUserBonus;

public interface AppUserBonusMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    int insert(AppUserBonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    int insertSelective(AppUserBonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    AppUserBonus selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    int updateByPrimaryKeySelective(AppUserBonus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_user_bonus
     *
     * @mbg.generated Mon Jun 25 18:22:27 CST 2018
     */
    int updateByPrimaryKey(AppUserBonus record);
}