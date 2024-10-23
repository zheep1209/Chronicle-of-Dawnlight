package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 用户注册
     */
    Integer registerUser(@Param("user") UserPO user);

    /**
     *用户名密码登录
     */
    Integer loginByPassword(String identifier, String password);
    /**
     *查询
     */
    String selectByEmail(String identifier);

    // id 和 avatar 是必需的
    void update(UserPO userPO);
    /**
     *查询用户名
     */
    Integer select(UserPO userPO);
}
