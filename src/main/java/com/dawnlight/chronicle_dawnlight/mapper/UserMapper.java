package com.dawnlight.chronicle_dawnlight.mapper;

import com.dawnlight.chronicle_dawnlight.pojo.po.UserPO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 用户注册
     */
    Integer registerUser(@Param("user") UserPO user);

    /**
     *登录
     */
    String loginByPassword(@Param("identifier") String identifier, @Param("password") String password);
    /**
     *查询
     */
    String selectByEmail(String identifier);

    // id 和 avatar 是必需的
    void update(UserPO userPO);
    /**
     *查重
     */
    String select(UserPO userPO);
    /**
     * 用户名查询ID
     */
    String selectByUserName(String username);

    UserVO getUser(String id);
}
